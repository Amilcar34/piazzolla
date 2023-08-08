package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    public static final Double SIN_LIMITE = 999D;

    private Long id;
    private String nombre;
    private Double pesoMin;
    private Double pesoMax;

}
