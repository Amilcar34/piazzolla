package com.example.service;

import com.example.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    List<Categoria> getAllCategorias();
    Categoria create(Categoria categoria);
    Optional<Categoria> update(Long idCategoria, Categoria categoria);
    Categoria obtenerCategoriaPorPeso(Double peso);
}
