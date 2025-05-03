package repository;

import com.raf.javafxapp.DatabaseConnection;
import model.Seansa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeansaRepository {
    /*
     * Get past sessions for a therapist
     */
    public List<Seansa> getPastSessions(int therapistId) {
        String query = "SELECT * FROM Seansa WHERE fk_kandidatId = ? AND datum < CURRENT_DATE() ORDER BY datum DESC, vremePocetka DESC";
        List<Seansa> seansaList = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, therapistId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    seansaList.add(mapResultSetToSeansa(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return seansaList;
    }

    /*
     * Get future sessions for a therapist
     */
    public List<Seansa> getFutureSessions(int therapistId) {
        String query = "SELECT * FROM Seansa WHERE fk_kandidatId = ? AND datum >= CURRENT_DATE() ORDER BY datum ASC, vremePocetka ASC";
        List<Seansa> seansaList = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, therapistId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    seansaList.add(mapResultSetToSeansa(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return seansaList;
    }

    /*
     * Map ResultSet to Seansa
     */
    private Seansa mapResultSetToSeansa(ResultSet rs) throws SQLException {
        Seansa seansa = new Seansa();
        seansa.setSeansaId(rs.getInt("seansaId"));
        seansa.setDatum(rs.getDate("datum"));
        seansa.setVremePocetka(rs.getTime("vremePocetka"));
        seansa.setTrajanje(rs.getInt("trajanje"));
        seansa.setBesplatnaSeansa(rs.getBoolean("besplatnaSeansa"));
        
        return seansa;
    }
}
