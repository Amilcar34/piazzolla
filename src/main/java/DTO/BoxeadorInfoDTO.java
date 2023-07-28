package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Categoria;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxeadorInfoDTO {
   private String nombre;
   private Double peso;
   private Categoria categoria;
}
