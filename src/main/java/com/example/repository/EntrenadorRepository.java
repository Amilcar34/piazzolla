package com.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class EntrenadorRepository implements IDAORepository<Entrenador , String >{

    List<Entrenador> entrenadores = new ArrayList<>();


    public EntrenadorRepository(){
        List<Categoria> cat1 = new ArrayList<>();

        cat1.add(new Categoria(1L,"Mosca",48.988,50.802));
        cat1.add(new Categoria(2L,"Gallo",52.163 ,53.525));
        this.create(new Entrenador("Agus",cat1,new ArrayList<>()));

        List<Categoria> cat2 = new ArrayList<>();

        cat2.add(new Categoria(3L,"Pluma",55.338,57.152));
        cat2.add(new Categoria(4L,"Ligero",58.967,61.237));
        this.create(new Entrenador("Pablo",cat2,new ArrayList<>()));

        List<Categoria> cat3 = new ArrayList<>();

        cat3.add(new Categoria(5L,"Welter",63.503,66.678));
        cat3.add(new Categoria(6L,"Mediano",69.853,72.562));
        this.create(new Entrenador("Flor",cat3,new ArrayList<>()));

        List<Categoria> cat4 = new ArrayList<>();


        cat4.add(new Categoria(7L,"Mediopesado",76.205,79.378));
        cat4.add(new Categoria(8L,"Pesado",91D,Categoria.SIN_LIMITE));
        this.create(new Entrenador("Juan",cat4,new ArrayList<>()));

    }

    @Override
    public List<Entrenador> getAll() {
        return this.entrenadores;
    }


    @Override
    public Entrenador create(Entrenador entrenador) {
         this.entrenadores.add(entrenador);
         return entrenador;
    }

    @Override
    public Optional<Entrenador> find(String nombre) {
        return this.entrenadores.stream().filter(e -> Objects.equals(e.getNombre(), nombre)).findFirst();
    }

    @Override
    public Boolean delete(Entrenador entrenador) {
      return this.entrenadores.remove(entrenador);
    }

    public Entrenador addBoxeador(Entrenador entrenador, Boxeador boxeador) {
        for (Entrenador en: this.entrenadores) {
            if(entrenador.getNombre().equals(en.getNombre())){
                en.getBoxeadores().add(boxeador);
                return en;
            }
        }
        return null;
    }

    public Boolean deleteBoxeador(Entrenador entrenador,Boxeador boxeador){

        for (Entrenador e: this.entrenadores) {
            if(e.getNombre().equals(entrenador.getNombre())) {
               return e.getBoxeadores().removeIf(b -> b.getNombre().equals(boxeador.getNombre()));
            }
        }

        return false;
    }


    public Integer obtenerBoxeadoresDelDia(Entrenador entrenador) {
        List<Boxeador> boxeadores = entrenador.getBoxeadores()
                                    .stream().filter(b -> b.getFechaIngreso().toLocalDate().equals(new Date(System.currentTimeMillis()).toLocalDate()))
                                    .collect(Collectors.toList());

        return boxeadores.size();
    }

    public Entrenador obtenerEntrenadorPorCategoria(Categoria categoria) {
        for (Entrenador entrenador: this.entrenadores) {
            if(entrenador.getCategorias().contains(categoria)){
                return entrenador;
            }
        }
        return null;
    }



}
