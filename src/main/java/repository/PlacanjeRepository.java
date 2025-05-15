package repository;

import com.raf.javafxapp.DatabaseConnection;
import com.raf.javafxapp.SessionManager;
import model.Klijent;
import model.Placanje;
import model.Seansa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlacanjeRepository {
    public List<Placanje> getAllPaymentsWithDelay() {
        List<Placanje> payments = new ArrayList<>();
        int therapistId = SessionManager.getInstance().getLoggedInKandidatId();

        String sql = "SELECT p.*, k.ime, k.prezime, s.datum AS seansaDatum " +
                "FROM Placanje p " +
                "JOIN Klijent k ON p.fk_klijentId = k.klijentId " +
                "JOIN Seansa s ON p.Seansa_seansaId = s.seansaId " +
                "WHERE s.fk_kandidatId = ? " +
                "ORDER BY p.datum DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, therapistId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Placanje p = new Placanje();
                    p.setPlacanjeId(rs.getInt("placanjeId"));
                    p.setRata(rs.getInt("rata"));
                    p.setIznos(rs.getBigDecimal("iznos"));
                    p.setProvizija(rs.getInt("provizija"));
                    p.setDatum(rs.getDate("datum"));
                    p.setNacinPlacanja(rs.getString("nacinPlacanja"));
                    p.setSvrha(rs.getString("svrha"));

                    Klijent k = new Klijent();
                    k.setIme(rs.getString("ime"));
                    k.setPrezime(rs.getString("prezime"));
                    p.setKlijent(k);

                    Seansa s = new Seansa();
                    s.setDatum(rs.getDate("seansaDatum"));
                    p.setSeansa(s);

                    payments.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }
}

