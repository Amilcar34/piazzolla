package com.example.controller;


import com.example.model.Categoria;
import com.example.service.CategoriaServiceImp;
import com.example.service.LogErrorService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class CategoriaControllerTest {

    @InjectMock
    CategoriaServiceImp categoriaServiceImp;

    @InjectMock
    LogErrorService logErrorService;

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
        List<Categoria> categoriasList = new ArrayList<>();
        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);
        Categoria categoria1 = new Categoria(10L,"Nueva Categoria 1",130D,14D);

        categoriasList.add(categoria);
        categoriasList.add(categoria1);

        //Config
        Mockito.when(categoriaServiceImp.obtenerCategorias()).thenReturn(categoriasList);

        given()
                .contentType(ContentType.JSON)
                .body(categoria)
                .when().get("/categorias")
                .then()
                .statusCode(200)
                .body(is("[{\"_id\":9,\"nombre\":\"Nueva Categoria\",\"pesoMin\":100.0,\"pesoMax\":120.0},{\"_id\":10,\"nombre\":\"Nueva Categoria 1\",\"pesoMin\":130.0,\"pesoMax\":14.0}]"));
    }

    @Test
    public void queSePuedaActualizarUnaCategoria(){

        Categoria categoria = new Categoria(1L,"Mosca",48.988,50.802);

        Categoria modificada = new Categoria(1L,"MOSQUITA",49D,51D);

        //Config
        Mockito.when(categoriaServiceImp.actualizarCategoria(categoria.get_id(),modificada)).thenReturn(Optional.of(modificada));

        given()
                .contentType(ContentType.JSON)
                .body(modificada)
                .when().put("/categorias/{id}",categoria.get_id())
                .then()
                .statusCode(200)
                .body(is("{\"_id\":1,\"nombre\":\"MOSQUITA\",\"pesoMin\":49.0,\"pesoMax\":51.0}"));
    }

    @Test
    public void queNoSePuedaActualizarUnaCategoriaInexistente() throws IOException {

        Categoria categoria = new Categoria(9L,"Nueva Categoria",100D,120D);

        Categoria modificada = new Categoria(9L,"Nueva Categoria Mosca",100D,120D);

        //Config
        Mockito.when(categoriaServiceImp.actualizarCategoria(categoria.get_id(),modificada)).thenThrow(new NotFoundException("La categoría no fue encontrada."));
        Mockito.when(logErrorService.grabarError(new NotFoundException("La categoría no fue encontrada."),this.getClass().getName())).thenReturn(true);

            given()
                    .contentType(ContentType.JSON)
                    .body(modificada)
                    .when().put("/categorias/{id}", categoria.get_id())
                    .then()
                    .statusCode(404)
                    .body(is("La categoría no fue encontrada."));

    }

}
