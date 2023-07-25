package service;

import model.Categoria;

import java.util.Set;

public interface ICategoriaService {
    Set<Categoria> getAllCategorias();
    void create(Categoria categoria);
    void update(Long idCategoria,Categoria categoria);
}
