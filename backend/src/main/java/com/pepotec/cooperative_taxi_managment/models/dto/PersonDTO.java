package com.pepotec.cooperative_taxi_managment.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotNull;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class PersonDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Solo se permiten letras y espacios")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String firstName;

    @Pattern(regexp = "^[\\p{L} ]+$", message = "Solo se permiten letras y espacios")
    @Size(min = 2, max = 50, message = "El segundo nombre debe tener entre 2 y 50 caracteres")
    private String secondName;

    @NotBlank(message = "El apellido paterno no puede estar vacío")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Solo se permiten letras y espacios")
    @Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 50 caracteres")
    private String fatherSurname;

    @Pattern(regexp = "^[\\p{L} ]+$", message = "Solo se permiten letras y espacios")
    @Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 50 caracteres")
    private String motherSurname;

    @NotBlank(message = "El dni no puede estar vacío")
    @Pattern(regexp = "^[1-9]\\d{6,7}$", message = "El dni debe tener entre 7 y 8 dígitos, sin ceros iniciales")
    @Size(min = 7, max = 8, message = "El dni debe tener entre 7 y 8 dígitos")
    private String dni;

    @NotBlank(message = "El cuit no puede estar vacío")
    @Pattern(regexp = "^[1-9]\\d{9,10}$", message = "El cuit debe tener entre 10 y 11 dígitos, sin ceros iniciales")
    @Size(min = 10, max = 11, message = "El cuit debe tener entre 10 y 11 dígitos")
    private String cuit;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "El teléfono debe tener entre 10 y 15 dígitos")
    @Size(min = 10, max = 15, message = "El teléfono debe tener entre 10 y 15 dígitos")
    private String phone;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    @Past(message = "La fecha de nacimiento no puede ser futura")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
}
