package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    public static final Double SIN_LIMITE = 999D;

    public Long _id;
    public String nombre;
    public Double pesoMin;
    public Double pesoMax;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(_id, categoria._id) && Objects.equals(nombre, categoria.nombre) && Objects.equals(pesoMin, categoria.pesoMin) && Objects.equals(pesoMax, categoria.pesoMax);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }
}
