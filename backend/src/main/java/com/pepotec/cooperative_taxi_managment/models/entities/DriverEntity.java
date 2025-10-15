package com.pepotec.cooperative_taxi_managment.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import lombok.experimental.SuperBuilder;
@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DriverEntity extends MemberEntity{
    @Column(name = "expiration_registration_date", nullable = false)
    @NotNull(message = "The expiration registration date cannot be empty")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate expirationRegistrationDate;
}
