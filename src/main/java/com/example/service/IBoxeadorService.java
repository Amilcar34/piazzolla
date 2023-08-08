package com.example.service;

import com.example.dto.BoxeadorCreateDto;
import com.example.dto.BoxeadorDto;

import java.util.List;
import java.util.Optional;

public interface IBoxeadorService {
    List<BoxeadorDto> getAllBoxeadores();

    BoxeadorDto create(BoxeadorCreateDto boxeador) throws Exception;

    Optional<BoxeadorDto> actualizar(String nombre, BoxeadorDto boxeadorDto);

    Boolean eliminar(String nombre);
}
