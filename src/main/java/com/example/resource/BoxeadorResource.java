package com.example.resource;

import com.example.dto.BoxeadorCreateDto;
import com.example.dto.BoxeadorDto;
import com.example.service.IBoxeadorService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.example.service.LogErrorService;

import java.io.IOException;
import java.util.List;

@Path("/boxeadores")
public class BoxeadorResource {
    @Inject
    IBoxeadorService boxeadorService;

    @Inject
    LogErrorService logErrorService;

    @GET
    public List<BoxeadorDto> list(){
        return this.boxeadorService.getAllBoxeadores();
    }

    @POST
    public Response create(@Valid BoxeadorCreateDto boxeadorCreateDto) throws IOException {
        try {
            BoxeadorDto boxeador = this.boxeadorService.create(boxeadorCreateDto);
            return Response.status(Response.Status.CREATED).entity(boxeador).build();
        }
        catch (Exception e){
            this.logErrorService.grabarError(e,this.getClass().getName());
            return Response.status(402).entity(e.getMessage()).build();
        }
    }

    @PUT
    public Response update(@QueryParam("nombre") String nombre, BoxeadorDto boxeadorDto) throws IOException {
        try {
            this.boxeadorService.actualizar(nombre,boxeadorDto);
            return Response.ok().entity(boxeadorDto).build();
        }catch (NotFoundException e){
            logErrorService.grabarError(e,this.getClass().getName());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }

    @DELETE
    public Response eliminarBoxeador(@QueryParam("nombre") String nombre) throws IOException {
        try {
            this.boxeadorService.eliminar(nombre);
            return Response.ok().entity("Boxeador eliminado con exito").build();
        }catch (NotFoundException e){
            logErrorService.grabarError(e,this.getClass().getName());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
