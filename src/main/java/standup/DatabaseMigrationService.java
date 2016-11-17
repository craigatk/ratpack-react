package standup;

import org.flywaydb.core.Flyway;
import ratpack.service.Service;
import ratpack.service.StartEvent;

import javax.sql.DataSource;

public class DatabaseMigrationService implements Service {
    @Override
    public void onStart(StartEvent e) {
        DataSource dataSource = e.getRegistry().get(DataSource.class);

        Flyway flyway = new Flyway();

        flyway.setDataSource(dataSource);

        flyway.migrate();
    }
}
