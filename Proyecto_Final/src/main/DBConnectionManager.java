package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DBConnectionManager {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/escuela";
    static final String USER = "root";
    static final String PASS = "";
    
    public String[][] getTable(String name) throws SQLException {
        
        Connection connection = connect();
        String query = "SELECT * FROM " + name;
        PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery();
            
        ResultSetMetaData meta = resultSet.getMetaData();
        int columns = meta.getColumnCount();
        resultSet.last();
        int rows = resultSet.getRow();
        String[][] table = new String[rows][columns];
        resultSet.beforeFirst();
        
        int i = 0;
        while (resultSet.next()) {
            for (int j = 0; j < table[i].length; j++) {
                Object cell = resultSet.getObject(j + 1);
                if (cell == null) {
                    table[i][j] = "null";
                } else {
                    table[i][j] = cell.toString();
                }
            }
            i++;
        }
        return table;
    }
    
    private Connection connect() {
        Connection con = null;
        try {
            Class.forName(JDBC_DRIVER);
            con = (com.mysql.jdbc.Connection) DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}

