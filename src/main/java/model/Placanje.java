package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Placanje {
    private int placanjeId;
    private int rata;
    private BigDecimal iznos;
    private int provizija;
    private Date datum;
    private String nacinPlacanja;
    private String svrha;
    private Seansa seansa;
    private Klijent klijent;
    private Valuta valuta;
} 