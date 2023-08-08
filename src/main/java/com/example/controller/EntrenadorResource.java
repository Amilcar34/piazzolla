package com.example.controller;

import com.example.dto.EntrenadorDto;
import com.example.service.EntrenadorServiceImp;
import com.example.service.LogErrorService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;

@Path("/entrenadores")
public class EntrenadorResource {

    @Inject
    EntrenadorServiceImp entrenadorServiceImp;

    @Inject
    LogErrorService logErrorService;

    @GET
    public List<EntrenadorDto> list() {
        return this.entrenadorServiceImp.getAllEntrenadores();
    }

    @POST
    public Response crear(@Valid EntrenadorDto entrenadorDto) {
        try {
            this.entrenadorServiceImp.crearEntrenador(entrenadorDto);
            return Response.status(Response.Status.CREATED).entity(entrenadorDto).build();

        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    public Response update(@QueryParam("nombre") String nombre, EntrenadorDto entrenadorDto) throws IOException {
        try {
            this.entrenadorServiceImp.actualizarEntrenador(nombre,entrenadorDto);
            return Response.ok().entity(entrenadorDto).build();
        }catch (NotFoundException e){
            logErrorService.grabarError(e,this.getClass().getName());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }

    @DELETE
    public Response eliminar(@QueryParam("nombre") String nombre){
        try{
            this.entrenadorServiceImp.eliminarEntrenador(nombre);
            return Response.ok().entity("Entrenador eliminado con exito").build();
        }catch (NotFoundException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
