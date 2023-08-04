package com.example.service;

import com.example.dto.informe.InformeDto;
import com.example.model.Entrenador;
import com.example.repository.BoxeadorRepository;
import com.example.repository.EntrenadorRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class InformeDiarioServiceTest {

    @InjectMock
    BoxeadorRepository boxeadorRepository;

    @InjectMock
    EntrenadorRepository entrenadorRepository;

    @Inject
    InformeDiarioServiceImp informeDiarioServiceImp;

    @Test
    public void informeDiario(){

        //setup
        List<Entrenador> entrenadors = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Agus",null,null);

        entrenadors.add(entrenador);

        //config
        Mockito.when(this.boxeadorRepository.cantBoxeadoresPorDia()).thenReturn(2);
        Mockito.when(this.entrenadorRepository.getAll()).thenReturn(entrenadors);
        Mockito.when(this.entrenadorRepository.obtenerBoxeadoresDelDia(entrenador)).thenReturn(2);

        //execute
        InformeDto informeDTO = this.informeDiarioServiceImp.informeDiario();

        //verify
       assertEquals(2,informeDTO.getTotalBoxeadores());
       assertEquals(entrenador.getNombre(),informeDTO.getEntrenadores().get(0).getNombre());
       assertEquals(new Date(System.currentTimeMillis()).toLocalDate(),informeDTO.getDia().toLocalDate());

    }

}
