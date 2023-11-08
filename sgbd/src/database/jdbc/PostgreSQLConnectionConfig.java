package database.jdbc;

public class PostgreSQLConnectionConfig extends ConnectionConfig {

    public PostgreSQLConnectionConfig(String host, String database, String username, String password) {
        super(host, database, username, password);
    }

    @Override
    protected String constructConnectionURL() {
        return "jdbc:postgresql://" + host + "/" + database;
    }
}
