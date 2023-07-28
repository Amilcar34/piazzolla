package repository;

import DTO.EntrenadorInformeDTO;
import model.Entrenador;

import java.sql.Date;
import java.util.List;

public interface IEntrenadorRepository {
    List<Entrenador> getAllEntrenadores();

    Integer obtenerBoxeadoresDelDia(Entrenador entrenador);
}
