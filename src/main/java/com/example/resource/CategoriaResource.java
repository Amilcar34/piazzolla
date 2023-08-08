package com.example.resource;

import com.example.model.Categoria;
import com.example.service.CategoriaServiceImp;
import com.example.service.LogErrorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;

@Path("/categorias")
public class CategoriaResource {
    @Inject
    CategoriaServiceImp categoriaServiceImp;

    @Inject
    LogErrorService logErrorService;

    @GET
    public List<Categoria> list() {
        return this.categoriaServiceImp.obtenerCategorias();
    }

    @POST
    public Response create(Categoria categoria){
        this.categoriaServiceImp.crearCategoria(categoria);
        return Response.status(Response.Status.CREATED).entity(categoria).build();
    }

    @PUT
    @Path("/{id}")
    public Response update( Long id, Categoria categoria) throws IOException {
        try {
            this.categoriaServiceImp.actualizarCategoria(id,categoria);
            return Response.ok().entity(categoria).build();
        }catch (NotFoundException e){
            logErrorService.grabarError(e,this.getClass().getName());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }
}
