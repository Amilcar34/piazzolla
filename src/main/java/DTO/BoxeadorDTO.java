package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Categoria;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxeadorDTO {

    String nombre;
    Double peso;
    Categoria categoria;
    EntrenadorInfoDTO entrenador;
    Date fechaIngreso;
}
