package com.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import com.example.model.Categoria;
import com.example.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoriaServiceImp implements ICategoriaService{

    @Inject
    CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> getAllCategorias() {
        return this.categoriaRepository.getAllCategorias();
    }

    @Override
    public Categoria create(Categoria categoria) {
      return this.categoriaRepository.save(categoria);
    }

    @Override
    public Optional<Categoria> update(Long idCategoria, Categoria categoria) {
        Optional<Categoria> categoriaExist = Optional.ofNullable(this.categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new NotFoundException("La categoría " + idCategoria + " no fue encontrada.")));

        categoriaExist.get().setNombre(categoria.getNombre());
        categoriaExist.get().setPesoMax(categoria.getPesoMax());
        categoriaExist.get().setPesoMin(categoria.getPesoMin());
        
        return categoriaExist;
    }

    @Override
    public Categoria obtenerCategoriaPorPeso(Double peso){
        List<Categoria> categorias = this.categoriaRepository.getAllCategorias();

        if(peso < categorias.get(0).getPesoMin()){
            return categorias.get(0);
        }

        for (Categoria categoria: categorias) {
            if(peso >= categoria.getPesoMin() && peso <= categoria.getPesoMax()){
                return categoria;
            }

            Integer cat = categorias.indexOf(categoria)+1;
            if (peso > categoria.getPesoMax() && peso < categorias.get(cat).getPesoMin()){
                return categoria;
            }
        }

        return null;
    }
}
