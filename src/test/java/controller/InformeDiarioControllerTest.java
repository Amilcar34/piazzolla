package controller;


import DTO.EntrenadorInformeDTO;
import DTO.InformeDTO;

import io.quarkus.test.junit.QuarkusTest;

import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.InformeDiarioServiceImp;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class InformeDiarioControllerTest {
    @InjectMock
    InformeDiarioServiceImp informeDiarioServiceImp;

    @Test
    public void mostrarInforme(){
        //setup
        List<EntrenadorInformeDTO> entrenadores = new ArrayList<>();
        EntrenadorInformeDTO entrenadorInformeDTO = new EntrenadorInformeDTO("Agus",2);
        entrenadores.add(entrenadorInformeDTO);
        InformeDTO informeDTO = new InformeDTO(new Date(System.currentTimeMillis()),entrenadores,2);

        //config
        Mockito.when(this.informeDiarioServiceImp.informeDiario()).thenReturn(informeDTO);

        given()
                .contentType(ContentType.JSON)
                .body(informeDTO)
                .when().get("/informe")
                .then()
                .statusCode(200)
                .body(is("{\"dia\":\"2023-07-31\",\"entrenadores\":[{\"nombre\":\"Agus\",\"boxeadoresAsignadosHoy\":2}],\"totalBoxeadores\":2}"));

    }
}
