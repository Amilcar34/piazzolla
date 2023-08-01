package com.example.controller;

import com.example.DTO.EntrenadorDTO;
import com.example.service.EntrenadorServiceImp;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

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
