package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Boxeador {

    private String nombre;
    private Double peso;
    private Categoria categoria;
    private Entrenador entrenador;
    private Date fechaIngreso;
}
