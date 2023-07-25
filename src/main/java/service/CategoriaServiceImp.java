package service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import model.Categoria;
import repository.CategoriaRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class CategoriaServiceImp implements ICategoriaService{

    @Inject
    CategoriaRepository categoriaRepository;

    @Override
    public Set<Categoria> getAllCategorias() {
        return this.categoriaRepository.getAllCategorias();
    }

    @Override
    public void create(Categoria categoria) {
        this.categoriaRepository.create(categoria);
    }

    @Override
    public void update(Long idCategoria, Categoria categoria) {
        Optional<Categoria> categoriaExist = Optional.ofNullable(this.categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new NotFoundException("La categoría no fue encontrada.")));

        //TODO revisar si existe una forma optima de cargar
        categoriaExist.get().nombre = categoria.nombre;
        categoriaExist.get().pesoMax = categoria.pesoMax;
        categoriaExist.get().pesoMin = categoria.pesoMin;
    }
}
