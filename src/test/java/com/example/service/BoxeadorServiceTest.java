package com.example.service;

import com.example.dto.BoxeadorCreateDto;
import com.example.dto.BoxeadorDto;
import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;
import com.example.repository.BoxeadorRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Mockito.when(this.boxeadorRepository.getAll()).thenReturn(boxeadores);

        List<BoxeadorDto> boxeadorDtos = this.boxeadorServiceImp.getAllBoxeadores();

        //verify
        assertNotNull(boxeadorDtos);
        assertEquals(1, boxeadorDtos.size());
        assertEquals(boxeador.getNombre(), boxeadorDtos.get(0).getNombre());
        assertEquals(boxeador.getPeso(), boxeadorDtos.get(0).getPeso());
    }

    @Test
    void crearBoxeador() throws Exception {

        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(3L, "Pluma", 55.338, 57.152);
        Categoria cat2 = new Categoria(4L, "Ligero", 58.967, 61.237);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Pablo", categorias, null);
        Boxeador boxeador = new Boxeador("Nico", 57D, cat1, entrenador, new Date(System.currentTimeMillis()));

        //Config
        Mockito.when(boxeadorRepository.create(boxeador)).thenReturn(boxeador);

        //execute
        BoxeadorCreateDto boxeadorCreateDto = modelMapper.map(boxeador,BoxeadorCreateDto.class);

        BoxeadorDto boxeadorDTO = this.boxeadorServiceImp.create(boxeadorCreateDto);

        //assertions
        assertEquals(cat1, boxeadorDTO.getCategoria());
        assertEquals(entrenador.getNombre(), boxeadorDTO.getEntrenador().getNombre());
        assertEquals(new Date(System.currentTimeMillis()).toLocalDate(), boxeadorDTO.getFechaIngreso().toLocalDate());


    }

    @Test
    public void actualizarBoxeador(){
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(1L, "Mosca", 48.988, 50.802);
        Categoria cat2 = new Categoria(2L, "Gallo", 52.163, 53.525);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Agus", categorias, null);
        Entrenador entrenador1 = new Entrenador("Leo", categorias, null);

        Boxeador boxeador = new Boxeador("Luca",50D,cat1,entrenador,new Date(System.currentTimeMillis()));
        Boxeador boxeadorModificado = new Boxeador("Lucas",54D,cat2,entrenador1,new Date(116, 5,3));


        //config
        Mockito.when(this.boxeadorRepository.find(boxeador.getNombre())).thenReturn(Optional.of(boxeador));

        //execute
        Optional<BoxeadorDto> boxeadorActualizado = this.boxeadorServiceImp.actualizar(boxeador.getNombre(),modelMapper.map(boxeadorModificado,BoxeadorDto.class));

        //verify
        assertNotNull(boxeadorActualizado);
        assertTrue(boxeadorActualizado.isPresent()); // Ensure the Optional is not empty

        BoxeadorDto boxeadorActualizadoObj = boxeadorActualizado.get();
        assertEquals(boxeadorModificado.getNombre(), boxeadorActualizadoObj.getNombre());
        assertEquals(boxeadorModificado.getPeso(), boxeadorActualizadoObj.getPeso());
        assertEquals(boxeadorModificado.getCategoria(), boxeadorActualizadoObj.getCategoria());
        assertEquals(boxeadorModificado.getEntrenador().getNombre(), boxeadorActualizadoObj.getEntrenador().getNombre());
        assertEquals(boxeadorModificado.getFechaIngreso(), boxeadorActualizadoObj.getFechaIngreso());

    }

    @Test
    public void queNoSePuedaModificarBoxeadorInexistente(){
        //setup
        Boxeador boxeador = new Boxeador("Luca",50D,null,null,new Date(System.currentTimeMillis()));
        Boxeador boxeadorModificado = new Boxeador("Lucas",54D,null,null,new Date(116, 5,3));

        //config
        Mockito.when(this.boxeadorRepository.find(boxeador.getNombre())).thenReturn(Optional.empty());

        //execute

        try {
            this.boxeadorServiceImp.actualizar(boxeador.getNombre(), modelMapper.map(boxeadorModificado,BoxeadorDto.class));
        }catch (NotFoundException e){
            assertEquals("El Boxeador Luca no fue encontrado.",e.getMessage());
        }

    }

    @Test
    public void queSePuedaEliminarBoxeador(){
        //setup
        List<Boxeador> boxeadores = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Agus", null, null);
        Boxeador boxeador = new Boxeador("Nicol", 50D, null, entrenador, null);

        boxeadores.add(boxeador);
        //config
        Mockito.when(this.boxeadorRepository.getAll()).thenReturn(boxeadores);
        Mockito.when(this.boxeadorRepository.find(boxeador.getNombre())).thenReturn(Optional.of(boxeador));
        Mockito.when(this.boxeadorRepository.delete(boxeador)).thenReturn(true);

        //execute
        var valor = this.boxeadorServiceImp.eliminar(boxeador.getNombre());

        //verify
        assertTrue(valor);
    }

    @Test
    public void queNoSePuedaEliminarBoxeador(){
        //setup
        List<Boxeador> boxeadores = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Agus", null, null);
        Boxeador boxeador = new Boxeador("Nicol", 50D, null, entrenador, null);

        boxeadores.add(boxeador);
        //config
        Mockito.when(this.boxeadorRepository.getAll()).thenReturn(boxeadores);
        Mockito.when(this.boxeadorRepository.find(boxeador.getNombre())).thenReturn(Optional.ofNullable(boxeador));
        Mockito.when(this.boxeadorRepository.delete(boxeador)).thenReturn(false);

        //execute
        var valor = this.boxeadorServiceImp.eliminar(boxeador.getNombre());

        //verify
        assertFalse(valor);
    }

    @Test
    public void queNoSePuedaEliminarBoxeadorInexistente(){
        //setup
        List<Boxeador> boxeadores = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Agus", null, null);

        Boxeador boxeador = new Boxeador("Nico", 57D, null, null, null);
        Boxeador boxeadorInex = new Boxeador("Nicol", 50D, null, entrenador, null);

        boxeadores.add(boxeador);

        //config
        Mockito.when(this.boxeadorRepository.getAll()).thenReturn(boxeadores);
        Mockito.when(this.boxeadorRepository.find(boxeadorInex.getNombre())).thenReturn(Optional.empty());


        try {
            this.boxeadorServiceImp.eliminar(boxeadorInex.getNombre());
        } catch (NotFoundException e) {
            assertEquals("El boxeador Nicol no fue encontrado.",e.getMessage());
        }

    }

    @Test
    public void queNoSePuedaCrearMasDe5BoxeadoresPorEntrenador() throws Exception {

        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(5L,"Welter",63.503,66.678);
        Categoria cat2 = new Categoria(6L,"Mediano",69.853,72.562);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Flor",categorias,null);
        Boxeador boxeador = new Boxeador("Nico", 64D, cat1, entrenador, new Date(System.currentTimeMillis()));

        Mockito.when(this.boxeadorRepository.create(boxeador)).thenReturn(boxeador);

        BoxeadorDto boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorCreateDto.class));
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorCreateDto.class));
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorCreateDto.class));
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorCreateDto.class));
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorCreateDto.class));



        assertThrows(Exception.class, () -> this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorCreateDto.class)));

    }
}
