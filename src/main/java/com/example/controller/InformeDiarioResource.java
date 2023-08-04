package com.example.controller;

import com.example.dto.informe.InformeDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import com.example.service.InformeDiarioServiceImp;

@Path("/informe")
public class InformeDiarioResource {

    @Inject
    InformeDiarioServiceImp informeDiarioServiceImp;

    @GET
    public InformeDto imprimir(){
       return this.informeDiarioServiceImp.informeDiario();
    }
}
