package com.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformeDTO {
    private Date dia;
    private List<EntrenadorInformeDTO> entrenadores;
    private Integer totalBoxeadores;
}
