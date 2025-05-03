package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sertifikat {
    private int sertId;
    private Date datumSert;
    private OblastTerapije oblast;
} 