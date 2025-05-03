package repository;

import model.Therapist;
import com.raf.javafxapp.DatabaseConnection;
import model.CentarZaObuku;
import model.Fakultet;
import model.Sertifikat;
import model.StepenStudija;
import model.OblastTerapije;
import model.Univerzitet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistRepository {

    public List<Therapist> getAllTherapists() {
        List<Therapist> therapists = new ArrayList<>();
        
        String query = "SELECT k.*, f.*, c.*, s.*, st.*, ot.* " +
                       "FROM Kandidat k " +
                       "JOIN Fakultet f ON k.fk_fakultetId = f.fakultetId " +
                       "JOIN CentarZaObuku c ON k.fk_centarId = c.centarId " +
                       "JOIN StepenStudija st ON k.fk_stepenId = st.stepenId " +
                       "JOIN Sertifikat s ON k.fk_sertId = s.sertId " +
                       "JOIN OblastTerapije ot ON s.fk_oblastId = ot.oblastId " +
                       "WHERE k.fk_sertId IS NOT NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Create objects from result set
                OblastTerapije oblast = new OblastTerapije(
                    rs.getInt("ot.oblastId"),
                    rs.getString("ot.ime")
                );
                
                Sertifikat sertifikat = new Sertifikat(
                    rs.getInt("s.sertId"),
                    rs.getDate("s.datumSert"),
                    oblast
                );
                
                StepenStudija stepenStudija = new StepenStudija(
                    rs.getInt("st.stepenId"),
                    rs.getString("st.naziv")
                );
                
                Fakultet fakultet = new Fakultet(
                    rs.getInt("f.fakultetId"),
                    rs.getString("f.ime"),
                    null, // We're not loading the univerzitet object here
                    null  // We're not loading the oblasti list here
                );
                
                CentarZaObuku centar = new CentarZaObuku(
                    rs.getInt("c.centarId"),
                    rs.getString("c.naziv"),
                    rs.getString("c.email"),
                    rs.getString("c.brojTelefona"),
                    rs.getString("c.ulica"),
                    rs.getInt("c.brojUlice"),
                    rs.getString("c.opstina")
                );
                
                Therapist therapist = new Therapist(
                    rs.getInt("k.kandidatId"),
                    rs.getString("k.telefon"),
                    rs.getString("k.prebivaliste"),
                    rs.getString("k.ime"),
                    rs.getString("k.prezime"),
                    rs.getString("k.jmbg"),
                    rs.getDate("k.datumRodj"),
                    rs.getString("k.email"),
                    rs.getBoolean("k.psiholog"),
                    fakultet,
                    centar,
                    stepenStudija,
                    sertifikat
                );
                
                therapists.add(therapist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return therapists;
    }

    public Therapist getTherapistById(int id) {
        Therapist therapist = null;
        
        String query = "SELECT k.*, f.*, c.*, s.*, st.*, ot.* " +
                       "FROM Kandidat k " +
                       "JOIN Fakultet f ON k.fk_fakultetId = f.fakultetId " +
                       "JOIN CentarZaObuku c ON k.fk_centarId = c.centarId " +
                       "JOIN StepenStudija st ON k.fk_stepenId = st.stepenId " +
                       "JOIN Sertifikat s ON k.fk_sertId = s.sertId " +
                       "JOIN OblastTerapije ot ON s.fk_oblastId = ot.oblastId " +
                       "WHERE k.kandidatId = ? AND k.fk_sertId IS NOT NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create objects from result set
                    OblastTerapije oblast = new OblastTerapije(
                        rs.getInt("ot.oblastId"),
                        rs.getString("ot.ime")
                    );
                    
                    Sertifikat sertifikat = new Sertifikat(
                        rs.getInt("s.sertId"),
                        rs.getDate("s.datumSert"),
                        oblast
                    );
                    
                    StepenStudija stepenStudija = new StepenStudija(
                        rs.getInt("st.stepenId"),
                        rs.getString("st.naziv")
                    );
                    
                    Fakultet fakultet = new Fakultet(
                        rs.getInt("f.fakultetId"),
                        rs.getString("f.ime"),
                        null, // We're not loading the univerzitet object here
                        null  // We're not loading the oblasti list here
                    );
                    
                    CentarZaObuku centar = new CentarZaObuku(
                        rs.getInt("c.centarId"),
                        rs.getString("c.naziv"),
                        rs.getString("c.email"),
                        rs.getString("c.brojTelefona"),
                        rs.getString("c.ulica"),
                        rs.getInt("c.brojUlice"),
                        rs.getString("c.opstina")
                    );
                    
                    therapist = new Therapist(
                        rs.getInt("k.kandidatId"),
                        rs.getString("k.telefon"),
                        rs.getString("k.prebivaliste"),
                        rs.getString("k.ime"),
                        rs.getString("k.prezime"),
                        rs.getString("k.jmbg"),
                        rs.getDate("k.datumRodj"),
                        rs.getString("k.email"),
                        rs.getBoolean("k.psiholog"),
                        fakultet,
                        centar,
                        stepenStudija,
                        sertifikat
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return therapist;
    }

    public static void main(String[] args) {
        TherapistRepository repo = new TherapistRepository();
        List<Therapist> therapists = repo.getAllTherapists();
        for (Therapist therapist : therapists) {
            System.out.println(therapist.getIme() + " " + therapist.getEmail());
        }

    }
}
