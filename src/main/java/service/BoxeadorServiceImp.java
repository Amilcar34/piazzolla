package service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.Boxeador;
import model.Categoria;
import repository.BoxeadorRepository;

import java.util.List;

@ApplicationScoped
public class BoxeadorServiceImp implements IBoxeadorService {
    @Inject
    BoxeadorRepository boxeadorRepository;

    @Inject
    CategoriaServiceImp categoriaServiceImp;

    @Override
    public List<Boxeador> getAllBoxeadores() {
        return this.boxeadorRepository.getAllBoxeadores();
    }

    @Override
    public Boxeador create(Boxeador boxeador) {
        Categoria categoria = this.categoriaServiceImp.obtenerCategoriaPorPeso(boxeador.getPeso());
        boxeador.setCategoria(categoria);
        return this.boxeadorRepository.create(boxeador);
    }
}
