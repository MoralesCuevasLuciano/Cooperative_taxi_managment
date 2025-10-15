package com.pepotec.cooperative_taxi_managment.models.dto;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import jakarta.validation.constraints.PastOrPresent;
import com.pepotec.cooperative_taxi_managment.models.enums.MemberRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor    
public class MemberDTO extends PersonDTO {
    @NotNull(message = "La fecha de ingreso no puede ser nula")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate joinDate;

    @PastOrPresent(message = "La fecha de salida no puede ser futura")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate leaveDate;

    @NotNull(message = "El rol no puede ser nulo")
    private MemberRole role;

    @Valid
    @NotNull(message = "La dirección no puede ser nula")
    private AddressDTO address;

    @NotNull(message = "El estado no puede ser nulo")
    private Boolean active;
}
