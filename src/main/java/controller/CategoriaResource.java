package controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import model.Categoria;
import service.CategoriaServiceImp;

import java.util.List;
import java.util.Set;

@Path("/categorias")
public class CategoriaResource {
    @Inject
    CategoriaServiceImp categoriaServiceImp;

    @GET
    public List<Categoria> list() {
        return this.categoriaServiceImp.getAllCategorias();
    }

    @POST
    public Response create(Categoria categoria){
        this.categoriaServiceImp.create(categoria);
        return Response.status(Response.Status.CREATED).entity(categoria).build();
    }

    @PUT
    @Path("/{id}")
    public Response update( Long id, Categoria categoria){
        try {
            this.categoriaServiceImp.update(id,categoria);
            return Response.ok().entity(categoria).build();
        }catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }
}
