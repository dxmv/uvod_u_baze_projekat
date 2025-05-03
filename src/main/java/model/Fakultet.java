package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fakultet {
    private int fakultetId;
    private String ime;
    private Univerzitet univerzitet;
    private List<Oblast> oblasti;
} 