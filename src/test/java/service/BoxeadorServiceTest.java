package service;

import DTO.BoxeadorDTO;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import model.Boxeador;

import model.Categoria;
import model.Entrenador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import repository.BoxeadorRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        Integer cantidadBoxeadores = this.boxeadorServiceImp.getAllBoxeadores().size();

        //verify
        assertEquals(1, cantidadBoxeadores);
    }

    @Test
    public void crearBoxeador() throws Exception {

        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(3L,"Pluma",55.338,57.152);
        Categoria cat2 = new Categoria(4L,"Ligero",58.967,61.237);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Pablo", categorias, null);
        Boxeador boxeador = new Boxeador("Nico", 57D, cat1, entrenador, new Date(System.currentTimeMillis()));

        //config
        Mockito.when(this.boxeadorRepository.create(boxeador)).thenReturn(boxeador);

        BoxeadorDTO boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));

        Boxeador boxeadorObtenido = modelMapper.map(boxeadorDTO, Boxeador.class);

        //verify
        assertEquals(boxeador.getNombre(),boxeadorObtenido.getNombre());

    }

    @Test
    public void queNoSePuedaCrearMasDe5BoxeadoresPorEntrenador() throws Exception {

        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(1L, "Mosca", 48.988, 50.802);
        Categoria cat2 = new Categoria(2L, "Gallo", 52.163, 53.525);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Agus", categorias, null);

        Boxeador boxeador = new Boxeador("Nicol", 50D, cat1, entrenador, new Date(System.currentTimeMillis()));


        Mockito.when(this.boxeadorRepository.create(boxeador)).thenReturn(boxeador);


        BoxeadorDTO boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));
        boxeadorDTO = this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));


        Assertions.assertThrows(Exception.class, () -> {
              this.boxeadorServiceImp.create(modelMapper.map(boxeador, BoxeadorDTO.class));
        });


    }
}
