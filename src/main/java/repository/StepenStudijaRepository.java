package repository;

import com.raf.javafxapp.DatabaseConnection;
import model.StepenStudija;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StepenStudijaRepository {

    public List<StepenStudija> getAllStepenStudija() {
        List<StepenStudija> stepenStudijaList = new ArrayList<>();
        
        String query = "SELECT stepenId, naziv FROM StepenStudija";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                StepenStudija stepenStudija = new StepenStudija(
                    rs.getInt("stepenId"),
                    rs.getString("naziv")
                );
                stepenStudijaList.add(stepenStudija);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return stepenStudijaList;
    }
    
} 