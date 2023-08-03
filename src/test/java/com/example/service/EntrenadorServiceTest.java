package com.example.service;


import com.example.dto.BoxeadorDTO;
import com.example.dto.BoxeadorInfoDTO;
import com.example.dto.EntrenadorDTO;
import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;
import com.example.repository.EntrenadorRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntrenadorServiceTest {

    @Inject
    EntrenadorServiceImp entrenadorServiceImp;

    @InjectMock
    EntrenadorRepository entrenadorRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Test
    public void obtenerEntrenadores(){
        //setup
        List<Entrenador> entrenadors = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Agus",null,null);

        entrenadors.add(entrenador);

        //config
        Mockito.when(this.entrenadorRepository.getAll()).thenReturn(entrenadors);

        //execute
        List<EntrenadorDTO> entrenadorDTOS = this.entrenadorServiceImp.getAllEntrenadores();

        //verify
        assertNotNull(entrenadorDTOS);
        assertEquals(1,entrenadorDTOS.size());
        assertEquals(entrenador.getNombre(), entrenadorDTOS.get(0).getNombre());
    }

    @Test
    public void obtenerEntrenadorPorCategoria(){
        //setup
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria = new Categoria(1L,"Mosca",48.988,50.802);

        categorias.add(categoria);

        Entrenador entrenador = new Entrenador("Agus",categorias,new ArrayList<Boxeador>());

        //config
        Mockito.when(this.entrenadorRepository.obtenerEntrenadorPorCategoria(categoria)).thenReturn(entrenador);

        //execute
        EntrenadorDTO entrenadorDTO = this.entrenadorServiceImp.obtenerEntrenadorPorCategoria(categoria);

        //verify
        assertEquals(entrenador.getNombre(), entrenadorDTO.getNombre());

    }

    @Test
    public void queSePuedaAgregarBoxeador() throws Exception {
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);

        List<Boxeador> boxeadors = new ArrayList<>();
        Boxeador boxeador = new  Boxeador();

        boxeadors.add(boxeador);
        boxeadors.add(boxeador);
        boxeadors.add(boxeador);
        boxeadors.add(boxeador);

        Entrenador entrenador = new Entrenador("Juan",categorias, boxeadors);


        EntrenadorDTO entrenadorDTO = modelMapper.map(entrenador,EntrenadorDTO.class);
        BoxeadorInfoDTO boxeadorInfoDTO = modelMapper.map(boxeador, BoxeadorInfoDTO.class);

        //config
        Mockito.when(this.entrenadorRepository.addBoxeador(entrenador,boxeador)).thenReturn(entrenador);

        //execute
        EntrenadorDTO response = this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorInfoDTO);

            //verify
        assertEquals(4, response.getBoxeadores().size());
        assertNotNull(response.getBoxeadores());
        assertEquals(entrenadorDTO, response);

    }

    @Test
    public void queNoSePuedaAgregarBoxeador() throws Exception {
        //setup
        List<Boxeador> boxeadors = new ArrayList<>();
        Boxeador boxeador = new  Boxeador();

        boxeadors.add(boxeador);
        boxeadors.add(boxeador);
        boxeadors.add(boxeador);
        boxeadors.add(boxeador);
        boxeadors.add(boxeador);

        Entrenador entrenador = new Entrenador("Juan",null, boxeadors);

        //config
        Mockito.when(this.entrenadorRepository.addBoxeador(entrenador,boxeador)).thenReturn(entrenador);

        EntrenadorDTO entrenadorDTO = modelMapper.map(entrenador,EntrenadorDTO.class);
        BoxeadorInfoDTO boxeadorDTO = modelMapper.map(boxeador,BoxeadorInfoDTO.class);

        //execute
        EntrenadorDTO finalEntrenadorDTO = entrenadorDTO;
        Assertions.assertThrows(Exception.class, () -> {
             this.entrenadorServiceImp.addBoxeador(finalEntrenadorDTO,boxeadorDTO);
        });


    }

    @Test
    public void queSePuedaEliminarBoxeador(){
        Boxeador boxeador = new  Boxeador();

        Entrenador entrenador = new Entrenador();

        Mockito.when(this.entrenadorRepository.deleteBoxeador(entrenador,boxeador)).thenReturn(true);

        EntrenadorDTO entrenadorDTO = modelMapper.map(entrenador,EntrenadorDTO.class);
        BoxeadorDTO boxeadorInfoDTO = modelMapper.map(boxeador, BoxeadorDTO.class);

        var result = this.entrenadorServiceImp.eliminarBoxeador(entrenadorDTO,boxeadorInfoDTO);

        assertTrue(result);
    }

    @Test
    public void queNoSePuedaEliminarBoxeador(){
        Boxeador boxeador = new  Boxeador();

        Entrenador entrenador = new Entrenador();

        Mockito.when(this.entrenadorRepository.deleteBoxeador(entrenador,boxeador)).thenReturn(false);

        EntrenadorDTO entrenadorDTO = modelMapper.map(entrenador,EntrenadorDTO.class);
        BoxeadorDTO boxeadorInfoDTO = modelMapper.map(boxeador, BoxeadorDTO.class);

        var result = this.entrenadorServiceImp.eliminarBoxeador(entrenadorDTO,boxeadorInfoDTO);

        assertFalse(result);
    }

}
