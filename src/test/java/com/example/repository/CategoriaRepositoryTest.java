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

        List<Categoria> categorias = this.categoriaRepository.getAllCategorias();

        assertNotNull(categorias);
        assertEquals(8,categorias.size());
    }

    @Test
    public void agregarCategoria(){

        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        //execute
        Categoria categoriaGuardada = this.categoriaRepository.save(categoria);

        List<Categoria> categorias = this.categoriaRepository.getAllCategorias();

        //verify
        assertNotNull(categoriaGuardada);
        assertEquals(categoria, categoriaGuardada);
        assertTrue(categorias.contains(categoria));
    }


    @Test
    public void obtenerCategoriaPorId(){

        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        //execute
        this.categoriaRepository.save(categoria);

        Optional<Categoria> categoriaEncontrada = this.categoriaRepository.findById(categoria.get_id());

        //verify
        assertEquals(categoria,categoriaEncontrada.get());
    }
}
