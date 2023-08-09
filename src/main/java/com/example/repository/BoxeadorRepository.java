package com.example.repository;

import com.example.model.Entrenador;
import jakarta.enterprise.context.ApplicationScoped;
import com.example.model.Boxeador;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class BoxeadorRepository implements IDAORepository<Boxeador , String>{

    List<Boxeador> boxeadores = new ArrayList<>();
    @Override
    public List<Boxeador> getAll() {
        return boxeadores;
    }


    @Override
    public Boxeador create(Boxeador boxeador) {
         this.boxeadores.add(boxeador);
         return boxeador;
    }

    @Override
    public Optional<Boxeador> find(String nombre) {
        return this.boxeadores.stream().filter(b -> Objects.equals(b.getNombre(), nombre)).findFirst();
    }

    @Override
    public Boolean delete(Boxeador boxeador){
        return this.boxeadores.remove(boxeador);
    }

    public Boolean eliminarEntrenador(Entrenador entrenador){
        for (Boxeador box: this.boxeadores) {
            if(box.getEntrenador().getNombre().equals(entrenador.getNombre())){
                box.setEntrenador(null);
                return true;
            }
        }
        return false;
    }


    public Integer cantBoxeadoresPorDia() {

        List<Boxeador> boxeadorList = this.boxeadores.stream().filter(b -> (
                b.getFechaIngreso().toLocalDate().equals(new Date(System.currentTimeMillis()).toLocalDate())))
                .collect(Collectors.toList());

        return boxeadorList.size();
    }
}
