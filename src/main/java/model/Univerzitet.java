package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Univerzitet {
    private int uniId;
    private String ime;
    private UzeUsmerenje usmerenje;
} 