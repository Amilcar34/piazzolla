package com.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxeadorCreateDto {
   @NotNull(message = "El nombre no puede ser null")
   @Min(value = 3, message = "El nombre no puede tener menos de 3 carácteres")
   private String nombre;
   @NotNull(message = "El peso no puede ser null")
   private Double peso;
}
