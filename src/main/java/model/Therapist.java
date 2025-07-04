package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Therapist {
    private int kandidatId;
    private String telefon;
    private String prebivaliste;
    private String ime;
    private String prezime;
    private String jmbg;
    private Date datumRodj;
    private String email;
    private boolean psiholog;
    private Fakultet fakultet;
    private CentarZaObuku centar;
    private StepenStudija stepenStudija;
    private Sertifikat sertifikat; // Has certificate
    private String sifra; // Password field
} 