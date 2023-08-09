package com.example.resource;


import com.example.dto.BoxeadorCreateDto;
import com.example.dto.BoxeadorDto;
import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;
import com.example.service.BoxeadorServiceImp;
import com.example.service.LogErrorService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class BoxeadoresControllerTest {

    @InjectMock
    BoxeadorServiceImp boxeadorServiceImp;
    @InjectMock
    LogErrorService logErrorService;
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
    public void queSePuedaActualizarUnBoxeador() {

        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(1L, "Mosca", 48.988, 50.802);
        Categoria cat2 = new Categoria(2L, "Gallo", 52.163, 53.525);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Agus", categorias, null);
        Entrenador entrenador1 = new Entrenador("Leo", categorias, null);

        Boxeador boxeador = new Boxeador("Luca", 50D, cat1, entrenador, new Date(System.currentTimeMillis()));
        Boxeador boxeadorModificado = new Boxeador("Lucas", 54D, cat2, entrenador1, new Date(116, 5, 3));

        BoxeadorDto boxeadorDto = modelMapper.map(boxeadorModificado, BoxeadorDto.class);

        //Config
        Mockito.when(boxeadorServiceImp.actualizar(boxeador.getNombre(), boxeadorDto)).thenReturn(Optional.of(boxeadorDto));

        given()
                .contentType(ContentType.JSON)
                .queryParam("nombre", boxeador.getNombre())
                .body(boxeadorDto)
                .when()
                .put("/boxeadores")
                .then()
                .statusCode(200)
                .body(is("{\"nombre\":\"Lucas\",\"peso\":54.0,\"categoria\":{\"id\":2,\"categoria\":\"Gallo\",\"pesoMin\":52.163,\"pesoMax\":53.525},\"entrenador\":{\"nombre\":\"Leo\",\"categorias\":[{\"id\":1,\"categoria\":\"Mosca\",\"pesoMin\":48.988,\"pesoMax\":50.802},{\"id\":2,\"categoria\":\"Gallo\",\"pesoMin\":52.163,\"pesoMax\":53.525}]},\"fechaIngreso\":\"2016-06-03\"}"));
    }

    @Test
    public void queNoSePuedaActualizarUnaCategoriaInexistente() throws IOException {
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(1L, "Mosca", 48.988, 50.802);
        Categoria cat2 = new Categoria(2L, "Gallo", 52.163, 53.525);

        categorias.add(cat1);
        categorias.add(cat2);

        Entrenador entrenador = new Entrenador("Agus", categorias, null);
        Entrenador entrenador1 = new Entrenador("Leo", categorias, null);

        Boxeador boxeador = new Boxeador("Luca",50D,cat1,entrenador,new Date(System.currentTimeMillis()));
        Boxeador boxeadorModificado = new Boxeador("Lucas",54D,cat2,entrenador1,new Date(116, 5,3));

        BoxeadorDto boxeadorDto = modelMapper.map(boxeadorModificado,BoxeadorDto.class);

        //Config
        Mockito.when(boxeadorServiceImp.actualizar(boxeador.getNombre(),boxeadorDto)).thenThrow(new NotFoundException("El boxeador no fue encontrado."));
        Mockito.when(logErrorService.grabarError(new NotFoundException("La categoría no fue encontrada."),this.getClass().getName())).thenReturn(true);

        given()
                .contentType(ContentType.JSON)
                .queryParam("nombre",boxeador.getNombre())
                .body(boxeadorDto)
                .when().put("/boxeadores")
                .then()
                .statusCode(404)
                .body(is("El boxeador no fue encontrado."));

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

    @Test
    public void queNoSePuedaEliminarBoxeadorInexistente(){

        Boxeador boxeador = new Boxeador("Camila",50D,null,null,null);

        Mockito.when(this.boxeadorServiceImp.eliminar(boxeador.getNombre())).thenThrow(new NotFoundException("El boxeador " + boxeador.getNombre() + " no fue encontrado."));

        given()
                .contentType(ContentType.JSON)
                .queryParam("nombre", boxeador.getNombre())
                .when()
                .delete("/boxeadores")
                .then()
                .statusCode(400)
                .body(is("El boxeador Camila no fue encontrado."));
    }


}
