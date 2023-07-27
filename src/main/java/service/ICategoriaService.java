package service;

import model.Categoria;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ICategoriaService {
    List<Categoria> getAllCategorias();
    Categoria create(Categoria categoria);
    Optional<Categoria> update(Long idCategoria, Categoria categoria);
    Categoria obtenerCategoriaPorPeso(Double peso);
}
