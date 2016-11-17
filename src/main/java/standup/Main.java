package standup;

import org.h2.jdbcx.JdbcDataSource;
import ratpack.guice.Guice;
import ratpack.server.RatpackServer;

import javax.sql.DataSource;

public class Main {
    public static void main(String... args) throws Exception {
        RatpackServer.start(server -> server
                .registry(Guice.registry(b -> b.
                        bindInstance(DataSource.class, createDatasource()).
                        bind(StatusService.class, DefaultStatusService.class).
                        bindInstance(new DatabaseMigrationService()).
                        bind(CreateStatusHandler.class).
                        bind(GetAllStatusHandler.class)
                    )

                )
                .handlers(chain -> chain
                        .prefix("api", c -> c
                                .prefix("status", statusPrefix -> statusPrefix.
                                        post(CreateStatusHandler.class).
                                        get("all", GetAllStatusHandler.class)
                                )
                        )
                )
        );
    }

    private static DataSource createDatasource() {
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        return dataSource;
    }
}
