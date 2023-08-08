package com.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.example.model.Categoria;

import java.util.*;


@ApplicationScoped
public class CategoriaRepository implements IDAORepository<Categoria, Long> {

    List<Categoria> categorias = new ArrayList<>();

    public CategoriaRepository() {
        categorias.add(new Categoria(1L, "Mosca", 48.988, 50.802));
        categorias.add(new Categoria(2L, "Gallo", 52.163, 53.525));
        categorias.add(new Categoria(3L, "Pluma", 55.338, 57.152));
        categorias.add(new Categoria(4L, "Ligero", 58.967, 61.237));
        categorias.add(new Categoria(5L, "Welter", 63.503, 66.678));
        categorias.add(new Categoria(6L, "Mediano", 69.853, 72.562));
        categorias.add(new Categoria(7L, "Mediopesado", 76.205, 79.378));
        categorias.add(new Categoria(8L, "Pesado", 91D, Categoria.SIN_LIMITE));
    }

    @Override
    public List<Categoria> getAll() {
        return this.categorias;
    }

    @Override
    public Boolean create(Categoria categoria) {
       return this.categorias.add(categoria);
    }

    @Override
    public Optional<Categoria> find(Long idCategoria) {
        return this.categorias.stream().filter(c -> Objects.equals(c.getId(), idCategoria)).findFirst();
    }

    @Override
    public Boolean delete(Categoria categoria) {
        return this.categorias.remove(categoria);
    }


}
