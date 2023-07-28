package controller;

import DTO.EntrenadorDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import model.Boxeador;
import model.Categoria;
import model.Entrenador;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import service.CategoriaServiceImp;
import service.EntrenadorServiceImp;

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

        List<EntrenadorDTO> entrenadorDTOS = entrenadores.stream().map(e -> modelMapper.map(e,EntrenadorDTO.class))
                                            .collect(Collectors.toList());

        //Config
        Mockito.when(entrenadorServiceImp.getAllEntrenadores()).thenReturn(entrenadorDTOS);

        given()
                .contentType(ContentType.JSON)
                .body(entrenadorDTOS)
                .when().get("/entrenadores")
                .then()
                .statusCode(200)
                .body(is("[{\"nombre\":\"Agus\",\"categorias\":[{\"_id\":1,\"nombre\":\"Mosca\",\"pesoMin\":48.988,\"pesoMax\":50.802},{\"_id\":2,\"nombre\":\"Gallo\",\"pesoMin\":52.163,\"pesoMax\":53.525}],\"boxeadores\":[]}]"));
    }
}
