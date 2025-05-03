package repository;

import model.Therapist;
import com.raf.javafxapp.DatabaseConnection;
import model.CentarZaObuku;
import model.Fakultet;
import model.Sertifikat;
import model.StepenStudija;
import model.OblastTerapije;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Date;
import java.time.LocalDate;
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

    /**
     * Registers a new therapist using the insert_terapeut stored procedure.
     * 
     * @param ime Therapist's first name
     * @param prezime Therapist's last name
     * @param email Therapist's email
     * @param telefon Therapist's phone number
     * @param jmbg Therapist's JMBG (unique ID number)
     * @param datumRodj Therapist's date of birth
     * @param prebivaliste Therapist's place of residence
     * @param isPsiholog Whether the therapist is a psychologist
     * @param fakultet Selected faculty name
     * @param stepenStudija Education level name
     * @param centarZaObuku Training center name
     * @param oblastTerapije Therapy field name
     * @param datumSertifikata Certificate date
     * @return true if registration was successful, false otherwise
     */
    public boolean registerTherapist(
            String ime, 
            String prezime, 
            String email, 
            String telefon, 
            String jmbg, 
            LocalDate datumRodj, 
            String prebivaliste, 
            boolean isPsiholog,
            String fakultet, 
            String stepenStudija, 
            String centarZaObuku, 
            String oblastTerapije, 
            LocalDate datumSertifikata) {
        
        String callProcedure = "{CALL insert_terapeut(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callProcedure)) {
            
            // Set all parameters in the order defined in the procedure
            stmt.setString(1, ime);
            stmt.setString(2, prezime);
            stmt.setString(3, email);
            stmt.setString(4, telefon);
            stmt.setString(5, jmbg);
            stmt.setDate(6, Date.valueOf(datumRodj));
            stmt.setString(7, prebivaliste);
            stmt.setBoolean(8, isPsiholog);
            stmt.setString(9, fakultet);
            stmt.setString(10, stepenStudija);
            stmt.setString(11, centarZaObuku);
            stmt.setString(12, oblastTerapije);
            stmt.setDate(13, Date.valueOf(datumSertifikata));
            
            // Execute the stored procedure
            stmt.execute();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTherapistIdByEmail(String email) {
        int kandidatId = 0;
        
        String query = "SELECT kandidatId FROM Kandidat WHERE email = ? LIMIT 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    kandidatId = rs.getInt("kandidatId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return kandidatId;
    }
}
