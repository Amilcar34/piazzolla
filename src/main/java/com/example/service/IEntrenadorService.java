package com.example.service;

import com.example.dto.BoxeadorDto;
import com.example.dto.BoxeadorSinEntreDto;
import com.example.dto.EntrenadorDto;
import com.example.model.Categoria;

import java.util.List;

public interface IEntrenadorService {
    List<EntrenadorDto> getAllEntrenadores();

    EntrenadorDto obtenerEntrenadorPorCategoria(Categoria categoria);

    EntrenadorDto addBoxeador(EntrenadorDto entrenadorDTO, BoxeadorSinEntreDto boxeadorDTO) throws Exception;

    Boolean eliminarBoxeador(EntrenadorDto entrenadorDTO, BoxeadorDto boxeadorDTO);
}
