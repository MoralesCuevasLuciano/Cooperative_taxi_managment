package com.pepotec.cooperative_taxi_managment.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class DriverEntity extends PersonEntity{
    @Column(name = "expiration_registration_date", nullable = false)
    @NotNull(message = "The expiration registration date cannot be empty")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate expirationRegistrationDate;
}
