package repository;

import jakarta.enterprise.context.ApplicationScoped;
import model.Boxeador;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BoxeadorRepository implements IBoxeadorRepository{

    List<Boxeador> boxeadores = new ArrayList<>();
    @Override
    public List<Boxeador> getAllBoxeadores() {
        return boxeadores;
    }

    @Override
    public Boxeador create(Boxeador boxeador) {
        this.boxeadores.add(boxeador);
        return boxeador;
    }
}
