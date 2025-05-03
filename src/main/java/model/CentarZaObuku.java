package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentarZaObuku {
    private int centarId;
    private String naziv;
    private String email;
    private String brojTelefona;
    private String ulica;
    private int brojUlice;
    private String opstina;
} 