package com.example.repository;

import com.example.model.Boxeador;

import java.util.List;

public interface IBoxeadorRepository {
    List<Boxeador> getAllBoxeadores();
    Boxeador create(Boxeador boxeador);
    Integer cantBoxeadoresPorDia();
}
