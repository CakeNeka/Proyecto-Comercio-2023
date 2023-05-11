package org.cakeneka.utilities;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseConnection {
    
    protected final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    protected final String DB_URL;
    protected final String USER = "root";
    protected final String PASS = "";

    public DatabaseConnection(String databaseName) {
        DB_URL = "jdbc:mysql://localhost/" + databaseName;
    }
    
    protected abstract Connection connect() throws SQLException;
}
