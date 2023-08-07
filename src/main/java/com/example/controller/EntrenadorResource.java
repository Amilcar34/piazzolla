package com.example.controller;

import com.example.dto.EntrenadorDto;
import com.example.model.Entrenador;
import com.example.service.EntrenadorServiceImp;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/entrenadores")
public class EntrenadorResource {

    @Inject
    EntrenadorServiceImp entrenadorServiceImp;

    @GET
    public List<EntrenadorDto> list() {
        return this.entrenadorServiceImp.getAllEntrenadores();
    }

    @POST
    public Response crear(EntrenadorDto entrenadorDto) {
        try {
            this.entrenadorServiceImp.crearEntrenador(entrenadorDto);
            return Response.status(Response.Status.CREATED).entity(entrenadorDto).build();

        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }
}
