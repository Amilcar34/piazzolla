package com.example.dto.informe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformeDto {
    private Date dia;
    private List<EntrenadorInformeDto> entrenadores;
    private Integer totalBoxeadores;
}
