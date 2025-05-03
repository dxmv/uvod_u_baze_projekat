package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenaPoSatu {
    private int cenaId;
    private BigDecimal cena;
    private Date datumPromene;
    private Seansa seansa;
} 