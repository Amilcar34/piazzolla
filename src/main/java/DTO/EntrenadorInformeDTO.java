package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrenadorInformeDTO {
    private String nombre;
    private Integer boxeadoresAsignadosHoy;
}
