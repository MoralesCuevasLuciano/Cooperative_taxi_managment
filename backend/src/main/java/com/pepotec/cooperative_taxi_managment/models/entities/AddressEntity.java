package com.pepotec.cooperative_taxi_managment.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address", unique = true, nullable = false)
    private Long id;
    
    @Column(name = "street", nullable = false)
    @NotBlank(message = "The street cannot be empty")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Only letters and spaces are allowed")
    @Size(min = 2, max = 50, message = "The street must be between 2 and 50 characters")
    private String street;
    
    @Column(name = "numeral", nullable = false)
    @NotBlank(message = "The numeral cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "The numeral must contain only digits")
    @Size(min = 1, max = 5, message = "The numeral must be between 1 and 5 digits")
    private String numeral;
    
    
    @Column(name = "city", nullable = false)
    @NotBlank(message = "The city cannot be empty")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Only letters and spaces are allowed")
    @Size(min = 2, max = 50, message = "The city must be between 2 and 50 characters")
    private String city;
    
    
    @Column(name = "floor")
    @Pattern(regexp = "^[0-9]+$", message = "The floor must contain only digits")
    @Size(min = 1, max = 5, message = "The floor must be between 1 and 5 digits")
    private String floor;
    
    @Column(name = "apartment")
    @Size(min = 1, max = 5, message = "The apartment must be between 1 and 5 characters")
    private String apartment;
}
