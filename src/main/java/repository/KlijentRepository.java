package repository;

import model.Klijent;
import com.raf.javafxapp.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KlijentRepository {

    public List<Klijent> getAll() {
        List<Klijent> klijenti = new ArrayList<>();
        String sql = "SELECT klijentId, ime, prezime, datumPrijave, opisProblema FROM Klijent";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Klijent k = new Klijent();
                k.setKlijentId(rs.getInt("klijentId"));
                k.setIme(rs.getString("ime"));
                k.setPrezime(rs.getString("prezime"));
                k.setDatumPrijave(rs.getDate("datumPrijave"));
                k.setOpisProblema(rs.getString("opisProblema"));
                klijenti.add(k);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return klijenti;
    }
    public List<Klijent> getAllPrijave() {
        List<Klijent> prijavljeniKlijenti = new ArrayList<>();
        String sql = "SELECT k.* FROM Klijent k WHERE k.datumPrijave IS NOT NULL AND NOT EXISTS (SELECT 1 FROM Seansa s WHERE s.fk_klijentId = k.klijentId)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Klijent k = new Klijent();
                k.setKlijentId(rs.getInt("klijentId"));
                k.setIme(rs.getString("ime"));
                k.setPrezime(rs.getString("prezime"));
                k.setDatumRodj(rs.getDate("datumRodj"));
                k.setPol(rs.getString("pol"));
                k.setEmail(rs.getString("email"));
                k.setTelefon(rs.getString("telefon"));
                k.setDatumPrijave(rs.getDate("datumPrijave"));
                k.setRanijePosetio(rs.getBoolean("ranijePosetio"));
                k.setOpisProblema(rs.getString("opisProblema"));
                prijavljeniKlijenti.add(k);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prijavljeniKlijenti;
    }
}
