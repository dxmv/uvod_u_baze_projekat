package repository;

import com.raf.javafxapp.DatabaseConnection;
import model.Candidate;
import model.CentarZaObuku;
import model.Fakultet;
import model.StepenStudija;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateRepository {
    /**
     * Insert a new candidate with a supervisor into the database
     * Uses the insert_kandidat_sa_supervizorom stored procedure
     * 
     * @param candidate The candidate to insert
     * @param supervisorJmbg JMBG of the supervisor
     * @param supervisionStartDate Date when supervision starts
     * @param sifra Password for login
     * @return true if the insert was successful, false otherwise
     */
    public boolean insert(Candidate candidate, String supervisorJmbg, Date supervisionStartDate, String sifra) {
        String callProcedure = "{CALL insert_kandidat_sa_supervizorom(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callProcedure)) {
            
            // Set the parameters for the stored procedure
            stmt.setString(1, candidate.getIme());
            stmt.setString(2, candidate.getPrezime());
            stmt.setString(3, candidate.getEmail());
            stmt.setString(4, candidate.getTelefon());
            stmt.setString(5, candidate.getJmbg());
            stmt.setDate(6, new java.sql.Date(candidate.getDatumRodj().getTime()));
            stmt.setString(7, candidate.getPrebivaliste());
            stmt.setBoolean(8, candidate.isPsiholog());
            stmt.setString(9, sifra);
            
            // Get fakultet, stepen, and centar names
            stmt.setString(10, candidate.getFakultet().getIme());
            stmt.setString(11, candidate.getStepenStudija().getNaziv());
            stmt.setString(12, candidate.getCentar().getNaziv());
            
            // Set supervisor JMBG and supervision start date
            stmt.setString(13, supervisorJmbg);
            stmt.setDate(14, supervisionStartDate);
            
            // Execute the stored procedure
            stmt.execute();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all candidates that don't have a certificate (fk_sertId is NULL)
     * 
     * @return List of candidates without certificates
     */
    public List<Candidate> getAllCandidates() {
        String query = "SELECT k.*, f.ime AS fakultet_ime, s.naziv AS stepen_naziv, c.naziv AS centar_naziv " +
                       "FROM Kandidat k " +
                       "JOIN Fakultet f ON k.fk_fakultetId = f.fakultetId " +
                       "JOIN StepenStudija s ON k.fk_stepenId = s.stepenId " +
                       "JOIN CentarZaObuku c ON k.fk_centarId = c.centarId " +
                       "WHERE k.fk_sertId IS NULL";
        
        List<Candidate> candidates = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                candidates.add(mapResultSetToCandidateWithFullInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return candidates;
    }
    
    /**
     * Get a candidate by ID with full information about their Fakultet, StepenStudija, and CentarZaObuku
     * 
     * @param id The candidate ID
     * @return The candidate with full information, or null if not found
     */
    public Candidate getCandidateById(int id) {
        String query = "SELECT k.*, f.ime AS fakultet_ime, f.*, s.naziv AS stepen_naziv, s.*, c.naziv AS centar_naziv, c.* " +
                       "FROM Kandidat k " +
                       "JOIN Fakultet f ON k.fk_fakultetId = f.fakultetId " +
                       "JOIN StepenStudija s ON k.fk_stepenId = s.stepenId " +
                       "JOIN CentarZaObuku c ON k.fk_centarId = c.centarId " +
                       "WHERE k.kandidatId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCandidateWithFullInfo(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    
    /**
     * Map a ResultSet to a Candidate object with full information for related entities
     */
    private Candidate mapResultSetToCandidateWithFullInfo(ResultSet rs) throws SQLException {
        Candidate candidate = new Candidate();
        candidate.setKandidatId(rs.getInt("kandidatId"));
        candidate.setIme(rs.getString("ime"));
        candidate.setPrezime(rs.getString("prezime"));
        candidate.setEmail(rs.getString("email"));
        candidate.setTelefon(rs.getString("telefon"));
        candidate.setJmbg(rs.getString("jmbg"));
        candidate.setDatumRodj(rs.getDate("datumRodj"));
        candidate.setPrebivaliste(rs.getString("prebivaliste"));
        candidate.setPsiholog(rs.getBoolean("psiholog"));
        
        // Create fully populated related entities
        Fakultet fakultet = new Fakultet();
        fakultet.setFakultetId(rs.getInt("fk_fakultetId"));
        fakultet.setIme(rs.getString("fakultet_ime"));
        
        StepenStudija stepenStudija = new StepenStudija();
        stepenStudija.setStepenId(rs.getInt("fk_stepenId"));
        stepenStudija.setNaziv(rs.getString("stepen_naziv"));
        
        CentarZaObuku centar = new CentarZaObuku();
        centar.setCentarId(rs.getInt("fk_centarId"));
        centar.setNaziv(rs.getString("centar_naziv"));
        centar.setEmail(rs.getString("email"));

        
        candidate.setFakultet(fakultet);
        candidate.setStepenStudija(stepenStudija);
        candidate.setCentar(centar);
        
        return candidate;
    }
}
