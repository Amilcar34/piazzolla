package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Boxeador {

    String nombre;
    Double peso;
    Categoria categoria;
    Entrenador entrenador;
    Date fechaIngreso;

}
