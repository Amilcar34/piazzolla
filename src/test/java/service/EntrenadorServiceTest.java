package service;


import DTO.BoxeadorDTO;
import DTO.EntrenadorDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import model.Boxeador;
import model.Categoria;
import model.Entrenador;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import repository.EntrenadorRepository;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class EntrenadorServiceTest {

    @InjectMock
    EntrenadorRepository entrenadorRepository;

    @Inject
    EntrenadorServiceImp entrenadorServiceImp;

    ModelMapper modelMapper = new ModelMapper();

    @Test
    public void obtenerEntrenadores(){

        //setup
        List<Entrenador> entrenadores = new ArrayList<>();
        Entrenador entrenador = new Entrenador();

        entrenadores.add(entrenador);

        //config
        Mockito.when(entrenadorRepository.getAllEntrenadores()).thenReturn(entrenadores);

        //execute
        List<EntrenadorDTO> entrenadorDTOS = this.entrenadorServiceImp.getAllEntrenadores();

        //verify
        assertEquals(1,entrenadorDTOS.size());
    }

    @Test
    public void obtenerEntrenadorPorCategoria(){

        //setup
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria = new Categoria(1L,"Mosca",48.988,50.802);

        categorias.add(categoria);

        Entrenador entrenador = new Entrenador("Juan",categorias,new ArrayList<Boxeador>());

        //config
        Mockito.when(entrenadorRepository.obtenerEntrenadorPorCategoria(categoria)).thenReturn(entrenador);

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

        List<Boxeador> boxeadores = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Juan",categorias, boxeadores);

        Boxeador boxeador = new  Boxeador("Nahuel",77D,cat1,entrenador,new Date(System.currentTimeMillis()));

        EntrenadorDTO entrenadorDTO = modelMapper.map(entrenador,EntrenadorDTO.class);
        BoxeadorDTO boxeadorDTO = modelMapper.map(boxeador,BoxeadorDTO.class);


        //execute
        this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
        Boolean valor = this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);

        //verify
        assertEquals(true,valor);
    }

    @Test
    public void queNoSePuedaAgregarBoxeador() throws Exception {
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);

        List<Boxeador> boxeadores = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Juan",categorias, boxeadores);

        Boxeador boxeador = new  Boxeador("Nahuel",77D,cat1,entrenador,new Date(System.currentTimeMillis()));

        EntrenadorDTO entrenadorDTO = modelMapper.map(entrenador,EntrenadorDTO.class);
        BoxeadorDTO boxeadorDTO = modelMapper.map(boxeador,BoxeadorDTO.class);


        //execute
        this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
        this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
        this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
        this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
        this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
        this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
        Boolean valor = this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);

        //verify
        assertEquals(false,valor);
    }

}
