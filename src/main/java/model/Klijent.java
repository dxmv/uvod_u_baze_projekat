package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Klijent {
    private int klijentId;
    private String ime;
    private String prezime;
    private Date datumRodj;
    private String pol;
    private String email;
    private String telefon;
    private Date datumPrijave;
    private boolean ranijePosetio;
    private String opisProblema;
} 