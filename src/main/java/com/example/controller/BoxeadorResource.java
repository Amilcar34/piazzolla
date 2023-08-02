package com.example.controller;

import com.example.dto.BoxeadorDTO;
import com.example.service.BoxeadorServiceImp;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import com.example.service.LogErrorService;

import java.io.IOException;
import java.util.List;

@Path("/boxeadores")
public class BoxeadorResource {
    @Inject
    BoxeadorServiceImp boxeadorServiceImp;

    @Inject
    LogErrorService logErrorService;

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
            this.logErrorService.grabarError(e,this.getClass().getName());
            return Response.status(402).entity(e.getMessage()).build();
        }

    }
}
