package database.jdbc;

public class MySQLConnectionConfig extends ConnectionConfig {

    public MySQLConnectionConfig(String host, String database, String username, String password) {
        super(host, database, username, password);
    }

    @Override
    protected String constructConnectionURL() {
        return "jdbc:mysql://" + host + "/" + database;
    }
}
