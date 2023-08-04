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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class EntrenadorRepositoryTest {

    @Inject
    EntrenadorRepository entrenadorRepository;

    @Test
    public void obtenerTodosLosEntrenadores(){

        List<Entrenador> entrenadores = this.entrenadorRepository.getAll();

        assertNotNull(entrenadores);
        assertFalse(entrenadores.isEmpty());
    }

    @Test
    public void queSePuedaCrearEntrenador(){
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(9L,"nueva",76.205,79.378);

        categorias.add(cat1);

        Entrenador entrenador = new Entrenador("nuevo",categorias, new ArrayList<Boxeador>());

        //execute
        Entrenador entrenadorCreado = this.entrenadorRepository.create(entrenador);

        //verify
        assertNotNull(entrenadorCreado);
        assertEquals(entrenador,entrenadorCreado);
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
        Categoria cat1 = new Categoria(10L,"categoria",76.205,79.378);

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

        //execute
        Entrenador entrenadorObtenido = this.entrenadorRepository.addBoxeador(entrenador,boxeador);

        //verify
        assertTrue(entrenadorObtenido.getBoxeadores().contains(boxeador));
    }

    @Test
    public void queSePuedaEliminarUnBoxeador(){
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Juan",categorias, new ArrayList<Boxeador>());

        Boxeador boxeador1 = new Boxeador("Laura",92D,cat1,entrenador,new Date(System.currentTimeMillis()));
        Boxeador boxeador2 = new Boxeador("Lautaro",77D,cat1,entrenador,new Date(System.currentTimeMillis()));

        entrenador = this.entrenadorRepository.addBoxeador(entrenador,boxeador1);
        entrenador = this.entrenadorRepository.addBoxeador(entrenador,boxeador2);

        //execute
        Boolean value = this.entrenadorRepository.deleteBoxeador(entrenador,boxeador2);

        //verify
        assertTrue(entrenador.getBoxeadores().contains(boxeador1));
        assertTrue(value);
        assertFalse(entrenador.getBoxeadores().contains(boxeador2));

    }

    @Test
    public void queNoSePuedaEliminarUnBoxeadorInexistente(){
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Juan",categorias, new ArrayList<Boxeador>());

        Boxeador boxeador1 = new Boxeador("Laura",92D,cat1,entrenador,new Date(System.currentTimeMillis()));
        Boxeador boxeador2 = new Boxeador("Nuevo",77D,cat1,null,new Date(System.currentTimeMillis()));

        entrenador = this.entrenadorRepository.addBoxeador(entrenador,boxeador1);

        //execute
        Boolean value = this.entrenadorRepository.deleteBoxeador(entrenador,boxeador2);

        //verify
        assertFalse(value);
    }

    @Test
    public void queNoSePuedaEliminarBoxeadorDeEntrenadorInexistente(){

        //setup
        Entrenador entrenador = new Entrenador("inex",null, new ArrayList<Boxeador>());

        Boxeador boxeador = new Boxeador("Laura",92D,null,entrenador,new Date(System.currentTimeMillis()));

        //execute
        Boolean value = this.entrenadorRepository.deleteBoxeador(entrenador,boxeador);

        //verify
        assertFalse(value);

    }


    @Test
    public void obtenerEntrenadorPorNombre(){

        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Juan",categorias, new ArrayList<Boxeador>());

        //execute
        Optional<Entrenador> entrenadorBuscado = this.entrenadorRepository.find(entrenador.getNombre());

        //verify
        assertNotNull(entrenadorBuscado);
        assertEquals(entrenador.getNombre(),entrenadorBuscado.get().getNombre());
    }


    @Test
    public void queNoSePuedaAgregarUnBoxeadorAEntrenadorInexistente(){
        //setup
        Entrenador entrenador = new Entrenador("Ignacio",null, new ArrayList<Boxeador>());

        Boxeador boxeador = new Boxeador("Nahuel",77D,null,null,new Date(System.currentTimeMillis()));

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
        boxeadores.add(new Boxeador("Milo",77D,cat1,entrenador,new Date(123,5,3)));

        //execute
        int cantidad = this.entrenadorRepository.obtenerBoxeadoresDelDia(entrenador);

        //verify
        assertEquals(1,cantidad);
    }
}
