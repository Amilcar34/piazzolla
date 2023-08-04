package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.model.Categoria;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrenadorSinBoxDto {

  private String nombre;
  private List<Categoria> categorias;
}
