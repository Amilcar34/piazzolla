package service;

import DTO.EntrenadorDTO;
import model.Boxeador;
import model.Categoria;
import model.Entrenador;

import java.util.List;

public interface IEntrenadorService {
    List<EntrenadorDTO> getAllEntrenadores();

    Entrenador obtenerEntrenadorPorCategoria(Categoria categoria);

    Boolean addBoxeador(Entrenador entrenador, Boxeador boxeador) throws Exception;
}
