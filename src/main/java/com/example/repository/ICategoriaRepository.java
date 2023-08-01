package com.example.repository;

import com.example.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaRepository {
    List<Categoria> getAllCategorias();
    Categoria save(Categoria categoria);
    Optional<Categoria> findById(Long idCategoria);
}
