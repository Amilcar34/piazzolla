package com.example.service;


import com.example.dto.informe.EntrenadorInformeDto;
import com.example.dto.informe.InformeDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.example.repository.BoxeadorRepository;
import com.example.repository.EntrenadorRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InformeDiarioServiceImp {

    @Inject
    BoxeadorRepository boxeadorRepository;

    @Inject
    EntrenadorRepository entrenadorRepository;

    public InformeDto informeDiario() {

        Integer totalBoxeadores = this.boxeadorRepository.cantBoxeadoresPorDia();


        InformeDto informeDTO = new InformeDto();
        informeDTO.setDia(new Date(System.currentTimeMillis()));
        informeDTO.setTotalBoxeadores(totalBoxeadores);
        informeDTO.setEntrenadores(this.obtenerEntrenadoresInforme());

        return informeDTO;
    }


    private List<EntrenadorInformeDto> obtenerEntrenadoresInforme() {
        List<EntrenadorInformeDto> entrenadorInformeDtos = new ArrayList<>();

        this.entrenadorRepository.getAll().forEach(entrenador -> {
                    Integer cant = this.entrenadorRepository.obtenerBoxeadoresDelDia(entrenador);
                    entrenadorInformeDtos.add(new EntrenadorInformeDto(entrenador.getNombre(), cant));
                }
        );

        return entrenadorInformeDtos;
    }
}
