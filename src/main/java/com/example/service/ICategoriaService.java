package com.example.service;

import com.example.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    List<Categoria> obtenerCategorias();
    Boolean crearCategoria(Categoria categoria);
    Optional<Categoria> actualizarCategoria(Long idCategoria, Categoria categoria);
    Categoria obtenerCategoriaPorPeso(Double peso);
}
