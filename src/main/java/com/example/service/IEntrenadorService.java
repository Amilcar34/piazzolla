package com.example.service;

import com.example.dto.BoxeadorDto;
import com.example.dto.BoxeadorSinEntreDto;
import com.example.dto.EntrenadorDto;
import com.example.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface IEntrenadorService {
    List<EntrenadorDto> getAllEntrenadores();
    Boolean crearEntrenador(EntrenadorDto entrenadorDto) throws Exception;
    Boolean eliminarEntrenador(String nombre);
    Optional<EntrenadorDto> actualizarEntrenador(String nombre, EntrenadorDto entrenadorDto);
    EntrenadorDto obtenerEntrenadorPorCategoria(Categoria categoria);

    EntrenadorDto addBoxeador(EntrenadorDto entrenadorDTO, BoxeadorSinEntreDto boxeadorDTO) throws Exception;

    Boolean eliminarBoxeador(EntrenadorDto entrenadorDTO, BoxeadorDto boxeadorDTO);
}
