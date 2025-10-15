package com.pepotec.cooperative_taxi_managment.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import com.pepotec.cooperative_taxi_managment.models.enums.MemberRole;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;


@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MemberEntity extends PersonEntity {

    @Column(name = "join_date", nullable = false)
    @NotNull(message = "The join date cannot be empty")
    @PastOrPresent(message = "The join date cannot be in the future")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate joinDate;

    @Column(name = "leave_date")
    @PastOrPresent(message = "The leave date cannot be in the future")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Builder.Default
    private LocalDate leaveDate = null;

    @Column(name = "role", nullable = false)
    @NotNull(message = "The role cannot be null")
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", nullable = false)
    private AddressEntity address;
}
