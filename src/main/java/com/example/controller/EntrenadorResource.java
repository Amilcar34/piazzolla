package com.example.controller;

import com.example.dto.EntrenadorDto;
import com.example.service.EntrenadorServiceImp;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/entrenadores")
public class EntrenadorResource {

    @Inject
    EntrenadorServiceImp entrenadorServiceImp;

    @GET
    public List<EntrenadorDto> list() {
        return this.entrenadorServiceImp.getAllEntrenadores();
    }
}
