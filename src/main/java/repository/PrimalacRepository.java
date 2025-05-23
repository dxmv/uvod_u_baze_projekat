package repository;

import model.Primalac;
import com.raf.javafxapp.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrimalacRepository {

    public List<Primalac> getAllPrimaoci() {
        List<Primalac> primaoci = new ArrayList<>();
        String sql = "SELECT primalacId, naziv FROM Primalac";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Primalac p = new Primalac();
                p.setPrimalacId(rs.getInt("primalacId"));
                p.setNaziv(rs.getString("naziv"));
                primaoci.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider logging the error or throwing a custom exception
        }
        return primaoci;
    }
    
    // You can add other methods here, like getPrimalacById, savePrimalac, etc.
} 