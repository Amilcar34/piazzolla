package controller;

import DTO.BoxeadorDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import model.Boxeador;
import repository.BoxeadorRepository;
import service.BoxeadorServiceImp;

import java.sql.Date;
import java.util.List;

@Path("/boxeadores")
public class BoxeadorResource {
    @Inject
    BoxeadorServiceImp boxeadorServiceImp;
    @GET
    public List<Boxeador> list(){
        return this.boxeadorServiceImp.getAllBoxeadores();
    }

    @POST
    public BoxeadorDTO create(BoxeadorDTO boxeadorDTO){
        return this.boxeadorServiceImp.create(boxeadorDTO);
    }
}
