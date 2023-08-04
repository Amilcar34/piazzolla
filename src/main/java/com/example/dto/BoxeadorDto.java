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
public class BoxeadorDto {

   private String nombre;
   private Double peso;
   private Categoria categoria;
   private EntrenadorSinBoxDto entrenador;
   private Date fechaIngreso;
}
