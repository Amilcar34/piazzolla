package com.example.service;

import com.example.dto.BoxeadorDTO;
import com.example.dto.BoxeadorInfoDTO;
import com.example.dto.EntrenadorDTO;
import com.example.model.Boxeador;
import com.example.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface IEntrenadorService {
    List<EntrenadorDTO> getAllEntrenadores();

    EntrenadorDTO obtenerEntrenadorPorCategoria(Categoria categoria);

    EntrenadorDTO addBoxeador(EntrenadorDTO entrenadorDTO, BoxeadorInfoDTO boxeadorDTO) throws Exception;

    Boolean eliminarBoxeador(EntrenadorDTO entrenadorDTO, BoxeadorDTO boxeadorDTO);
}
