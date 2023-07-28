package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import model.Categoria;

import java.util.*;


@ApplicationScoped
public class CategoriaRepository implements ICategoriaRepository{

    List<Categoria> categorias = new ArrayList<>();

    public CategoriaRepository() {
        categorias.add(new Categoria(1L,"Mosca",48.988,50.802));
        categorias.add(new Categoria(2L,"Gallo",52.163 ,53.525));
        categorias.add(new Categoria(3L,"Pluma",55.338,57.152));
        categorias.add(new Categoria(4L,"Ligero",58.967,61.237));
        categorias.add(new Categoria(5L,"Welter",63.503,66.678));
        categorias.add(new Categoria(6L,"Mediano",69.853,72.562));
        categorias.add(new Categoria(7L,"Mediopesado",76.205,79.378));
        categorias.add(new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE));
    }

    @Override
    public List<Categoria> getAllCategorias() {
        return this.categorias;
    }

    @Override
    public Categoria save(Categoria categoria) {
        this.categorias.add(categoria);
        return categoria;
    }

    @Override
    public Optional<Categoria> findById(Long idCategoria) {
        Optional<Categoria> categoriaExist = this.categorias.stream().filter(c -> Objects.equals(c.get_id(), idCategoria)).findFirst();
        return categoriaExist;
    }

}
