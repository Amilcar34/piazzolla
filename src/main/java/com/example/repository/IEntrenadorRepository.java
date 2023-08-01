package com.example.repository;

import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;


import java.util.List;

public interface IEntrenadorRepository {
    List<Entrenador> getAllEntrenadores();
    Entrenador obtenerEntrenadorPorCategoria(Categoria categoria);
    Entrenador addBoxeador(Entrenador entrenador, Boxeador boxeador);
    Integer obtenerBoxeadoresDelDia(Entrenador entrenador);
}
