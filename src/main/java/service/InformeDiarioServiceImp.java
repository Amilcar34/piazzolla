package service;


import DTO.EntrenadorInformeDTO;
import DTO.InformeDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.Entrenador;
import repository.BoxeadorRepository;
import repository.EntrenadorRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InformeDiarioServiceImp {

    @Inject
    BoxeadorRepository boxeadorRepository;

    @Inject
    EntrenadorRepository entrenadorRepository;

    public InformeDTO informeDiario() {

        Integer totalBoxeadores = this.boxeadorRepository.cantBoxeadoresPorDia();


        InformeDTO informeDTO = new InformeDTO();
        informeDTO.setDia(new Date(System.currentTimeMillis()));
        informeDTO.setTotalBoxeadores(totalBoxeadores);
        informeDTO.setEntrenadores(this.obtenerEntrenadoresInforme());

        return informeDTO;
    }


    private List<EntrenadorInformeDTO> obtenerEntrenadoresInforme() {
        List<EntrenadorInformeDTO> entrenadorInformeDTOS = new ArrayList<>();

        this.entrenadorRepository.getAllEntrenadores().forEach(entrenador -> {
                    Integer cant = this.entrenadorRepository.obtenerBoxeadoresDelDia(entrenador);
                    entrenadorInformeDTOS.add(new EntrenadorInformeDTO(entrenador.getNombre(), cant));
                }
        );

        return entrenadorInformeDTOS;
    }
}
