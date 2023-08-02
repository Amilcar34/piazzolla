package com.example.repository;

import com.example.model.Boxeador;

import java.util.List;
import java.util.Optional;

/**
 * interface creada para implementar la logica basica de los repositorios
 * params
 * @K entity
 * @T id
 * @R variable_condition
 */

public interface IDAORepository<K,T>{
    List<K> getAll();
    K create(K k);
    Optional<K> find(T t);
}
