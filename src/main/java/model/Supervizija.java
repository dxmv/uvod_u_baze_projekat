package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supervizija {
    private Date datumPocetka;
    private Date datumKraja;
    private Candidate kandidat;
    private Candidate supervizor;
} 