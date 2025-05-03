package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UzeUsmerenje {
    private int usmerenjeId;
    private String naziv;
    private List<Oblast> oblasti;
} 