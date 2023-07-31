package service;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import model.Categoria;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void queNoSePuedaModificarCateogiriaInexistente(){
        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);
        Categoria modificaciones = new Categoria(9L,"Categoria Modificada",100D,120D);

        //config
        Mockito.when(categoriaRepository.findById(categoria.get_id())).thenReturn(Optional.empty());

        //execute

        Assertions.assertThrows(NotFoundException.class, () -> {
            Optional<Categoria> categoriActualizada = this.categoriaServiceImp.update(categoria.get_id(), modificaciones);
        });


    }

    @Test
    public void obtenerCategoriaPesoEntreElRango(){
        //setup
        Double peso= 49D;
        Categoria categoria = new Categoria(1L,"Mosca",48.988,50.802);

        //config
        List<Categoria> categorias = new ArrayList<>();

        categorias.add(new Categoria(1L,"Mosca",48.988,50.802));
        categorias.add(new Categoria(2L,"Gallo",52.163 ,53.525));
        categorias.add(new Categoria(3L,"Pluma",55.338,57.152));
        categorias.add(new Categoria(4L,"Ligero",58.967,61.237));
        categorias.add(new Categoria(5L,"Welter",63.503,66.678));
        categorias.add(new Categoria(6L,"Mediano",69.853,72.562));
        categorias.add(new Categoria(7L,"Mediopesado",76.205,79.378));
        categorias.add(new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE));

        Mockito.when(categoriaRepository.getAllCategorias()).thenReturn(categorias);

        //execute
        Categoria categoriaObtenida = this.categoriaServiceImp.obtenerCategoriaPorPeso(peso);

        //verify
        assertEquals(categoria,categoriaObtenida);

    }

    @Test
    public void obtenerCategoriaPesoEntreLosLimites(){
        //setup
        Double peso= 68D;
        Categoria categoria = new Categoria(5L,"Welter",63.503,66.678);


        //config
        List<Categoria> categorias = new ArrayList<>();

        categorias.add(new Categoria(1L,"Mosca",48.988,50.802));
        categorias.add(new Categoria(2L,"Gallo",52.163 ,53.525));
        categorias.add(new Categoria(3L,"Pluma",55.338,57.152));
        categorias.add(new Categoria(4L,"Ligero",58.967,61.237));
        categorias.add(new Categoria(5L,"Welter",63.503,66.678));
        categorias.add(new Categoria(6L,"Mediano",69.853,72.562));
        categorias.add(new Categoria(7L,"Mediopesado",76.205,79.378));
        categorias.add(new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE));

        Mockito.when(categoriaRepository.getAllCategorias()).thenReturn(categorias);

        //execute
        Categoria categoriaObtenida = this.categoriaServiceImp.obtenerCategoriaPorPeso(peso);

        //verify
        assertEquals(categoria,categoriaObtenida);

    }

    @Test
    public void obtenerCategoriaPesoInferirALaMenorCategoria(){
        //setup
        Double peso= 40D;
        Categoria categoria = new Categoria(1L,"Mosca",48.988,50.802);

        //config
        List<Categoria> categorias = new ArrayList<>();

        categorias.add(new Categoria(1L,"Mosca",48.988,50.802));
        categorias.add(new Categoria(2L,"Gallo",52.163 ,53.525));
        categorias.add(new Categoria(3L,"Pluma",55.338,57.152));
        categorias.add(new Categoria(4L,"Ligero",58.967,61.237));
        categorias.add(new Categoria(5L,"Welter",63.503,66.678));
        categorias.add(new Categoria(6L,"Mediano",69.853,72.562));
        categorias.add(new Categoria(7L,"Mediopesado",76.205,79.378));
        categorias.add(new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE));

        Mockito.when(categoriaRepository.getAllCategorias()).thenReturn(categorias);

        //execute
        Categoria categoriaObtenida = this.categoriaServiceImp.obtenerCategoriaPorPeso(peso);

        //verify
        assertEquals(categoria,categoriaObtenida);

    }

}
