package database.jdbc;

public class OracleConnectionConfig extends ConnectionConfig {

    public OracleConnectionConfig(String host, String database, String username, String password) {
        super(host, database, username, password);
    }

    @Override
    protected String constructConnectionURL() {
        return "jdbc:oracle:thin:@" + host + ":" + database;
    }
}
