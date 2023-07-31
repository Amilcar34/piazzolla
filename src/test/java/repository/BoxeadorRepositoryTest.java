package repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import model.Boxeador;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.sql.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;

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
        //setup
        Boxeador boxeador = new Boxeador("Lautaro",40D,null,null,null);

        //execute
        Boxeador boxeadorObtenido = this.boxeadorRepository.create(boxeador);

        //verify
        assertEquals(boxeador.getNombre(),boxeadorObtenido.getNombre());
        assertEquals(boxeador.getPeso(),boxeadorObtenido.getPeso());

    }

    @Test
    public void obtenerCantidadDeBoxeadoresPorDia(){
        //setup
        Boxeador boxeador = new Boxeador("Lautaro",40D,null,null,new Date(System.currentTimeMillis()));
        Boxeador boxeador1 = new Boxeador("Lautaro",40D,null,null,new Date(System.currentTimeMillis()));

         this.boxeadorRepository.create(boxeador);
         this.boxeadorRepository.create(boxeador1);

         //execute

        int cantidadBoxeadores = this.boxeadorRepository.cantBoxeadoresPorDia();

        assertEquals(2,cantidadBoxeadores);

    }
}
