package com.pepotec.cooperative_taxi_managment.models.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Table(name = "people")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person", unique = true, nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "The name cannot be empty")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Only letters and spaces are allowed")
    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters")
    private String firstName;

    @Column(name = "second_name")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Only letters and spaces are allowed")
    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters")
    private String secondName;

    @Column(name = "father_surname", nullable = false)
    @NotBlank(message = "The father's surname cannot be empty")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Only letters and spaces are allowed")
    @Size(min = 2, max = 50, message = "The surname must be between 2 and 50 characters")
    private String fatherSurname;

    @Column(name = "mother_surname")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Only letters and spaces are allowed")
    @Size(min = 2, max = 50, message = "The surname must be between 2 and 50 characters")
    private String motherSurname;

    @Column(name = "dni", nullable = false, unique = true)
    @NotBlank(message = "The DNI cannot be empty")
    @Pattern(regexp = "^[1-9]\\d{6,7}$", message = "The DNI must have 7 or 8 digits, without leading zeros")
    @Size(min = 7, max = 8, message = "The DNI must have 7 or 8 digits")
    private String dni;

    @Column(name = "cuit", nullable = false, unique = true)
    @NotBlank(message = "The CUIT cannot be empty")
    @Pattern(regexp = "^[1-9]\\d{9,10}$", message = "The CUIT must have 10 or 11 digits, without leading zeros")
    @Size(min = 10, max = 11, message = "The CUIT must have 10 or 11 digits")
    private String cuit;

    @Column(name = "phone", nullable = false)
    @NotBlank(message = "The phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "The phone number must contain only digits and be between 10 and 15 characters long")
    @Size(min = 10, max = 15, message = "The phone number must be between 10 and 15 characters long")
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "The email cannot be empty")
    @Email(message = "The email must be a valid email address")
    private String email;

    @Column(name = "birth_date", nullable = false)
    @NotNull(message = "The birth date cannot be empty")
    @Past(message = "The birth date cannot be in the future")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @Column(name = "active", nullable = false)
    @NotNull(message = "The active status cannot be null")
    private Boolean active = true;
}
