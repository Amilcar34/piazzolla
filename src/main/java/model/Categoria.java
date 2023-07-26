package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    public static final Double SIN_LIMITE = 999D;

    public Long _id;
    public String nombre;
    public Double pesoMin;
    public Double pesoMax;

}
