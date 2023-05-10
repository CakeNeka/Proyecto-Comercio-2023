package org.cakeneka.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class DuaDatabase {
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/ProyectoDUA";
    private final String USER = "root";
    private final String PASS = "";
    
    public String[][] executeSelect(String query) throws SQLException {
        
        Connection connection = connect();
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
        
        connection.close();
        return table;
    }
    
    public int addSaveState(List<String> fields) throws SQLException {
        Connection con = connect();
        String query = "INSERT INTO SaveStates(fields) VALUES(?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, listToString(fields));
        int rowsAffected = ps.executeUpdate();
        con.close();
        return rowsAffected;
    }
        
    private String listToString(List<String> ls) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < ls.size(); i++) {
            String cleanField = ls.get(i);
            cleanField = cleanField.replace("\\", "");
            sb.append(cleanField);
            
            if (i < ls.size() - 1) {
                sb.append("\\");
            }
        }

        return sb.toString();
    }
    
    public int deleteById(int id) throws SQLException {
        Connection con = connect();
        String query = "DELETE FROM SaveStates WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        con.close();
        return rowsAffected;
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

