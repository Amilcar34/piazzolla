package DTO;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class InformeDTO {
    private Date dia;
    private List<EntrenadorInformeDTO> entrenadores;
    private Integer totalBoxeadores;
}
