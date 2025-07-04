package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjavaPodataka {
    private int objavaId;
    private Date datumObjave;
    private String razlog;
    private Seansa seansa;
    private Primalac primalac;
} 