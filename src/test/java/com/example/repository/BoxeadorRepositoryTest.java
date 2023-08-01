package com.example.repository;

import com.example.model.Boxeador;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

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

        this.boxeadorRepository.create(boxeador);

        //verify
        assertEquals(1, this.boxeadorRepository.getAllBoxeadores().size());
    }

    @Test
    public void crearBoxeador(){
        // Setup
        Boxeador boxeador = new Boxeador("Lautaro", 40D, null, null, null);

        // Execute
        Boxeador boxeadorObtenido = this.boxeadorRepository.create(boxeador);

        // Verify
        assertNotNull(boxeadorObtenido);
        assertNotNull(boxeador);

        List<Boxeador> boxeadores = this.boxeadorRepository.getAllBoxeadores();
        assertTrue(boxeadores.contains(boxeador));

    }

    @Test
    public void obtenerCantidadDeBoxeadoresPorDia(){
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
