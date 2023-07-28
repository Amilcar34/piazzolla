package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Categoria;
import java.sql.Date;

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
