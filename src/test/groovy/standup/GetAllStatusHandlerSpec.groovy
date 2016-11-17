package standup

import ratpack.exec.Promise
import ratpack.handling.Handler
import ratpack.jackson.JsonRender
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

class GetAllStatusHandlerSpec extends Specification {
    StatusService statusService = Mock()

    Handler handler = new GetAllStatusHandler(statusService)

    RequestFixture requestFixture = RequestFixture.requestFixture()

    void "should get all status items"() {
        given:
        Status status = new Status(name: 'My Name', yesterday: 'Completed yesterday')

        when:
        def result = requestFixture.handle(handler)

        then:
        1 * statusService.list() >> Promise.sync { [status] }

        and:
        assert result.status.code == 200

        List<Status> renderedStatusList = result.rendered(JsonRender).object
        assert renderedStatusList.size() == 1
        assert renderedStatusList[0].name == status.name
        assert renderedStatusList[0].yesterday == status.yesterday
    }
}
