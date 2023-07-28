package repository;

import model.Boxeador;
import model.Categoria;
import model.Entrenador;


import java.util.List;

public interface IEntrenadorRepository {
    List<Entrenador> getAllEntrenadores();
    Entrenador obtenerEntrenadorPorCategoria(Categoria categoria);
    Boolean addBoxeador(Entrenador entrenador, Boxeador boxeador);
    Integer obtenerBoxeadoresDelDia(Entrenador entrenador);
}
