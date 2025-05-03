package repository;

import com.raf.javafxapp.DatabaseConnection;
import model.OblastTerapije;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OblastTerapijeRepository {

    public List<OblastTerapije> getAllOblastiTerapije() {
        List<OblastTerapije> oblasti = new ArrayList<>();
        
        String query = "SELECT oblastId, ime FROM OblastTerapije";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                OblastTerapije oblast = new OblastTerapije(
                    rs.getInt("oblastId"),
                    rs.getString("ime")
                );
                oblasti.add(oblast);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return oblasti;
    }
    
    public OblastTerapije getOblastTerapijeById(int id) {
        OblastTerapije oblast = null;
        
        String query = "SELECT oblastId, ime FROM OblastTerapije WHERE oblastId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    oblast = new OblastTerapije(
                        rs.getInt("oblastId"),
                        rs.getString("ime")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return oblast;
    }
} 