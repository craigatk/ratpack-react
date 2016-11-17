package standup;

import ratpack.exec.Promise;

import java.util.List;

public interface StatusService {
    public Promise<Status> create(Status newStatus);

    public Promise<List<Status>> list();
}

