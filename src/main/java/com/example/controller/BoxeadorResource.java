package com.example.controller;

import com.example.dto.BoxeadorCreateDto;
import com.example.dto.BoxeadorDto;
import com.example.service.BoxeadorServiceImp;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.example.service.LogErrorService;

import java.util.List;

@Path("/boxeadores")
public class BoxeadorResource {
    @Inject
    BoxeadorServiceImp boxeadorServiceImp;

    @Inject
    LogErrorService logErrorService;

    @GET
    public List<BoxeadorDto> list(){
        return this.boxeadorServiceImp.getAllBoxeadores();
    }

    @POST
    public Response create(BoxeadorCreateDto boxeadorCreateDto)  {
        try {
            BoxeadorDto boxeador = this.boxeadorServiceImp.create(boxeadorCreateDto);
            return Response.status(Response.Status.CREATED).entity(boxeador).build();
        }
        catch (Exception e){
            this.logErrorService.grabarError(e,this.getClass().getName());
            return Response.status(402).entity(e.getMessage()).build();
        }

    }

    @DELETE
    public Response eliminarBoxeador(@QueryParam("nombre") String nombre){
        this.boxeadorServiceImp.eliminar(nombre);
        return Response.ok().entity("Boxeador eliminado con exito").build();
    }
}
