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

    private String nombre;
    private List<Categoria> categorias;
    private List<BoxeadorInfoDTO> boxeadores;

}
