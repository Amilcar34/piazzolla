package com.example.controller;


import com.example.dto.BoxeadorCreateDto;
import com.example.dto.BoxeadorDto;
import com.example.model.Boxeador;
import com.example.service.BoxeadorServiceImp;
import io.quarkus.hibernate.validator.runtime.jaxrs.JaxrsEndPointValidated;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class BoxeadoresControllerTest {

    @InjectMock
    BoxeadorServiceImp boxeadorServiceImp;
    ModelMapper modelMapper = new ModelMapper();

    @Test
    public void listarBoxeadores(){

        List<Boxeador> boxeadors = new ArrayList<>();

        Boxeador boxeador = new Boxeador("Lautaro",40D,null,null,null);

        boxeadors.add(boxeador);

        List<BoxeadorDto> boxeadorDtos = boxeadors.stream().map(b -> modelMapper.map( b, BoxeadorDto.class))
                .collect(Collectors.toList());

        Mockito.when(this.boxeadorServiceImp.getAllBoxeadores()).thenReturn(boxeadorDtos);

        given()
                .contentType(ContentType.JSON)
                .body(boxeadorDtos)
                .when().get("/boxeadores")
                .then()
                .statusCode(200)
                .body(is("[{\"nombre\":\"Lautaro\",\"peso\":40.0,\"categoria\":null,\"entrenador\":null,\"fechaIngreso\":null}]"));
    }

    @Test
    public void crearBoxeador() throws Exception {

        BoxeadorCreateDto boxeador = new BoxeadorCreateDto("Lautaro",40D);

        BoxeadorDto boxeadorDTO = modelMapper.map(boxeador, BoxeadorDto.class);

        Mockito.when(this.boxeadorServiceImp.create(Mockito.any(BoxeadorCreateDto.class))).thenReturn(boxeadorDTO);

        given()
                .contentType(ContentType.JSON)
                .body(boxeador)
                .when().post("/boxeadores")
                .then()
                .statusCode(201)
                .body(is("{\"nombre\":\"Lautaro\",\"peso\":40.0,\"categoria\":null,\"entrenador\":null,\"fechaIngreso\":null}"));
    }

    @Test
    public void queNosePuedaCrearBoxeador() throws Exception {

        BoxeadorCreateDto boxeador = new BoxeadorCreateDto("Lautaro",40D);

        BoxeadorDto boxeadorDTO = modelMapper.map(boxeador, BoxeadorDto.class);

        Mockito.when(this.boxeadorServiceImp.create(Mockito.any(BoxeadorCreateDto.class))).thenThrow(new Exception("El entrenador ha alcanzado el límite de boxeadores (5)."));



        given()
                .contentType(ContentType.JSON)
                .body(boxeadorDTO)
                .when()
                .post("/boxeadores") // Reemplaza "ruta-del-endpoint" por la ruta real del endpoint
                .then()
                .statusCode(402) // Verifica que la respuesta sea un código 402 (u otro código de error que esperes)
                .body(is("El entrenador ha alcanzado el límite de boxeadores (5)."));// Verifica el mensaje de error esperado
    }

    @Test
    public void queSePuedaEliminarBoxeador(){

        Boxeador boxeador = new Boxeador("Lautaro",40D,null,null,null);

        Mockito.when(this.boxeadorServiceImp.eliminar(boxeador.getNombre())).thenReturn(true);

        given()
                .contentType(ContentType.JSON)
                .queryParam("nombre", boxeador.getNombre())
                .when()
                .delete("/boxeadores")
                .then()
                .statusCode(200)
                .body(is("Boxeador eliminado con exito"));
    }


}
