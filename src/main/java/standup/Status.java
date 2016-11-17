package standup;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Status {
    private String name;
    private String yesterday;
    private String today;
    private String impediments;
    private Date dateCreated;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getYesterday() {
        return this.yesterday;
    }
    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }

    public String getToday() {
        return this.today;
    }
    public void setToday(String today) {
        this.today = today;
    }

    public String getImpediments() {
        return this.impediments;
    }
    public void setImpediments(String impediments) {
        this.impediments = impediments;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public static class Mapper implements ResultSetMapper<Status> {
        @Override
        public Status map(int idx, ResultSet rs, StatementContext sc) throws SQLException {
            Status status = new Status();
            status.name = rs.getString("name");
            status.yesterday = rs.getString("completed_yesterday");
            status.today = rs.getString("working_on_today");
            status.impediments = rs.getString("impediments");
            status.dateCreated = rs.getDate("date_created");

            return status;
        }
    }
}