package service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import model.Boxeador;
import model.Categoria;
import repository.CategoriaRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

        //TODO revisar si existe una forma optima de cargar
        categoriaExist.get().nombre = categoria.nombre;
        categoriaExist.get().pesoMax = categoria.pesoMax;
        categoriaExist.get().pesoMin = categoria.pesoMin;
        
        return categoriaExist;
    }

    @Override
    public Categoria obtenerCategoriaPorPeso(Double peso){
        List<Categoria> categorias = this.categoriaRepository.getAllCategorias();

        if(peso < categorias.get(0).pesoMin){
            return categorias.get(0);
        }

        for (Categoria categoria: categorias) {
            if(peso >= categoria.pesoMin && peso <= categoria.pesoMax){
                return categoria;
            }

            Integer cat = categorias.indexOf(categoria)+1;
            if (peso > categoria.pesoMax && peso < categorias.get(cat).pesoMin){
                return categoria;
            }
        }

        return null;
    }
}
