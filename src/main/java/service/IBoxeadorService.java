package service;

import model.Boxeador;

import java.util.List;

public interface IBoxeadorService {
    List<Boxeador> getAllBoxeadores();

    Boxeador create(Boxeador boxeador);
}
