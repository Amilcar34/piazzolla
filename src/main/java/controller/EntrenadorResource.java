package controller;

import DTO.EntrenadorDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import service.EntrenadorServiceImp;

import java.util.List;

@Path("/entrenadores")
public class EntrenadorResource {

    @Inject
    EntrenadorServiceImp entrenadorServiceImp;

    @GET
    public List<EntrenadorDTO> list() {
        return this.entrenadorServiceImp.getAllEntrenadores();
    }
}
