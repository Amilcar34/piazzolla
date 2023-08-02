package com.example.repository;

import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class EntrenadorRepositoryTest {

    @Inject
    EntrenadorRepository entrenadorRepository;

    @Test
    public void obtenerTodosLosEntrenadores(){

        List<Entrenador> entrenadores = this.entrenadorRepository.getAll();

        assertNotNull(entrenadores);
        assertEquals(4,entrenadores.size());
    }

    @Test
    public void obtenerEntrenadorPorCategoria(){

        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Juan",categorias, new ArrayList<Boxeador>());

        //execute
        Entrenador entrenadorObtenido = this.entrenadorRepository.obtenerEntrenadorPorCategoria(cat1);

        //verify
        assertEquals(entrenador,entrenadorObtenido);
    }

    @Test
    public void queNoSePuedaObtenerEntrenadorPorCategoriaInexistente(){

        //setup
        Categoria cat1 = new Categoria(9L,"categoria",76.205,79.378);

        //execute
        Entrenador entrenadorObtenido = this.entrenadorRepository.obtenerEntrenadorPorCategoria(cat1);

        //verify
        assertEquals(null,entrenadorObtenido);
    }

    @Test
    public void queSePuedaAgregarUnBoxeador(){
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Juan",categorias, new ArrayList<Boxeador>());

        Boxeador boxeador = new Boxeador("Nahuel",77D,cat1,entrenador,new Date(System.currentTimeMillis()));

        Entrenador entrenadorObtenido = this.entrenadorRepository.addBoxeador(entrenador,boxeador);

        assertEquals(1,entrenadorObtenido.getBoxeadores().size());
    }

    @Test
    public void queNoSePuedaAgregarUnBoxeadorAEntrenadorInexistente(){
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Ignacio",categorias, new ArrayList<Boxeador>());

        Boxeador boxeador = new Boxeador("Nahuel",77D,cat1,entrenador,new Date(System.currentTimeMillis()));

        //execute
        Entrenador entrenadorObtenido = this.entrenadorRepository.addBoxeador(entrenador,boxeador);

        //verify
        assertEquals(null,entrenadorObtenido);
    }

    @Test
    public void obtenerCantBoxeadoresQueSeAsignaronElDiaActual(){

        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);

        List<Boxeador> boxeadores = new ArrayList<>();

        Entrenador entrenador = new Entrenador("Juan",categorias, boxeadores);

        boxeadores.add(new Boxeador("Nahuel",77D,cat1,entrenador,new Date(System.currentTimeMillis())));
        boxeadores.add(new Boxeador("Milo",77D,cat1,entrenador,new Date(2023-05-20)));

        //execute
        int cantidad = this.entrenadorRepository.obtenerBoxeadoresDelDia(entrenador);

        //verify
        assertEquals(1,cantidad);
    }
}
