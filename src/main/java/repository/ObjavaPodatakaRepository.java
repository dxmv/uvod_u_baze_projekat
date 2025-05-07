package repository;

import model.ObjavaPodataka;
import model.Primalac;
import model.Seansa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjavaPodatakaRepository {
    public boolean save(int seasnaId, Date datum, String primalac, String razlog) {
        return true;
    }

    /**
     * Get all ObjavaPodataka for a specific Seansa
     */
    public static List<ObjavaPodataka> getObjaveForSeansa(int seansaId, Connection conn) throws SQLException {
        String query = "SELECT o.*, p.naziv as primalacNaziv " +
                      "FROM ObjavaPodataka o " +
                      "JOIN Primalac p ON o.primalacId = p.primalacId " +
                      "WHERE o.fk_seansaId = ?";
        List<ObjavaPodataka> objave = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, seansaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ObjavaPodataka objava = new ObjavaPodataka();
                    objava.setObjavaId(rs.getInt("objavaId"));
                    objava.setDatumObjave(rs.getDate("datumObjave"));
                    objava.setRazlog(rs.getString("razlog"));

                    Primalac primalac = new Primalac();
                    primalac.setPrimalacId(rs.getInt("primalacId"));
                    primalac.setNaziv(rs.getString("primalacNaziv"));
                    objava.setPrimalac(primalac);
                    
                    objave.add(objava);
                }
            }
        }
        
        return objave;
    }
}
