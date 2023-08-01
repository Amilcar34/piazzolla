package com.example.service;

import com.example.DTO.BoxeadorDTO;

import java.util.List;

public interface IBoxeadorService {
    List<BoxeadorDTO> getAllBoxeadores();

    BoxeadorDTO create(BoxeadorDTO boxeador) throws Exception;
}
