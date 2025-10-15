package com.pepotec.cooperative_taxi_managment.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Builder;
import java.util.List;
import java.util.ArrayList;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.Valid;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberDTO extends PersonDTO {
    
    @Builder.Default
    @Size(max = 10, message = "No se pueden tener más de 10 números de licencia")
    @Valid
    private List<@Pattern(regexp = "^\\d{4}$", message = "Cada número de licencia debe tener exactamente 4 dígitos") String> licenceNumbers = new ArrayList<>();
}