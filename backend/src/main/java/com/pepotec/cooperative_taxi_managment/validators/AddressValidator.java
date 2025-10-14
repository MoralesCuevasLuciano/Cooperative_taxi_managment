package com.pepotec.cooperative_taxi_managment.validators;

import com.pepotec.cooperative_taxi_managment.models.dto.AddressDTO;
import com.pepotec.cooperative_taxi_managment.exceptions.InvalidDataException;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class AddressValidator {

    private static final Pattern STREET_PATTERN = Pattern.compile("^[\\p{L} ]+$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");

    public void validateAddress(AddressDTO address) {
        validateRequiredFields(address);
        validateFormats(address);
    }

    private void validateRequiredFields(AddressDTO address) {
        if (address == null) {
            throw new InvalidDataException("La dirección no puede ser nula");
        }
        
        if (address.getStreet() == null || address.getStreet().trim().isEmpty()) {
            throw new InvalidDataException("La calle no puede estar vacía");
        }
        
        if (address.getNumeral() == null || address.getNumeral().trim().isEmpty()) {
            throw new InvalidDataException("El número no puede estar vacío");
        }
        
        if (address.getCity() == null || address.getCity().trim().isEmpty()) {
            throw new InvalidDataException("La ciudad no puede estar vacía");
        }
    }

    private void validateFormats(AddressDTO address) {
        // Validar calle
        if (!STREET_PATTERN.matcher(address.getStreet()).matches()) {
            throw new InvalidDataException("La calle solo puede contener letras y espacios");
        }
        
        if (address.getStreet().length() < 2 || address.getStreet().length() > 50) {
            throw new InvalidDataException("La calle debe tener entre 2 y 50 caracteres");
        }
        
        // Validar número
        if (!NUMBER_PATTERN.matcher(address.getNumeral()).matches()) {
            throw new InvalidDataException("El número debe contener solo dígitos");
        }
        
        if (address.getNumeral().length() > 5) {
            throw new InvalidDataException("El número debe tener máximo 5 dígitos");
        }
        
        // Validar ciudad
        if (!STREET_PATTERN.matcher(address.getCity()).matches()) {
            throw new InvalidDataException("La ciudad solo puede contener letras y espacios");
        }
        
        if (address.getCity().length() < 2 || address.getCity().length() > 50) {
            throw new InvalidDataException("La ciudad debe tener entre 2 y 50 caracteres");
        }
        
        // Validar piso (opcional)
        if (address.getFloor() != null && !address.getFloor().trim().isEmpty()) {
            if (!NUMBER_PATTERN.matcher(address.getFloor()).matches()) {
                throw new InvalidDataException("El piso debe contener solo dígitos");
            }
            if (address.getFloor().length() > 5) {
                throw new InvalidDataException("El piso debe tener máximo 5 dígitos");
            }
        }
        
        // Validar apartamento (opcional)
        if (address.getApartment() != null && !address.getApartment().trim().isEmpty()) {
            if (address.getApartment().length() > 5) {
                throw new InvalidDataException("El departamento debe tener máximo 5 caracteres");
            }
        }
    }
}