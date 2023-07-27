package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Categoria;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxeadorInfoDTO {
    String nombre;
    Double peso;
    Categoria categoria;
}
