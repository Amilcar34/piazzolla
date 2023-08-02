package com.example.service;

import com.example.DTO.BoxeadorDTO;
import com.example.DTO.EntrenadorInfoDTO;
import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;
import com.example.repository.BoxeadorRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class BoxeadorServiceTest {

    @Inject
    BoxeadorServiceImp boxeadorServiceImp;

    @InjectMock
    BoxeadorRepository boxeadorRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Test
    public void obtenerBoxeadores() {

        //setup
        List<Boxeador> boxeadores = new ArrayList<>();
        Boxeador boxeador = new Boxeador("Nicol", 50D, null, null, null);

        boxeadores.add(boxeador);

        //config
        Mockito.when(this.boxeadorRepository.getAllBoxeadores()).thenReturn(boxeadores);

        List<BoxeadorDTO> boxeadorDTOS = this.boxeadorServiceImp.getAllBoxeadores();

        //verify
        assertNotNull(boxeadorDTOS);
        assertEquals(1, boxeadorDTOS.size());
        assertEquals(boxeador.getNombre(), boxeadorDTOS.get(0).getNombre());
        assertEquals(boxeador.getPeso(), boxeadorDTOS.get(0).getPeso());
    }

    @Test
    public void crearBoxeador() throws Exception {

        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(3L, "Pluma", 55.338, 57.152);
        Categoria cat2 = new Categoria(4L, "Ligero", 58.967, 61.237);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Pablo", categorias, null);
        Boxeador boxeador = new Boxeador("Nico", 57D, cat1, entrenador, new Date(System.currentTimeMillis()));

        //config
        Mockito.when(this.boxeadorRepository.create(boxeador)).thenReturn(boxeador);

        BoxeadorDTO boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));
        Boxeador boxeadorObtenido = modelMapper.map(boxeadorDTO, Boxeador.class);

        //assertions
        assertEquals(cat1, boxeadorDTO.getCategoria());
        assertEquals(entrenador.getNombre(), boxeadorDTO.getEntrenador().getNombre());
        assertEquals(boxeador.getFechaIngreso().toLocalDate(), boxeadorDTO.getFechaIngreso().toLocalDate());

        Mockito.verify(boxeadorRepository).create(boxeador);


    }

   /* @Test
    public void queNoSePuedaCrearMasDe5BoxeadoresPorEntrenador() throws Exception {

        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(1L, "Mosca", 48.988, 50.802);
        Categoria cat2 = new Categoria(2L, "Gallo", 52.163, 53.525);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Agus", categorias, null);

        Boxeador boxeador = new Boxeador("Nicol", 50D, cat1, entrenador, new Date(System.currentTimeMillis()));

        Mockito.when(this.boxeadorRepository.create(boxeador)).thenReturn(boxeador);


        BoxeadorDTO boxeadorDTO;
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));

        assertNotNull(boxeadorDTO.getEntrenador());
        assertNotNull(boxeadorDTO.getCategoria());
        assertNotNull(boxeadorDTO.getFechaIngreso());

        assertEquals(boxeador.getEntrenador().getNombre() , boxeadorDTO.getEntrenador().getNombre());



        Assertions.assertThrows(Exception.class, () -> {
              this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));
        });

    }*/
}
