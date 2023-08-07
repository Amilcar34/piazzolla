package com.example.service;


import com.example.dto.BoxeadorDto;
import com.example.dto.BoxeadorSinEntreDto;
import com.example.dto.EntrenadorDto;
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
        List<EntrenadorDto> entrenadorDtos = this.entrenadorServiceImp.getAllEntrenadores();

        //verify
        assertNotNull(entrenadorDtos);
        assertEquals(1, entrenadorDtos.size());
        assertEquals(entrenador.getNombre(), entrenadorDtos.get(0).getNombre());
    }

    @Test
    public void crearEntrenador() throws Exception {
        //setup
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria = new Categoria(11L,"Junior",30.0,35.0);

        categorias.add(categoria);

        Entrenador entrenador = new Entrenador("Pedro",categorias,new ArrayList<Boxeador>());

        //config
        Mockito.when(this.entrenadorRepository.create(entrenador)).thenReturn(true);

        EntrenadorDto entrenadorDto = modelMapper.map(entrenador,EntrenadorDto.class);

        var result = this.entrenadorServiceImp.crearEntrenador(entrenadorDto);

        assertTrue(result);
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
        EntrenadorDto entrenadorDTO = this.entrenadorServiceImp.obtenerEntrenadorPorCategoria(categoria);

        //verify
        assertEquals(entrenador.getNombre(), entrenadorDTO.getNombre());

    }

    @Test
    public void queNoSePuedaObtenerEntrenadorPorCategoriaInexistente(){
        //setup
        Categoria categoria = new Categoria(9L,"Nueva",48.988,50.802);

        //config
        Mockito.when(this.entrenadorRepository.obtenerEntrenadorPorCategoria(categoria)).thenReturn(null);

        //execute
        EntrenadorDto entrenadorDTO = this.entrenadorServiceImp.obtenerEntrenadorPorCategoria(categoria);

        //verify
        assertNull(entrenadorDTO);

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


        EntrenadorDto entrenadorDTO = modelMapper.map(entrenador, EntrenadorDto.class);
        BoxeadorSinEntreDto boxeadorSinEntreDTO = modelMapper.map(boxeador, BoxeadorSinEntreDto.class);

        //config
        Mockito.when(this.entrenadorRepository.addBoxeador(entrenador,boxeador)).thenReturn(entrenador);

        //execute
        EntrenadorDto response = this.entrenadorServiceImp.addBoxeador(entrenadorDTO, boxeadorSinEntreDTO);

            //verify
        assertEquals(4, response.getBoxeadores().size());
        assertNotNull(response.getBoxeadores());
        assertEquals(entrenadorDTO, response);

    }

    @Test
    public void queNoSePuedaAgregarMasDe5Boxeador() {
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

        EntrenadorDto entrenadorDTO = modelMapper.map(entrenador, EntrenadorDto.class);
        BoxeadorSinEntreDto boxeadorDTO = modelMapper.map(boxeador, BoxeadorSinEntreDto.class);

        //execute
        EntrenadorDto finalEntrenadorDto = entrenadorDTO;
        Assertions.assertThrows(Exception.class, () -> {
             this.entrenadorServiceImp.addBoxeador(finalEntrenadorDto,boxeadorDTO);
        });


    }

    @Test
    public void queSePuedaEliminarBoxeador(){
        Boxeador boxeador = new  Boxeador();

        Entrenador entrenador = new Entrenador();

        Mockito.when(this.entrenadorRepository.deleteBoxeador(entrenador,boxeador)).thenReturn(true);

        EntrenadorDto entrenadorDTO = modelMapper.map(entrenador, EntrenadorDto.class);
        BoxeadorDto boxeadorInfoDTO = modelMapper.map(boxeador, BoxeadorDto.class);

        var result = this.entrenadorServiceImp.eliminarBoxeador(entrenadorDTO,boxeadorInfoDTO);

        assertTrue(result);
    }

    @Test
    public void queNoSePuedaEliminarBoxeador(){
        Boxeador boxeador = new  Boxeador();

        Entrenador entrenador = new Entrenador();

        Mockito.when(this.entrenadorRepository.deleteBoxeador(entrenador,boxeador)).thenReturn(false);

        EntrenadorDto entrenadorDTO = modelMapper.map(entrenador, EntrenadorDto.class);
        BoxeadorDto boxeadorInfoDTO = modelMapper.map(boxeador, BoxeadorDto.class);

        var result = this.entrenadorServiceImp.eliminarBoxeador(entrenadorDTO,boxeadorInfoDTO);

        assertFalse(result);
    }

}
