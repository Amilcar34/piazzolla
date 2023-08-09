package com.example.repository;

import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class BoxeadorRepositoryTest {

    @Inject
    BoxeadorRepository boxeadorRepository;

    @BeforeEach
    public void setup(){
        boxeadorRepository = new BoxeadorRepository();
    }

    @Test
    public void listarBoxeadores(){
        //setup
        Boxeador boxeador = new Boxeador("Lautaro",40D,null,null,null);

        //execute
        this.boxeadorRepository.create(boxeador);

        //verify
        assertEquals(1, this.boxeadorRepository.getAll().size());
    }

    @Test
    public void crearBoxeador(){
        // Setup
        Boxeador boxeador = new Boxeador("Lautaro", 40D, null, null, null);

        // Execute
        var boxeadorCreado = this.boxeadorRepository.create(boxeador);

        // Verify
        assertEquals(boxeador,boxeadorCreado);
        assertTrue(this.boxeadorRepository.getAll().contains(boxeador));

    }

    @Test
    public void obtenerBoxeadorPorNombre(){

        //setup
        Boxeador boxeador1 = new Boxeador("Lautaro", 40D, null, null, null);
        Boxeador boxeador2 = new Boxeador("Lorena", 40D, null, null, null);

        this.boxeadorRepository.create(boxeador1);
        this.boxeadorRepository.create(boxeador2);

        //execute
        Optional<Boxeador> boxeadorBuscando = this.boxeadorRepository.find(boxeador1.getNombre());

        //verify
        assertNotNull(boxeadorBuscando);
        assertEquals(boxeador1,boxeadorBuscando.get());
    }

    @Test
    public void verificarQueNoExistaBoxeador() {
        // Setup
        Boxeador boxeador1 = new Boxeador("Lautaro", 40D, null, null, null);
        Boxeador boxeador2 = new Boxeador("Lorena", 40D, null, null, null);

        this.boxeadorRepository.create(boxeador1);
        this.boxeadorRepository.create(boxeador2);

        // Execute
        Optional<Boxeador> boxeadorBuscandoInexistente = this.boxeadorRepository.find("Juan");

        // Verify non-existing boxer
        assertNotNull(boxeadorBuscandoInexistente);
        assertFalse(boxeadorBuscandoInexistente.isPresent());
    }

    @Test
    public void queSePuedaEliminarBoxeador(){
        //setup
        Boxeador boxeador1 = new Boxeador("Lautaro", 40D, null, null, null);
        Boxeador boxeador2 = new Boxeador("Estefi", 60D, null, null, null);

        this.boxeadorRepository.create(boxeador1);
        this.boxeadorRepository.create(boxeador2);

        List<Boxeador> boxeadors = this.boxeadorRepository.getAll();

        //execute
        Boolean valor = this.boxeadorRepository.delete(boxeador2);

        assertTrue(valor);
        assertFalse(boxeadors.contains(boxeador2));
    }

    @Test
    public void queNoSePuedaEliminarBoxeadorInexistente(){
        //setup
        Boxeador boxeador1 = new Boxeador("Lautaro", 40D, null, null, null);
        Boxeador boxeador2 = new Boxeador("Estefi", 60D, null, null, null);
        Boxeador boxeador3 = new Boxeador("Karina", 60D, null, null, null);

        this.boxeadorRepository.create(boxeador1);
        this.boxeadorRepository.create(boxeador2);

        //execute
        Boolean valor = this.boxeadorRepository.delete(boxeador3);

        assertFalse(valor);
    }

    @Test
    public void queSePuedaEliminarEntrenador(){
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(7L,"Mediopesado",76.205,79.378);
        Categoria cat2 = new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Juan",categorias, new ArrayList<Boxeador>());

        Boxeador boxeador = new Boxeador("Karina", 77D, cat1, entrenador, new Date(System.currentTimeMillis()));

        this.boxeadorRepository.create(boxeador);

        //execute
        Boolean valor = this.boxeadorRepository.eliminarEntrenador(entrenador);

        //verify
        assertTrue(valor);
        assertNull(boxeador.getEntrenador());
    }

    @Test
    public void obtenerCantidadDeBoxeadoresPorDia(){

        //setup
        Boxeador boxeadorHoy = new Boxeador("Lautaro", 40D, null, null, new Date(System.currentTimeMillis()));
        Boxeador boxeadorOtroDia = new Boxeador("Lautaro", 40D, null, null, new Date(2023-05-20));

        this.boxeadorRepository.create(boxeadorHoy);
        this.boxeadorRepository.create(boxeadorOtroDia);

        // Execute
        int cantidadBoxeadores = this.boxeadorRepository.cantBoxeadoresPorDia();

        // Verify
        assertEquals(1, cantidadBoxeadores);

    }
}
