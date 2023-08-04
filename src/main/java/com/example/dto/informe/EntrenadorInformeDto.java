package com.example.dto.informe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrenadorInformeDto {
    private String nombre;
    private Integer boxeadoresAsignadosHoy;
}
