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
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void queSePuedaCrearEntrenadorCon2Categorias() throws Exception {
        //setup
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria = new Categoria(11L,"Junior",30.0,35.0);
        Categoria categoria2 = new Categoria(11L,"Mini",36.0,37.0);

        categorias.add(categoria);
        categorias.add(categoria2);

        Entrenador entrenador = new Entrenador("Pedro",categorias,new ArrayList<Boxeador>());

        //config
        Mockito.when(this.entrenadorRepository.create(entrenador)).thenReturn(true);

        EntrenadorDto entrenadorDto = modelMapper.map(entrenador,EntrenadorDto.class);

        var result = this.entrenadorServiceImp.crearEntrenador(entrenadorDto);

        assertTrue(result);
        assertEquals(2,categorias.size());
    }

    @Test
    public void queNoSePuedaCrearEntrenadorConCategoriaRepetida()  {
        //setup
        List<Categoria> categorias = new ArrayList<>();
        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);

        categorias.add(cat1);
        Entrenador entrenadorExist = new Entrenador("Juan",categorias,new ArrayList<Boxeador>());
        Entrenador entrenador = new Entrenador("Pedro",categorias,new ArrayList<Boxeador>());

        //config
        Mockito.when(this.entrenadorRepository.create(entrenador)).thenReturn(false);
        Mockito.when(this.entrenadorRepository.obtenerEntrenadorPorCategoria(cat1)).thenReturn(entrenadorExist);

        EntrenadorDto entrenadorDto = modelMapper.map(entrenador,EntrenadorDto.class);

        assertThrows(Exception.class, () ->{
            this.entrenadorServiceImp.crearEntrenador(entrenadorDto);
        });
    }

    @Test
    public void queNoSePuedaCrearEntrenadorConMasDe2Categorias()  {
        //setup
        List<Categoria> categorias = new ArrayList<>();
        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",80.0,81.0);
        Categoria cat3 = new Categoria(9L,"Muy Pesado",90.0,92.0);


        categorias.add(cat1);
        categorias.add(cat2);
        categorias.add(cat3);

        Entrenador entrenador = new Entrenador("Pedro",categorias,new ArrayList<Boxeador>());

        //config
        Mockito.when(this.entrenadorRepository.create(entrenador)).thenReturn(false);

        EntrenadorDto entrenadorDto = modelMapper.map(entrenador,EntrenadorDto.class);

        assertThrows(Exception.class, () ->{
            var result = this.entrenadorServiceImp.crearEntrenador(entrenadorDto);
            assertFalse(result);
            assertNotEquals(2,categorias.size());
        });
    }

    @Test
    public void queSePuedaEliminarEntrenador(){
        //setup
        List<Entrenador> entrenadors = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Agus", null, null);

        entrenadors.add(entrenador);

        //config
        Mockito.when(this.entrenadorRepository.getAll()).thenReturn(entrenadors);
        Mockito.when(this.entrenadorRepository.find(entrenador.getNombre())).thenReturn(Optional.of(entrenador));
        Mockito.when(this.entrenadorRepository.delete(entrenador)).thenReturn(true);

        //execute
        var valor = this.entrenadorServiceImp.eliminarEntrenador(entrenador.getNombre());

        //verify
        assertTrue(valor);
    }

    @Test
    public void queNoSePuedaEliminarEntrenador(){
        //setup
        List<Entrenador> entrenadores = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Agus", null, null);

        entrenadores.add(entrenador);
        //config
        Mockito.when(this.entrenadorRepository.getAll()).thenReturn(entrenadores);
        Mockito.when(this.entrenadorRepository.find(entrenador.getNombre())).thenReturn(Optional.ofNullable(entrenador));
        Mockito.when(this.entrenadorRepository.delete(entrenador)).thenReturn(false);

        //execute
        var valor = this.entrenadorServiceImp.eliminarEntrenador(entrenador.getNombre());

        //verify
        assertFalse(valor);
    }

    @Test
    public void queNoSePuedaEliminarEntrenadorInexistente(){
        //setup
        List<Entrenador> entrenadores = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Pedro",null,new ArrayList<Boxeador>());

        entrenadores.add(entrenador);

        //config
        Mockito.when(this.entrenadorRepository.getAll()).thenReturn(entrenadores);
        Mockito.when(this.entrenadorRepository.find(entrenador.getNombre())).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> {
            this.entrenadorServiceImp.eliminarEntrenador(entrenador.getNombre());
        });

    }

    @Test
    public void actualizarEntrenador(){
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(1L, "Mosca", 48.988, 50.802);
        Categoria cat2 = new Categoria(2L, "Gallo", 52.163, 53.525);

        categorias.add(cat1);
        categorias.add(cat2);

        List<Boxeador> boxeadors = new ArrayList<>();

        Boxeador boxeador = new Boxeador("Nico", 57D, null, null, null);
        Boxeador boxeador2 = new Boxeador("Nicol", 50D, null, null, null);

        boxeadors.add(boxeador);
        boxeadors.add(boxeador2);

        Entrenador entrenador = new Entrenador("Agus", categorias, boxeadors);

        //setup modificacion
        List<Categoria> categoriaModificada = new ArrayList<>();

        Categoria cat3 = new Categoria(3L,"Mediopesado",76.205,79.378);
        List<Boxeador> boxeadores = new ArrayList<>();

        Boxeador boxeador3 = new Boxeador("Anto", 57D, null, null, null);

        boxeadores.add(boxeador3);
        categoriaModificada.add(cat3);

        Entrenador entrenadorModificado = new Entrenador("Agustina", categoriaModificada, boxeadores);

        //config
        Mockito.when(this.entrenadorRepository.find(entrenador.getNombre())).thenReturn(Optional.of(entrenador));

        //execute
        Optional<EntrenadorDto> entrenadorActualizado = this.entrenadorServiceImp.actualizarEntrenador(entrenador.getNombre(),modelMapper.map(entrenadorModificado,EntrenadorDto.class));

        //verify
        assertNotNull(entrenadorActualizado);
        assertTrue(entrenadorActualizado.isPresent());

        EntrenadorDto entrenadorDto = entrenadorActualizado.get();
        List<Boxeador> boxeadorActualizado = entrenadorDto.getBoxeadores().stream().map(b -> modelMapper.map(b,Boxeador.class)).collect(Collectors.toList());

        assertEquals(entrenadorModificado.getNombre(), entrenadorDto.getNombre());
        assertEquals(entrenadorModificado.getCategorias(), entrenadorDto.getCategorias());
        assertEquals(entrenadorModificado.getBoxeadores(),boxeadorActualizado );

    }

    @Test
    public void queNoSePuedaModificarBoxeadorInexistente(){
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(1L, "Mosca", 48.988, 50.802);
        Categoria cat2 = new Categoria(2L, "Gallo", 52.163, 53.525);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Agus", categorias, null);
        categorias.remove(cat1);
        Entrenador entrenadorModificado = new Entrenador("Agustina", categorias, new ArrayList<Boxeador>());

        //config
        Mockito.when(this.entrenadorRepository.find(entrenador.getNombre())).thenReturn(Optional.empty());

        //execute

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.entrenadorServiceImp.actualizarEntrenador(entrenador.getNombre(), modelMapper.map(entrenadorModificado,EntrenadorDto.class));
        });

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
