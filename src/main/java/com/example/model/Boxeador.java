package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Boxeador {
    private String nombre;
    private Double peso;
    private Categoria categoria;
    private Entrenador entrenador;
    @EqualsAndHashCode.Exclude
    private Date fechaIngreso;

}
