package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Valuta {
    private int valutaId;
    private String sifra;
    private String puniNaziv;
} 