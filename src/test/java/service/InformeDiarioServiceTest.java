package service;

import DTO.EntrenadorInformeDTO;
import DTO.InformeDTO;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import model.Boxeador;
import model.Categoria;
import model.Entrenador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.BoxeadorRepository;
import repository.EntrenadorRepository;

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
        Mockito.when(this.entrenadorRepository.getAllEntrenadores()).thenReturn(entrenadors);
        Mockito.when(this.entrenadorRepository.obtenerBoxeadoresDelDia(entrenador)).thenReturn(2);

        //execute
        InformeDTO informeDTO = this.informeDiarioServiceImp.informeDiario();

        //verify
       assertEquals(2,informeDTO.getTotalBoxeadores());
       assertEquals(entrenador.getNombre(),informeDTO.getEntrenadores().get(0).getNombre());

    }

}
