package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrenador {

   private String nombre;
   private List<Categoria> categorias;
   private List<Boxeador> boxeadores;
}
