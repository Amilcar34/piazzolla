package com.example.controller;

import com.example.dto.BoxeadorSinEntreDto;
import com.example.dto.EntrenadorDto;
import com.example.dto.EntrenadorSinBoxDto;
import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;
import com.example.service.EntrenadorServiceImp;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class EntrenadorControllerTest {

    @InjectMock
    EntrenadorServiceImp entrenadorServiceImp;

    ModelMapper modelMapper = new ModelMapper();

    @Test
    public void queSeListenLosEntrenadores(){

        //setup
        List<Entrenador> entrenadores = new ArrayList<>();

        List<Categoria> cat1 = new ArrayList<>();

        cat1.add(new Categoria(1L,"Mosca",48.988,50.802));
        cat1.add(new Categoria(2L,"Gallo",52.163 ,53.525));
        entrenadores.add(new Entrenador("Agus",cat1,new ArrayList<Boxeador>()));

        List<EntrenadorDto> entrenadorDtos = entrenadores.stream().map(e -> modelMapper.map(e, EntrenadorDto.class))
                                            .collect(Collectors.toList());

        //Config
        Mockito.when(entrenadorServiceImp.getAllEntrenadores()).thenReturn(entrenadorDtos);

        given()
                .contentType(ContentType.JSON)
                .body(entrenadorDtos)
                .when().get("/entrenadores")
                .then()
                .statusCode(200)
                .body(is("[{\"nombre\":\"Agus\",\"categorias\":[{\"_id\":1,\"nombre\":\"Mosca\",\"pesoMin\":48.988,\"pesoMax\":50.802},{\"_id\":2,\"nombre\":\"Gallo\",\"pesoMin\":52.163,\"pesoMax\":53.525}],\"boxeadores\":[]}]"));
    }

    @Test
    public void queSePuedaCrearEntrenador() throws Exception {

        //setup

        List<Categoria> cat1 = new ArrayList<>();

        cat1.add(new Categoria(9L,"Nueva",51D,52D));
        EntrenadorDto entrenadorDto = new EntrenadorDto("Nuevo entrenador",cat1,null);



        //Config
        Mockito.when(entrenadorServiceImp.crearEntrenador(entrenadorDto)).thenReturn(true);

        given()
                .contentType(ContentType.JSON)
                .body(entrenadorDto)
                .when().post("/entrenadores")
                .then()
                .statusCode(201)
                .body(is("{\"nombre\":\"Nuevo entrenador\",\"categorias\":[{\"_id\":9,\"nombre\":\"Nueva\",\"pesoMin\":51.0,\"pesoMax\":52.0}],\"boxeadores\":null}"));
    }

    @Test
    public void queNoSePuedaCrearEntrenadorConMasDeDosCategorias() throws Exception {

        //setup

        List<Categoria> cat1 = new ArrayList<>();

        cat1.add(new Categoria(9L,"Nueva",51D,52D));
        cat1.add(new Categoria(10L,"Nueva1",51D,52D));
        cat1.add(new Categoria(11L,"Nueva2",51D,52D));
        EntrenadorDto entrenadorDto = new EntrenadorDto("Nuevo entrenador",cat1,null);

        //Config
        Mockito.when(entrenadorServiceImp.crearEntrenador(entrenadorDto)).thenThrow(new Exception("El entrenador no puede tener más de 2 categorias"));

        given()
                .contentType(ContentType.JSON)
                .body(entrenadorDto)
                .when().post("/entrenadores")
                .then()
                .statusCode(400)
                .body(is("El entrenador no puede tener más de 2 categorias"));
    }
}
