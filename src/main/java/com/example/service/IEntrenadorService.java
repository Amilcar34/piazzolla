package com.example.service;

import com.example.dto.BoxeadorDto;
import com.example.dto.BoxeadorSinEntreDto;
import com.example.dto.EntrenadorDto;
import com.example.model.Categoria;
import com.example.model.Entrenador;

import java.util.List;

public interface IEntrenadorService {
    List<EntrenadorDto> getAllEntrenadores();

    Boolean crearEntrenador(EntrenadorDto entrenadorDto) throws Exception;

    Boolean existEntrenadorConCategoria(List<Categoria> categorias);

    EntrenadorDto obtenerEntrenadorPorCategoria(Categoria categoria);

    EntrenadorDto addBoxeador(EntrenadorDto entrenadorDTO, BoxeadorSinEntreDto boxeadorDTO) throws Exception;

    Boolean eliminarBoxeador(EntrenadorDto entrenadorDTO, BoxeadorDto boxeadorDTO);
}
