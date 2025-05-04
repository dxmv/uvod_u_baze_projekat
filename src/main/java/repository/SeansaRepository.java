package repository;

import com.raf.javafxapp.DatabaseConnection;
import model.Seansa;
import model.SeansaTest;
import model.BeleskeSeanse;
import model.ObjavaPodataka;
import model.CenaPoSatu;
import model.Klijent;
import model.PsihoTest;

import java.sql.*;
import java.math.BigDecimal;
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
    
    /*
     * Get all sessions id for a therapist
     */
    public List<Integer> getAllSessions(int therapistId) {
        String query = "SELECT seansaId FROM Seansa WHERE fk_kandidatId = ?";
        List<Integer> seansaIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, therapistId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    seansaIds.add(rs.getInt("seansaId"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seansaIds;
    }

    /**
     * Get a single session by id
     */
    public Seansa getSessionById(int sessionId) {
        String query = "SELECT * FROM Seansa WHERE seansaId = ?";
        Seansa seansa = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, sessionId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    seansa = mapResultSetToSeansa(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return seansa;
    }

    /**
     * Get a detailed session by id including all related information
     * (SeansaTest, BeleskeSeanse, ObjavaPodataka, CenaPoSatu, and Klijent)
     * excluding Candidate information
     */
    public Seansa getDetailedSessionById(int sessionId) {
        String query = "SELECT s.*, c.cenaId, c.cena, c.datumPromene, k.klijentId, k.ime, k.prezime, " +
                      "k.datumRodj, k.pol, k.email, k.telefon, k.datumPrijave, k.ranijePosetio, k.opisProblema " +
                      "FROM Seansa s " +
                      "JOIN CenaPoSatu c ON s.fk_cenaId = c.cenaId " +
                      "JOIN Klijent k ON s.fk_klijentId = k.klijentId " +
                      "WHERE s.seansaId = ?";
        
        Seansa seansa = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, sessionId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create Seansa with base information
                    seansa = mapResultSetToSeansa(rs);
                    
                    // Add CenaPoSatu information
                    CenaPoSatu cenaPoSatu = new CenaPoSatu();
                    cenaPoSatu.setCenaId(rs.getInt("cenaId"));
                    cenaPoSatu.setCena(rs.getBigDecimal("cena"));
                    cenaPoSatu.setDatumPromene(rs.getDate("datumPromene"));
                    
                    // Add Klijent information
                    Klijent klijent = new Klijent();
                    klijent.setKlijentId(rs.getInt("klijentId"));
                    klijent.setIme(rs.getString("ime"));
                    klijent.setPrezime(rs.getString("prezime"));
                    klijent.setDatumRodj(rs.getDate("datumRodj"));
                    klijent.setPol(rs.getString("pol"));
                    klijent.setEmail(rs.getString("email"));
                    klijent.setTelefon(rs.getString("telefon"));
                    klijent.setDatumPrijave(rs.getDate("datumPrijave"));
                    klijent.setRanijePosetio(rs.getBoolean("ranijePosetio"));
                    klijent.setOpisProblema(rs.getString("opisProblema"));
                    
                    // Set the related objects to Seansa
                    seansa.setKlijent(klijent);
                    seansa.setCenaPoSatu(cenaPoSatu);
                    
                    // Fetch and add BeleskeSeanse
                    List<BeleskeSeanse> beleske = getBeleskeForSeansa(sessionId, conn);
                    seansa.setBeleske(beleske);
                    
                    // Fetch and add SeansaTest
                    List<SeansaTest> testovi = getTestoviForSeansa(sessionId, conn);
                    seansa.setTestovi(testovi);
                    
                    // Fetch and add ObjavaPodataka
                    List<ObjavaPodataka> objave = getObjaveForSeansa(sessionId, conn);
                    seansa.setObjave(objave);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return seansa;
    }
    
    /**
     * Get all BeleskeSeanse for a specific Seansa
     */
    private List<BeleskeSeanse> getBeleskeForSeansa(int seansaId, Connection conn) throws SQLException {
        String query = "SELECT * FROM BeleskeSeanse WHERE fk_seansaId = ?";
        List<BeleskeSeanse> beleske = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, seansaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BeleskeSeanse beleska = new BeleskeSeanse();
                    beleska.setBeleskeId(rs.getInt("beleskeId"));
                    beleska.setText(rs.getString("text"));
                    beleske.add(beleska);
                }
            }
        }
        
        return beleske;
    }
    
    /**
     * Get all SeansaTest for a specific Seansa
     */
    private List<SeansaTest> getTestoviForSeansa(int seansaId, Connection conn) throws SQLException {
        String query = "SELECT st.*, pt.testId, pt.naziv, pt.oblast, pt.cenaRSD " +
                      "FROM SeansaTest st " +
                      "JOIN PsihoTest pt ON st.fk_psihoTestId = pt.testId " +
                      "WHERE st.fk_seansaId = ?";
        List<SeansaTest> testovi = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, seansaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SeansaTest test = new SeansaTest();
                    test.setSeansaTestId(rs.getInt("seansaTestId"));
                    test.setRezultat(rs.getInt("rezultat"));
                    
                    // Create and set the PsihoTest
                    PsihoTest psihoTest = new PsihoTest();
                    psihoTest.setTestId(rs.getInt("testId"));
                    psihoTest.setNaziv(rs.getString("naziv"));
                    psihoTest.setOblast(rs.getString("oblast"));
                    psihoTest.setCenaRSD(rs.getBigDecimal("cenaRSD"));
                    test.setPsihoTest(psihoTest);
                    
                    testovi.add(test);
                }
            }
        }
        
        return testovi;
    }
    
    /**
     * Get all ObjavaPodataka for a specific Seansa
     */
    private List<ObjavaPodataka> getObjaveForSeansa(int seansaId, Connection conn) throws SQLException {
        String query = "SELECT * FROM ObjavaPodataka WHERE fk_seansaId = ?";
        List<ObjavaPodataka> objave = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, seansaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ObjavaPodataka objava = new ObjavaPodataka();
                    objava.setObjavaId(rs.getInt("objavaId"));
                    objava.setDatumObjave(rs.getDate("datumObjave"));
                    objava.setPrimalac(rs.getBytes("primalac"));
                    objava.setRazlog(rs.getString("razlog"));
                    objave.add(objava);
                }
            }
        }
        
        return objave;
    }
}
