package com.example.service;

import com.example.dto.BoxeadorDTO;

import java.util.List;

public interface IBoxeadorService {
    List<BoxeadorDTO> getAllBoxeadores();

    BoxeadorDTO create(BoxeadorDTO boxeador) throws Exception;

    Boolean eliminar(String nombre);
}
