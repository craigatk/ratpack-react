package standup;

import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;

import static ratpack.jackson.Jackson.json;

public class GetAllStatusHandler implements Handler {

    private final StatusService statusService;

    @Inject
    public GetAllStatusHandler(StatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    public void handle(Context ctx) {
        statusService.list().
                then(statusList -> ctx.render(json(statusList)));
    }
}
