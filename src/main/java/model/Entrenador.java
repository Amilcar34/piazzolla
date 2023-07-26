package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrenador {
    String nombre;
    List<Categoria> categorias;
    List<Boxeador> boxeadores;
}
