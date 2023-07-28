package controller;

import DTO.BoxeadorDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import service.BoxeadorServiceImp;

import java.util.List;

@Path("/boxeadores")
public class BoxeadorResource {
    @Inject
    BoxeadorServiceImp boxeadorServiceImp;
    @GET
    public List<BoxeadorDTO> list(){
        return this.boxeadorServiceImp.getAllBoxeadores();
    }

    @POST
    public Response create(BoxeadorDTO boxeadorDTO)  {
        try {
            BoxeadorDTO boxeador = this.boxeadorServiceImp.create(boxeadorDTO);
            return Response.status(Response.Status.CREATED).entity(boxeador).build();
        }
        catch (Exception e){
            return Response.status(402).entity(e.getMessage()).build();
        }

    }
}
