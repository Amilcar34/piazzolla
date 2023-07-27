package service;

import DTO.BoxeadorDTO;
import model.Boxeador;

import java.util.List;

public interface IBoxeadorService {
    List<Boxeador> getAllBoxeadores();

    BoxeadorDTO create(BoxeadorDTO boxeador);
}
