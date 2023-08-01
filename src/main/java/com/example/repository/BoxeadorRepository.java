package com.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.example.model.Boxeador;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BoxeadorRepository implements IBoxeadorRepository{

    List<Boxeador> boxeadores = new ArrayList<>();
    @Override
    public List<Boxeador> getAllBoxeadores() {
        return boxeadores;
    }

    @Override
    public Boxeador create(Boxeador boxeador) {
        this.boxeadores.add(boxeador);
        return boxeador;
    }

    @Override
    public Integer cantBoxeadoresPorDia() {

        List<Boxeador> boxeadores = this.boxeadores.stream().filter(b -> (
                b.getFechaIngreso().toLocalDate().equals(new Date(System.currentTimeMillis()).toLocalDate())))
                .collect(Collectors.toList());

        return boxeadores.size();
    }
}
