package service;

import DTO.BoxeadorDTO;
import DTO.BoxeadorInfoDTO;
import DTO.EntrenadorDTO;
import model.Boxeador;
import model.Categoria;
import model.Entrenador;

import java.util.List;

public interface IEntrenadorService {
    List<EntrenadorDTO> getAllEntrenadores();

    EntrenadorDTO obtenerEntrenadorPorCategoria(Categoria categoria);

    EntrenadorDTO addBoxeador(EntrenadorDTO entrenadorDTO, BoxeadorInfoDTO boxeadorDTO) throws Exception;
}
