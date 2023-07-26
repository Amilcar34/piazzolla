package service;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import model.Categoria;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.mockito.InjectMock;


import org.mockito.Mockito;
import repository.CategoriaRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CategoriaServiceTest {

    @InjectMock
    CategoriaRepository categoriaRepository;

    @Inject
    CategoriaServiceImp categoriaServiceImp;

    @Test
    public void guardarCategoria(){

        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        //Config
        Mockito.when(categoriaRepository.save(categoria)).thenReturn(categoria);

        //execute
        Categoria categoriaGuardada = categoriaServiceImp.create(categoria);

        //verify
        assertNotNull(categoriaGuardada);
    }

    @Test
    public void listarCategorias(){

        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);
        Categoria categoria1 = new Categoria(10L,"Nueva Categoria 1",13D,140D);

        categorias.add(categoria);
        categorias.add(categoria1);

        //Config
        Mockito.when(categoriaRepository.getAllCategorias()).thenReturn(categorias);

        //execute
        List<Categoria> categoriasObtenidas = this.categoriaServiceImp.getAllCategorias();

        //verify
        assertEquals(categorias,categoriasObtenidas);
    }

    @Test
    public void modificarCategoria(){
        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);
        Categoria modificaciones = new Categoria(9L,"Categoria Modificada",100D,120D);

        //config
        Mockito.when(categoriaRepository.findById(categoria.get_id())).thenReturn(Optional.of(categoria));

        //execute
        Optional<Categoria> categoriActualizada = this.categoriaServiceImp.update(categoria.get_id(),modificaciones);

        //verify
        assertEquals(categoria,categoriActualizada.get());
        assertEquals(categoria.getNombre(),categoriActualizada.get().getNombre());
    }

}
