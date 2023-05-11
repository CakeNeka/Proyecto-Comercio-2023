package org.cakeneka.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DuaDatabase extends MySqlConnection{
    
    public DuaDatabase() {
        super("proyectodua");
    }
    
    protected String[][] executeSelect(String query) throws SQLException {
        
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
}
