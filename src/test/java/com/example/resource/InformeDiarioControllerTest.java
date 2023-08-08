package com.example.resource;


import com.example.dto.informe.EntrenadorInformeDto;
import com.example.dto.informe.InformeDto;
import com.example.service.InformeDiarioServiceImp;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class InformeDiarioControllerTest {
    @InjectMock
    InformeDiarioServiceImp informeDiarioServiceImp;

    @Test
    public void mostrarInforme(){
        //setup
        List<EntrenadorInformeDto> entrenadores = new ArrayList<>();
        EntrenadorInformeDto entrenadorInformeDTO = new EntrenadorInformeDto("Agus",2);
        entrenadores.add(entrenadorInformeDTO);
        InformeDto informeDTO = new InformeDto(new Date(System.currentTimeMillis()),entrenadores,2);

        //config
        Mockito.when(this.informeDiarioServiceImp.informeDiario()).thenReturn(informeDTO);

        given()
                .contentType(ContentType.JSON)
                .body(informeDTO)
                .when().get("/informe")
                .then()
                .statusCode(200)
                .body("totalBoxeadores",equalTo(2));

    }
}
