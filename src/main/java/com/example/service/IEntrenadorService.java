package com.example.service;

import com.example.DTO.BoxeadorInfoDTO;
import com.example.DTO.EntrenadorDTO;
import com.example.model.Categoria;

import java.util.List;

public interface IEntrenadorService {
    List<EntrenadorDTO> getAllEntrenadores();

    EntrenadorDTO obtenerEntrenadorPorCategoria(Categoria categoria);

    EntrenadorDTO addBoxeador(EntrenadorDTO entrenadorDTO, BoxeadorInfoDTO boxeadorDTO) throws Exception;
}
