package repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import model.Categoria;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CategoriaRepositoryTest {

    @Inject
    CategoriaRepository categoriaRepository;

    @Test
    public void obtenerCategoriasExistentes(){

        Set<Categoria> categorias = this.categoriaRepository.getAllCategorias();

        assertNotNull(categorias);
        assertNotNull(categorias.isEmpty());
    }

    @Test
    public void agregarCategoria(){

        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        //execute
        this.categoriaRepository.save(categoria);

        Set<Categoria> categorias = this.categoriaRepository.getAllCategorias();

        //verify
        assertTrue(categorias.contains(categoria));
    }

    @Test
    public void obtenerCategoriaPorId(){

        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        //execute
        this.categoriaRepository.save(categoria);

        Optional<Categoria> categoriaEncontrada = this.categoriaRepository.findById(categoria._id);

        //verify
        assertEquals(categoria,categoriaEncontrada.get());
    }
}
