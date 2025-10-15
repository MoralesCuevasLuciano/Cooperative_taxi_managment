package com.pepotec.cooperative_taxi_managment.models.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO extends MemberDTO {
    @NotNull(message = "La fecha de expiración del registro no puede estar vacía")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate expirationRegistrationDate;
}
