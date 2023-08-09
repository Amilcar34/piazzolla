package com.example.resource;

import com.example.dto.EntrenadorDto;
import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;
import com.example.service.EntrenadorServiceImp;
import com.example.service.LogErrorService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class EntrenadorControllerTest {

    @InjectMock
    EntrenadorServiceImp entrenadorServiceImp;

    @InjectMock
    LogErrorService logErrorService;

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
                .body(is("[{\"nombre\":\"Agus\",\"categorias\":[{\"id\":1,\"categoria\":\"Mosca\",\"pesoMin\":48.988,\"pesoMax\":50.802},{\"id\":2,\"categoria\":\"Gallo\",\"pesoMin\":52.163,\"pesoMax\":53.525}],\"boxeadores\":[]}]"));
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
                .body(is("{\"nombre\":\"Nuevo entrenador\",\"categorias\":[{\"id\":9,\"categoria\":\"Nueva\",\"pesoMin\":51.0,\"pesoMax\":52.0}],\"boxeadores\":null}"));
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

    @Test
    public void queSePuedaActualizarUnEntrenador(){

        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(1L, "Mosca", 48.988, 50.802);
        Categoria cat2 = new Categoria(2L, "Gallo", 52.163, 53.525);

        categorias.add(cat1);
        categorias.add(cat2);

        List<Boxeador> boxeadors = new ArrayList<>();

        Boxeador boxeador = new Boxeador("Nico", 57D, null, null, null);
        Boxeador boxeador2 = new Boxeador("Nicol", 50D, null, null, null);

        boxeadors.add(boxeador);
        boxeadors.add(boxeador2);

        Entrenador entrenador = new Entrenador("Agus", categorias, boxeadors);
        categorias.remove(cat1);
        boxeadors.remove(boxeador);
        Entrenador entrenadorModificado = new Entrenador("Agustina", categorias, boxeadors);

        EntrenadorDto entrenadorDto = modelMapper.map(entrenadorModificado,EntrenadorDto.class);

        //Config
        Mockito.when(this.entrenadorServiceImp.actualizarEntrenador(entrenador.getNombre(),entrenadorDto)).thenReturn(Optional.of(entrenadorDto));

        given()
                .contentType(ContentType.JSON)
                .queryParam("nombre",entrenador.getNombre())
                .body(entrenadorDto)
                .when()
                .put("/entrenadores")
                .then()
                .statusCode(200)
                .body(is("{\"nombre\":\"Agustina\",\"categorias\":[{\"id\":2,\"categoria\":\"Gallo\",\"pesoMin\":52.163,\"pesoMax\":53.525}],\"boxeadores\":[{\"nombre\":\"Nicol\",\"peso\":50.0,\"categoria\":null,\"fechaIngreso\":null}]}"));
    }

    @Test
    public void queNoSePuedaActualizarUnEntrenadorInexistente() throws IOException {
        //setup
        List<Categoria> categorias = new ArrayList<>();

        Categoria cat1 = new Categoria(1L, "Mosca", 48.988, 50.802);
        Categoria cat2 = new Categoria(2L, "Gallo", 52.163, 53.525);

        categorias.add(cat1);
        categorias.add(cat2);

        List<Boxeador> boxeadors = new ArrayList<>();

        Boxeador boxeador = new Boxeador("Nico", 57D, null, null, null);
        Boxeador boxeador2 = new Boxeador("Nicol", 50D, null, null, null);

        boxeadors.add(boxeador);
        boxeadors.add(boxeador2);

        Entrenador entrenador = new Entrenador("Agus", categorias, boxeadors);
        categorias.remove(cat1);
        boxeadors.remove(boxeador);
        Entrenador entrenadorModificado = new Entrenador("Agustina", categorias, boxeadors);

        EntrenadorDto entrenadorDto = modelMapper.map(entrenadorModificado,EntrenadorDto.class);

        //Config
        Mockito.when(entrenadorServiceImp.actualizarEntrenador(entrenador.getNombre(),entrenadorDto)).thenThrow(new NotFoundException("El entrenador no fue encontrado."));
        Mockito.when(logErrorService.grabarError(new NotFoundException("El entrenador no fue encontrado."),this.getClass().getName())).thenReturn(true);

        given()
                .contentType(ContentType.JSON)
                .queryParam("nombre",entrenador.getNombre())
                .body(entrenadorDto)
                .when().put("/entrenadores")
                .then()
                .statusCode(404)
                .body(is("El entrenador no fue encontrado."));

    }

    @Test
    public void queSePuedaEliminarEntrenador(){

        //setup
        List<Categoria> cat1 = new ArrayList<>();

        cat1.add(new Categoria(1L,"Mosca",48.988,50.802));
        cat1.add(new Categoria(2L,"Gallo",52.163 ,53.525));
        Entrenador entrenador = new Entrenador("Agus",cat1,new ArrayList<Boxeador>());

        Mockito.when(this.entrenadorServiceImp.eliminarEntrenador(entrenador.getNombre())).thenReturn(true);

        given()
                .contentType(ContentType.JSON)
                .queryParam("nombre", entrenador.getNombre())
                .when()
                .delete("/entrenadores")
                .then()
                .statusCode(200)
                .body(is("Entrenador eliminado con exito"));
    }

    @Test
    public void queNoSePuedaEliminarEntrenadorInexistente(){

        EntrenadorDto entrenadorDto = new EntrenadorDto("Nuevo entrenador",null,null);

        Mockito.when(this.entrenadorServiceImp.eliminarEntrenador(entrenadorDto.getNombre())).thenThrow(new NotFoundException("El entrenador " + entrenadorDto.getNombre() + " no fue encontrado."));

        given()
                .contentType(ContentType.JSON)
                .queryParam("nombre", entrenadorDto.getNombre())
                .when()
                .delete("/entrenadores")
                .then()
                .statusCode(400)
                .body(is("El entrenador Nuevo entrenador no fue encontrado."));
    }
}
