package org.cakeneka.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementa el método connect() y permite ejecutar un SELECT
 * @author Neka
 */
public class DuaDatabase extends DatabaseConnection{
    
    public DuaDatabase() {
        super("proyectodua");
    }
    
    /**
     * Ejecuta un SELECT y devuelve un array bidimensional igual a la 
     * tabla sql (solo con los datos, sin cabecera)
     * @param query
     * @return
     * @throws SQLException 
     */
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
                table[i][j] = cell == null ? "null" : cell.toString();
            }
            i++;
        }
        
        connection.close();
        return table;
    }
    
    /**
     * Realiza la conexión con la base de datos
     * @return
     * @throws SQLException 
     */
    @Override
    protected Connection connect() throws SQLException {
        Connection con;

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DuaPersistenceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        con = (com.mysql.jdbc.Connection) DriverManager.getConnection(DB_URL, USER, PASS);

        return con;
    }
}
