package DTO;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class InformeDTO {
    Date dia;
    List<EntrenadorInformeDTO> entrenadores;
    Integer totalBoxeadores;
}
