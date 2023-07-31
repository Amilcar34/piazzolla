package service;


import DTO.BoxeadorInfoDTO;
import DTO.EntrenadorDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import model.Boxeador;
import model.Categoria;
import model.Entrenador;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntrenadorServiceTest {

    @Inject
    EntrenadorServiceImp entrenadorServiceImp;

    ModelMapper modelMapper = new ModelMapper();

    @Test
    @Order(1)
    public void obtenerEntrenadores(){
        //execute
        List<EntrenadorDTO> entrenadorDTOS = this.entrenadorServiceImp.getAllEntrenadores();

        //verify
        assertEquals(4,entrenadorDTOS.size());
    }

    @Test
    @Order(2)
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
    @Order(3)
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
        assertEquals(2,response.getBoxeadores().size());
    }

    @Test
    @Order(4)
    public void queNoSePuedaAgregarBoxeador() {
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


        //execute
        try{
            this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
            this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
            this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
            this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
            this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorDTO);
        }catch (Exception e){
            assertEquals(e.getMessage() ,"El entrenador Juan ha alcanzado el límite de boxeadores (5)." );
        }

    }

}
