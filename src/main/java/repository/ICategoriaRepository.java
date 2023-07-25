package repository;

import model.Categoria;

import java.util.Optional;
import java.util.Set;

public interface ICategoriaRepository {
    Set<Categoria> getAllCategorias();
    void create(Categoria categoria);
    Optional<Categoria> findById(Long idCategoria);
}
