package com.example.service;


import com.example.model.Categoria;
import com.example.repository.CategoriaRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CategoriaServiceTest {

    @InjectMock
    CategoriaRepository categoriaRepository;

    @Inject
    CategoriaServiceImp categoriaServiceImp;

    @BeforeEach
    public void setUpCategorias(){

        List<Categoria> categorias = new ArrayList<>();

        categorias.add(new Categoria(1L,"Mosca",48.988,50.802));
        categorias.add(new Categoria(2L,"Gallo",52.163 ,53.525));
        categorias.add(new Categoria(3L,"Pluma",55.338,57.152));
        categorias.add(new Categoria(4L,"Ligero",58.967,61.237));
        categorias.add(new Categoria(5L,"Welter",63.503,66.678));
        categorias.add(new Categoria(6L,"Mediano",69.853,72.562));
        categorias.add(new Categoria(7L,"Mediopesado",76.205,79.378));
        categorias.add(new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE));

        Mockito.when(categoriaRepository.getAll()).thenReturn(categorias);
    }

    @Test
    public void listarCategorias(){
        //execute
        List<Categoria> categoriasObtenidas = this.categoriaServiceImp.obtenerCategorias();

        //verify
        assertNotNull(categoriasObtenidas);
        assertFalse(categoriasObtenidas.isEmpty());
    }

    @Test
    public void guardarCategoria(){

        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        //Config
        Mockito.when(categoriaRepository.create(categoria)).thenReturn(categoria);

        //execute
        Categoria categoriaGuardada = categoriaServiceImp.crearCategoria(categoria);

        //verify
        assertNotNull(categoriaGuardada);
    }

    @Test
    public void modificarCategoria(){
        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);
        Categoria modificaciones = new Categoria(9L,"Categoria Modificada",130D,140D);

        //config
        Mockito.when(categoriaRepository.find(categoria.get_id())).thenReturn(Optional.of(categoria));

        //execute
        Optional<Categoria> categoriActualizada = this.categoriaServiceImp.actualizarCategoria(categoria.get_id(),modificaciones);

        //verify
        assertNotNull(categoriActualizada);
        assertTrue(categoriActualizada.isPresent()); // Ensure the Optional is not empty

        Categoria categoriaActualizadaObj = categoriActualizada.get();
        assertEquals(modificaciones.getNombre(), categoriaActualizadaObj.getNombre()); // Verify modified name
        assertEquals(modificaciones.getPesoMin(), categoriaActualizadaObj.getPesoMin()); // Verify modified min weight
        assertEquals(modificaciones.getPesoMax(), categoriaActualizadaObj.getPesoMax()); // Verify modified max weight
    }

    @Test
    public void queNoSePuedaModificarCateogiriaInexistente(){
        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);
        Categoria modificaciones = new Categoria(9L,"Categoria Modificada",100D,120D);

        //config
        Mockito.when(categoriaRepository.find(categoria.get_id())).thenReturn(Optional.empty());

        //execute

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.categoriaServiceImp.actualizarCategoria(categoria.get_id(), modificaciones);
        });

    }

    @Test
    public void obtenerCategoriaPesoEntreElRango(){
        //setup
        Double peso= 49D;
        Categoria categoria = new Categoria(1L,"Mosca",48.988,50.802);

        //execute
        Categoria categoriaObtenida = this.categoriaServiceImp.obtenerCategoriaPorPeso(peso);

        //verify
        assertNotNull(categoriaObtenida);
        assertEquals(categoria,categoriaObtenida);

    }

    @Test
    public void obtenerCategoriaPesoMinimoLimite(){
        //setup
        Double pesoMin= 48.988;
        Categoria categoria = new Categoria(1L,"Mosca",48.988,50.802);

        //execute
        Categoria categoriaObtenidaMin = this.categoriaServiceImp.obtenerCategoriaPorPeso(pesoMin);

        //verify
        assertNotNull(categoriaObtenidaMin);
        assertEquals(categoria, categoriaObtenidaMin);
    }

    @Test
    public void obtenerCategoriaPesoMaximoLimite(){
        //setup
        Double pesoMax= 50.802;
        Categoria categoria = new Categoria(1L,"Mosca",48.988,50.802);

        //execute
        Categoria categoriaObtenidaMax = this.categoriaServiceImp.obtenerCategoriaPorPeso(pesoMax);

        //verify
        assertNotNull(categoriaObtenidaMax);
        assertEquals(categoria, categoriaObtenidaMax);
    }

   @Test
    public void obtenerCategoriaPesoEntreLosLimites(){
        //setup
        Double peso= 68D;
        Categoria categoria = new Categoria(5L,"Welter",63.503,66.678);

        //execute
        Categoria categoriaObtenida = this.categoriaServiceImp.obtenerCategoriaPorPeso(peso);

        //verify
        assertNotNull(categoriaObtenida);
        assertEquals(categoria,categoriaObtenida);

    }

   @Test
    public void obtenerCategoriaPesoInferiorALaMenorCategoria(){
        //setup
        Double peso= 40D;
        Categoria categoria = new Categoria(1L,"Mosca",48.988,50.802);

        //execute
        Categoria categoriaObtenida = this.categoriaServiceImp.obtenerCategoriaPorPeso(peso);

        //verify
        assertNotNull(categoriaObtenida);
        assertEquals(categoria,categoriaObtenida);
    }

}
