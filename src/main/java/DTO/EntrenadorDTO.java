package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Categoria;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrenadorDTO {
    String nombre;
    List<Categoria> categorias;
    List<BoxeadorInfoDTO> boxeadores;
}
