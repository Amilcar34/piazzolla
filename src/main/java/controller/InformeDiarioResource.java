package controller;

import DTO.InformeDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import service.InformeDiarioServiceImp;

@Path("/informe")
public class InformeDiarioResource {

    @Inject
    InformeDiarioServiceImp informeDiarioServiceImp;

    @GET
    public InformeDTO imprimir(){
       return this.informeDiarioServiceImp.informeDiario();
    }
}
