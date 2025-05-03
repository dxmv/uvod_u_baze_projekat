package repository;

import com.raf.javafxapp.DatabaseConnection;
import model.CentarZaObuku;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CentarZaObukuRepository {

    public List<CentarZaObuku> getAllCentriZaObuku() {
        List<CentarZaObuku> centri = new ArrayList<>();
        
        String query = "SELECT centarId, naziv, email, brojTelefona, ulica, brojUlice, opstina " +
                       "FROM CentarZaObuku";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                CentarZaObuku centar = new CentarZaObuku(
                    rs.getInt("centarId"),
                    rs.getString("naziv"),
                    rs.getString("email"),
                    rs.getString("brojTelefona"),
                    rs.getString("ulica"),
                    rs.getInt("brojUlice"),
                    rs.getString("opstina")
                );
                centri.add(centar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return centri;
    }
    
    public CentarZaObuku getCentarZaObukuById(int id) {
        CentarZaObuku centar = null;
        
        String query = "SELECT centarId, naziv, email, brojTelefona, ulica, brojUlice, opstina " +
                       "FROM CentarZaObuku WHERE centarId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    centar = new CentarZaObuku(
                        rs.getInt("centarId"),
                        rs.getString("naziv"),
                        rs.getString("email"),
                        rs.getString("brojTelefona"),
                        rs.getString("ulica"),
                        rs.getInt("brojUlice"),
                        rs.getString("opstina")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return centar;
    }
} 