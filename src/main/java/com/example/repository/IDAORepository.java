package com.example.repository;

import com.example.model.Boxeador;

import java.util.List;
import java.util.Optional;

/**
 * interface creada para implementar la logica basica de los repositorios
 * params
 * @K entity
 * @T id
 *
 */

public interface IDAORepository<K,T>{
    List<K> getAll();
    Boolean create(K k);
    Optional<K> find(T t);
}
