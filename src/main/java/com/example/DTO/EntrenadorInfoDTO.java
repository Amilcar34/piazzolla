package com.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.model.Categoria;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrenadorInfoDTO {

  private String nombre;
  private List<Categoria> categorias;
}
