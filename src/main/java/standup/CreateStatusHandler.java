package standup;

import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class CreateStatusHandler implements Handler {
    private final StatusService statusService;

    @Inject
    CreateStatusHandler(StatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    public void handle(Context ctx) {
        ctx.parse(fromJson(Status.class)).
                flatMap(status -> statusService.create(status)).
                then(status -> ctx.render(json(status)));
    }
}
