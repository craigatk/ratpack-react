package standup;

import org.skife.jdbi.v2.DBI;
import ratpack.exec.Blocking;
import ratpack.exec.Promise;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;

class DefaultStatusService implements StatusService {
    private final DBI dbi;

    @Inject
    DefaultStatusService(DataSource dataSource) {
        this.dbi = new DBI(dataSource);
    }

    @Override
    public Promise<Status> create(Status newStatus) {
        return Blocking.get(() -> createStatus(newStatus));
    }

    private Status createStatus(Status newStatus) {
        StatusDAO statusDAO = dbi.open(StatusDAO.class);

        statusDAO.create(newStatus.getName(), newStatus.getYesterday(), newStatus.getToday(), newStatus.getImpediments());

        return newStatus;
    }

    @Override
    public Promise<List<Status>> list() {
        return Blocking.get(() -> listStatuses());
    }

    private List<Status> listStatuses() {
        StatusDAO statusDAO = dbi.open(StatusDAO.class);

        return statusDAO.list();
    }
}
