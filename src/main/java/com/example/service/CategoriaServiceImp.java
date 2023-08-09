package com.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import com.example.model.Categoria;
import com.example.repository.CategoriaRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoriaServiceImp implements ICategoriaService{

    @Inject
    CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> obtenerCategorias() {
        return this.categoriaRepository.getAll();
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) {
        return this.categoriaRepository.create(categoria);
    }

    @Override
    public Categoria actualizarCategoria(Long idCategoria, Categoria categoria) {
        Optional<Categoria> categoriaExist = Optional.ofNullable(this.categoriaRepository.find(idCategoria)
                .orElseThrow(() -> new NotFoundException("La categoría " + idCategoria + " no fue encontrada.")));

            return this.categoriaRepository.update(categoriaExist.get(),categoria);
    }

    @Override
    public Categoria obtenerCategoriaPorPeso(Double peso){
        List<Categoria> categorias = this.categoriaRepository.getAll().stream()
                                     .sorted((Comparator.comparingDouble(Categoria::getPesoMin)))
                                     .collect(Collectors.toList());

            for (Categoria categoria : categorias) {

                if (peso >= categoria.getPesoMin() && peso <= categoria.getPesoMax()) {
                    return categoria;
                }

                Integer cat = categorias.indexOf(categoria) + 1;
                if (peso > categoria.getPesoMax() && peso < categorias.get(cat).getPesoMin()) {
                    return categoria;
                }

            }

        return categorias.get(0);
    }
}
