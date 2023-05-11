package org.cakeneka.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySqlConnection extends DatabaseConnection{
    
    public MySqlConnection(String databaseName) {
        super(databaseName);
    }
    
    @Override
    protected Connection connect() throws SQLException {
        Connection con = null;

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DuaPersistenceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        con = (com.mysql.jdbc.Connection) DriverManager.getConnection(DB_URL, USER, PASS);

        return con;
    }
    
    
}
