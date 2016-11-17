package standup

import groovy.json.JsonOutput
import ratpack.exec.Promise
import ratpack.handling.Handler
import ratpack.jackson.JsonRender
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

class CreateStatusHandlerSpec extends Specification {
    StatusService statusService = Mock()

    Handler handler = new CreateStatusHandler(statusService)

    RequestFixture requestFixture = RequestFixture.requestFixture()

    void "should create new status"() {
        given:
        Status statusToCreate = new Status(name: 'My Name', yesterday: 'Yesterday', today: 'Today', impediments: 'None', dateCreated: new Date())

        String statusJson = JsonOutput.toJson(statusToCreate)

        when:
        def result = requestFixture.body(statusJson, 'application/json').handle(handler)

        then:
        1 * statusService.create({ Status newStatus ->
            assert newStatus.name == 'My Name'

            return true
        }) >> Promise.sync { statusToCreate }

        and:
        assert result.status.code == 200

        Status renderedStatus = result.rendered(JsonRender).object
        assert renderedStatus.name == 'My Name'
        assert renderedStatus.yesterday == 'Yesterday'
        assert renderedStatus.today == 'Today'
        assert renderedStatus.impediments == 'None'
    }
}
