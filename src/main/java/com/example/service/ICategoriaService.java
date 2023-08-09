package com.example.service;

import com.example.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    List<Categoria> obtenerCategorias();
    Categoria crearCategoria(Categoria categoria);
    Categoria actualizarCategoria(Long idCategoria, Categoria categoria);
    Categoria obtenerCategoriaPorPeso(Double peso);
}
