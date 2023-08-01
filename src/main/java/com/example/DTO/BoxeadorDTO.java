package com.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.model.Categoria;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxeadorDTO {

   private String nombre;
   private Double peso;
   private Categoria categoria;
   private EntrenadorInfoDTO entrenador;
   private Date fechaIngreso;
}
