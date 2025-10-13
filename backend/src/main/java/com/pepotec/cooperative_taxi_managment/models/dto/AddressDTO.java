package com.pepotec.cooperative_taxi_managment.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private Long id;
    @NotBlank(message = "La calle no puede estar vacía")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Solo se permiten letras y espacios")
    @Size(min = 2, max = 50, message = "La calle debe tener entre 2 y 50 caracteres")
    private String street;
    @NotBlank(message = "El número no puede estar vacío")
    @Pattern(regexp = "^[0-9]+$", message = "El número debe contener solo dígitos")
    @Size(min = 1, max = 5, message = "El número debe tener entre 1 y 5 dígitos")
    private String numeral;
    @NotBlank(message = "La ciudad no puede estar vacía")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Solo se permiten letras y espacios")
    @Size(min = 2, max = 50, message = "La ciudad debe tener entre 2 y 50 caracteres")
    private String city;
    @Pattern(regexp = "^[0-9]+$", message = "El piso debe contener solo dígitos")
    @Size(min = 1, max = 5, message = "El piso debe tener entre 1 y 5 dígitos")
    private String floor;
    @Size(min = 1, max = 5, message = "El departamento debe tener entre 1 y 5 caracteres")
    private String apartment;

}
