package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Boxeador {

    private String nombre;
    private Double peso;
    private Categoria categoria;
    private Entrenador entrenador;
    private Date fechaIngreso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boxeador boxeador = (Boxeador) o;
        return Objects.equals(nombre, boxeador.nombre) && Objects.equals(peso, boxeador.peso) && Objects.equals(categoria, boxeador.categoria) && Objects.equals(entrenador, boxeador.entrenador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, peso, categoria, entrenador);
    }
}
