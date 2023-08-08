package com.example.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.model.Categoria;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrenadorDto {

    private String nombre;
    @NotNull(message = "La categoria no puede ser nula")
    @NotEmpty(message = "La categoria no puede ser vacia")
    private List<Categoria> categorias;
    private List<BoxeadorSinEntreDto> boxeadores;

}
