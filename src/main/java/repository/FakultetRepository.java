package repository;

import com.raf.javafxapp.DatabaseConnection;
import model.Fakultet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FakultetRepository {

    public List<Fakultet> getAllFakulteti() {
        List<Fakultet> fakulteti = new ArrayList<>();
        
        String query = "SELECT fakultetId, ime FROM Fakultet";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Fakultet fakultet = new Fakultet(
                    rs.getInt("fakultetId"),
                    rs.getString("ime"),
                    null, // Not loading univerzitet
                    null  // Not loading oblasti
                );
                fakulteti.add(fakultet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return fakulteti;
    }
    
}
