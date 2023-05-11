package org.cakeneka.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;    
import java.util.List;

public class DuaPersistenceManager extends DuaDatabase{
    
    public String[][] getSaveStatesTable() throws SQLException {
        return executeSelect("SELECT * FROM SaveStates");
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
    
    public int deleteSaveStateById(int id) throws SQLException {
        Connection con = connect();
        String query = "DELETE FROM SaveStates WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        con.close();
        return rowsAffected;
    }

}

