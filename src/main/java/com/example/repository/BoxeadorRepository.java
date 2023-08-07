package com.example.repository;

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
    public Boolean create(Boxeador boxeador) {
       return this.boxeadores.add(boxeador);
    }

    @Override
    public Optional<Boxeador> find(String nombre) {
        Optional<Boxeador> boxExiste = this.boxeadores.stream().filter(b -> Objects.equals(b.getNombre(), nombre)).findFirst();
        return boxExiste;
    }


    public Integer cantBoxeadoresPorDia() {

        List<Boxeador> boxeadores = this.boxeadores.stream().filter(b -> (
                b.getFechaIngreso().toLocalDate().equals(new Date(System.currentTimeMillis()).toLocalDate())))
                .collect(Collectors.toList());

        return boxeadores.size();
    }

    public Boolean delete(Boxeador boxeador){
       return this.boxeadores.remove(boxeador);
    }
}
