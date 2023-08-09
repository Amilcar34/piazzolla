package com.example.repository;

import com.example.model.Categoria;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CategoriaRepositoryTest {

    @Inject
    CategoriaRepository categoriaRepository;

    @Test
    public void obtenerCategoriasExistentes(){

        List<Categoria> categorias = this.categoriaRepository.getAll();

        assertNotNull(categorias);
        assertEquals(8,categorias.size());
    }

    @Test
    public void agregarCategoria(){

        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        //execute
        var categoriaCreada = this.categoriaRepository.create(categoria);


        //verify
        assertEquals(categoria,categoriaCreada);
        assertNotNull(categoriaCreada);
        assertTrue(this.categoriaRepository.getAll().contains(categoria));
    }

    @Test
    public void queSePuedaEliminarCategoria(){
       Categoria categoria = new Categoria(1L, "Mosca", 48.988, 50.802);

       Boolean valor = this.categoriaRepository.delete(categoria);

       assertTrue(valor);
       assertFalse(this.categoriaRepository.getAll().contains(categoria));
    }


    @Test
    public void obtenerCategoriaPorId(){

        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        //execute
        this.categoriaRepository.create(categoria);

        Optional<Categoria> categoriaEncontrada = this.categoriaRepository.find(categoria.getId());

        //verify
        assertEquals(categoria,categoriaEncontrada.get());
    }
}
