package controller;


import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;

import jakarta.ws.rs.NotFoundException;
import model.Categoria;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.CategoriaServiceImp;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class CategoriaControllerTest {

    @InjectMock
    CategoriaServiceImp categoriaServiceImp;

    @Test
    public void queSeAgregueUnaCategoria(){

        //setup
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        //verify
        given()
                .contentType(ContentType.JSON)
                .body(categoria)
                .when().post("/categorias")
                .then()
                .statusCode(201)
                .body("nombre", equalTo("Nueva Categoria"));
    }

    @Test
    public void queSeListenLasCategorias(){

        //setup
        Set<Categoria> categoriasSet = new HashSet<>();
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);
        Categoria categoria1 = new Categoria(10L,"Nueva Categoria 1",130D,14D);

        categoriasSet.add(categoria);
        categoriasSet.add(categoria1);

        //Config
        Mockito.when(categoriaServiceImp.getAllCategorias()).thenReturn(categoriasSet);

        given()
                .contentType(ContentType.JSON)
                .body(categoria)
                .when().get("/categorias")
                .then()
                .statusCode(200)
                .body(is("[{\"_id\":9,\"nombre\":\"Nueva Categoria\",\"pesoMin\":100.0,\"pesoMax\":120.0},{\"_id\":10,\"nombre\":\"Nueva Categoria 1\",\"pesoMin\":130.0,\"pesoMax\":14.0}]"));
    }

    @Test
    public void queNoSePuedaActualizarUnaCategoriaInexistente(){

        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        Categoria modificada = new Categoria(9L,"Nueva Categoria Mosca",100D,120D);

        //Config
        Mockito.when(categoriaServiceImp.update(categoria.get_id(),modificada)).thenThrow(new NotFoundException("La categoría no fue encontrada."));

        given()
                .contentType(ContentType.JSON)
                .body(modificada)
                .when().put("/categorias/{id}",categoria.get_id())
                .then()
                .statusCode(404)
                .body(is("La categoría no fue encontrada."));
    }


}
