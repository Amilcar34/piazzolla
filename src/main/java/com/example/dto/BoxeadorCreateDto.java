package com.example.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxeadorCreateDto {
   @Length(min = 3,message = "El nombre no puede tener menos de 3 cáracteres")
   private String nombre;
   @NotNull(message = "El peso no puede ser null")
   private Double peso;
}
