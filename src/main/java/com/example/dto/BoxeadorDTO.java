package com.example.dto;

import jakarta.validation.constraints.NotNull;
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
   @NotNull(message = "El peso no puede ser null")
   private Double peso;
   private Categoria categoria;
   private EntrenadorInfoDTO entrenador;
   private Date fechaIngreso;
}
