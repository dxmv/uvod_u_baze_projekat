package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PsihoTest {
    private int testId;
    private String naziv;
    private String oblast;
    private BigDecimal cenaRSD;
} 