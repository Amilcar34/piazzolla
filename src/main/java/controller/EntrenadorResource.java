package controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import model.Categoria;
import model.Entrenador;
import repository.EntrenadorRepository;
import service.CategoriaServiceImp;

import java.util.List;
import java.util.Set;

@Path("/entrenadores")
public class EntrenadorResource {

    @Inject
    EntrenadorRepository entrenadorRepository;

    @GET
    public List<Entrenador> list() {
        return this.entrenadorRepository.getAllEntrenadores();
    }
}
