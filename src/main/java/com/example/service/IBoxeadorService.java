package com.example.service;

import com.example.dto.BoxeadorCreateDto;
import com.example.dto.BoxeadorDto;

import java.util.List;

public interface IBoxeadorService {
    List<BoxeadorDto> getAllBoxeadores();

    BoxeadorDto create(BoxeadorCreateDto boxeador) throws Exception;

    Boolean eliminar(String nombre);
}
