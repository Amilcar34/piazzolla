package service;

import model.Categoria;

import java.util.Optional;
import java.util.Set;

public interface ICategoriaService {
    Set<Categoria> getAllCategorias();
    Categoria create(Categoria categoria);
    Optional<Categoria> update(Long idCategoria, Categoria categoria);
}
