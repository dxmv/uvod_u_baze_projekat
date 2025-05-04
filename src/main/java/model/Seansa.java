package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.sql.Time;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seansa {
    private int seansaId;
    private Date datum;
    private Time vremePocetka;
    private int trajanje;
    private boolean besplatnaSeansa;
    private Candidate kandidat;
    private Klijent klijent;
    private CenaPoSatu cenaPoSatu;
    
    // Related collections
    private List<BeleskeSeanse> beleske;
    private List<SeansaTest> testovi;
    private List<ObjavaPodataka> objave;
} 