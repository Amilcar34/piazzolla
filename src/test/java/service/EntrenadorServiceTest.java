package service;


import DTO.BoxeadorInfoDTO;
import DTO.EntrenadorDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import model.Boxeador;
import model.Categoria;
import model.Entrenador;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import repository.BoxeadorRepository;
import repository.EntrenadorRepository;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntrenadorServiceTest {

    @Inject
    EntrenadorServiceImp entrenadorServiceImp;
    ModelMapper modelMapper = new ModelMapper();

    @Test
    public void obtenerEntrenadores(){
        //execute
        List<EntrenadorDTO> entrenadorDTOS = this.entrenadorServiceImp.getAllEntrenadores();

        //verify
        assertEquals(4,entrenadorDTOS.size());
    }

    @Test
    public void obtenerEntrenadorPorCategoria(){
        //setup
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria = new Categoria(1L,"Mosca",48.988,50.802);

        categorias.add(categoria);

        Entrenador entrenador = new Entrenador("Agus",categorias,new ArrayList<Boxeador>());

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

        Entrenador entrenador = new Entrenador("Juan",categorias, new ArrayList<Boxeador>());

        Boxeador boxeador = new  Boxeador();

        EntrenadorDTO entrenadorDTO = modelMapper.map(entrenador,EntrenadorDTO.class);
        BoxeadorInfoDTO boxeadorInfoDTO = modelMapper.map(boxeador, BoxeadorInfoDTO.class);

        //execute
        EntrenadorDTO response = this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorInfoDTO);

        //verify
        assertEquals(5,response.getBoxeadores().size());
    }

    @Test
    public void queNoSePuedaAgregarBoxeador() throws Exception {
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);


        Entrenador entrenador = new Entrenador("Juan",categorias, new ArrayList<Boxeador>());

        Boxeador boxeador = new  Boxeador("Nahuel",77D,cat1,entrenador,new Date(System.currentTimeMillis()));

        EntrenadorDTO entrenadorDTO = modelMapper.map(entrenador,EntrenadorDTO.class);
        BoxeadorInfoDTO boxeadorDTO = modelMapper.map(boxeador,BoxeadorInfoDTO.class);


        entrenadorDTO = this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
        entrenadorDTO = this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
        entrenadorDTO = this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);



        //execute

        EntrenadorDTO finalEntrenadorDTO = entrenadorDTO;
        Assertions.assertThrows(Exception.class, () -> {
             this.entrenadorServiceImp.addBoxeador(finalEntrenadorDTO,boxeadorDTO);
        });


    }

}
