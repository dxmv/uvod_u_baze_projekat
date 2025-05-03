package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeleskeSeanse {
    private int beleskeId;
    private String text;
    private Seansa seansa;
} 