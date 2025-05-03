package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeansaTest {
    private int seansaTestId;
    private int rezultat;
    private Seansa seansa;
    private PsihoTest psihoTest;
} 