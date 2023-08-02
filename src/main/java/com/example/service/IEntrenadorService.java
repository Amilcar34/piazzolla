package com.example.service;

import com.example.dto.BoxeadorInfoDTO;
import com.example.dto.EntrenadorDTO;
import com.example.model.Categoria;

import java.util.List;

public interface IEntrenadorService {
    List<EntrenadorDTO> getAllEntrenadores();

    EntrenadorDTO obtenerEntrenadorPorCategoria(Categoria categoria);

    EntrenadorDTO addBoxeador(EntrenadorDTO entrenadorDTO, BoxeadorInfoDTO boxeadorDTO) throws Exception;
}
