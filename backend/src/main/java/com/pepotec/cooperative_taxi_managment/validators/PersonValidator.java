package com.pepotec.cooperative_taxi_managment.validators;

import com.pepotec.cooperative_taxi_managment.exceptions.InvalidDataException;
import com.pepotec.cooperative_taxi_managment.exceptions.DuplicateFieldException;
import com.pepotec.cooperative_taxi_managment.models.dto.PersonDTO;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.function.Function;
import java.util.Optional;

@Component
public class PersonValidator {

    // Regex patterns para validaciones
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} ]+$");
    private static final Pattern DNI_PATTERN = Pattern.compile("^[1-9]\\d{6,7}$");
    private static final Pattern CUIT_PATTERN = Pattern.compile("^[1-9]\\d{9,10}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /**
     * Método principal de validación - Punto de entrada para PersonDTO
     * Valida todos los datos básicos de persona antes de crear/actualizar
     */
    public void validatePersonData(PersonDTO person) {
        validateRequiredFields(person);
        validateFormats(person);
        validateCuitDniMatch(person);
        validateBirthDate(person);
    }

    /**
     * Valida que los campos obligatorios de Person no estén vacíos
     */
    private void validateRequiredFields(PersonDTO person) {
        if (person.getFirstName() == null || person.getFirstName().trim().isEmpty()) {
            throw new InvalidDataException("El nombre no puede estar vacío");
        }
        
        if (person.getFatherSurname() == null || person.getFatherSurname().trim().isEmpty()) {
            throw new InvalidDataException("El apellido paterno no puede estar vacío");
        }
        
        if (person.getDni() == null || person.getDni().trim().isEmpty()) {
            throw new InvalidDataException("El DNI no puede estar vacío");
        }
        
        if (person.getCuit() == null || person.getCuit().trim().isEmpty()) {
            throw new InvalidDataException("El CUIT no puede estar vacío");
        }
        
        if (person.getEmail() == null || person.getEmail().trim().isEmpty()) {
            throw new InvalidDataException("El email no puede estar vacío");
        }
        
        if (person.getPhone() == null || person.getPhone().trim().isEmpty()) {
            throw new InvalidDataException("El teléfono no puede estar vacío");
        }
        
        if (person.getBirthDate() == null) {
            throw new InvalidDataException("La fecha de nacimiento no puede estar vacía");
        }
    }

    /**
     * Valida el formato de los campos básicos de Person (DNI, CUIT, email, teléfono, nombres)
     */
    private void validateFormats(PersonDTO person) {
        // Validar formato del nombre
        if (!NAME_PATTERN.matcher(person.getFirstName()).matches()) {
            throw new InvalidDataException("El nombre solo puede contener letras y espacios");
        }
        
        if (person.getFirstName().length() < 2 || person.getFirstName().length() > 50) {
            throw new InvalidDataException("El nombre debe tener entre 2 y 50 caracteres");
        }
        
        // Validar segundo nombre si existe
        if (person.getSecondName() != null && !person.getSecondName().trim().isEmpty()) {
            if (!NAME_PATTERN.matcher(person.getSecondName()).matches()) {
                throw new InvalidDataException("El segundo nombre solo puede contener letras y espacios");
            }
            if (person.getSecondName().length() < 2 || person.getSecondName().length() > 50) {
                throw new InvalidDataException("El segundo nombre debe tener entre 2 y 50 caracteres");
            }
        }
        
        // Validar apellido paterno
        if (!NAME_PATTERN.matcher(person.getFatherSurname()).matches()) {
            throw new InvalidDataException("El apellido paterno solo puede contener letras y espacios");
        }
        
        if (person.getFatherSurname().length() < 2 || person.getFatherSurname().length() > 50) {
            throw new InvalidDataException("El apellido paterno debe tener entre 2 y 50 caracteres");
        }
        
        // Validar apellido materno si existe
        if (person.getMotherSurname() != null && !person.getMotherSurname().trim().isEmpty()) {
            if (!NAME_PATTERN.matcher(person.getMotherSurname()).matches()) {
                throw new InvalidDataException("El apellido materno solo puede contener letras y espacios");
            }
            if (person.getMotherSurname().length() < 2 || person.getMotherSurname().length() > 50) {
                throw new InvalidDataException("El apellido materno debe tener entre 2 y 50 caracteres");
            }
        }
        
        // Validar DNI
        if (!DNI_PATTERN.matcher(person.getDni()).matches()) {
            throw new InvalidDataException("El DNI debe tener 7 u 8 dígitos sin ceros a la izquierda");
        }
        
        // Validar CUIT
        if (!CUIT_PATTERN.matcher(person.getCuit()).matches()) {
            throw new InvalidDataException("El CUIT debe tener 10 u 11 dígitos sin ceros a la izquierda");
        }
        
        // Validar email
        if (!EMAIL_PATTERN.matcher(person.getEmail()).matches()) {
            throw new InvalidDataException("El email no tiene un formato válido");
        }
        
        // Validar teléfono
        if (!PHONE_PATTERN.matcher(person.getPhone()).matches()) {
            throw new InvalidDataException("El teléfono debe tener entre 10 y 15 dígitos");
        }
    }

    /**
     * Valida que el DNI corresponda con el CUIT
     * Formato CUIT: XX-DDDDDDDD-V donde DDDDDDDD debe ser igual al DNI
     */
    private void validateCuitDniMatch(PersonDTO person) {
        String dni = person.getDni();
        String cuit = person.getCuit();
        
        // El CUIT debe tener al menos 10 dígitos (2 prefijo + 7-8 DNI + 1 verificador)
        if (cuit.length() < 10) {
            return; // Ya fue validado en validateFormats
        }
        
        // Extraer el DNI del CUIT (quitar primeros 2 dígitos y el último)
        String dniFromCuit = cuit.substring(2, cuit.length() - 1);
        
        // Comparar
        if (!dni.equals(dniFromCuit)) {
            throw new InvalidDataException(
                "El CUIT no corresponde al DNI. El CUIT debe contener el DNI en el medio (formato: XX-DNI-X)"
            );
        }
    }    

    /**
     * Valida la fecha de nacimiento
     */
    private void validateBirthDate(PersonDTO person) {
        LocalDate today = LocalDate.now();
        
        // Validar que la fecha de nacimiento no sea futura
        if (person.getBirthDate().isAfter(today)) {
            throw new InvalidDataException("La fecha de nacimiento no puede ser futura");
        }
        
        // Validar edad mínima (18 años)
        LocalDate minBirthDate = today.minusYears(18);
        if (person.getBirthDate().isAfter(minBirthDate)) {
            throw new InvalidDataException("La persona debe tener al menos 18 años");
        }
    }

    /**
     * Método genérico para validar campos únicos de Person (DNI, CUIT, Email)
     * @param person DTO que extiende PersonDTO
     * @param excludeId ID a excluir (para updates)
     * @param findByDni Función para buscar por DNI
     * @param findByCuit Función para buscar por CUIT  
     * @param findByEmail Función para buscar por Email
     * @param resourceType Tipo de recurso para mensaje de error (ej: "miembro", "conductor")
     */
    public <T> void validatePersonUniqueFields(
            PersonDTO person, 
            Long excludeId,
            Function<String, Optional<T>> findByDni,
            Function<String, Optional<T>> findByCuit,
            Function<String, Optional<T>> findByEmail,
            String resourceType) {
        
        // Validar DNI único
        Optional<T> existingByDni = findByDni.apply(person.getDni());
        if (existingByDni.isPresent() && !getIdFromEntity(existingByDni.get()).equals(excludeId)) {
            throw new DuplicateFieldException("DNI", person.getDni(), resourceType);
        }

        // Validar CUIT único  
        Optional<T> existingByCuit = findByCuit.apply(person.getCuit());
        if (existingByCuit.isPresent() && !getIdFromEntity(existingByCuit.get()).equals(excludeId)) {
            throw new DuplicateFieldException("CUIT", person.getCuit(), resourceType);
        }

        // Validar Email único
        Optional<T> existingByEmail = findByEmail.apply(person.getEmail());
        if (existingByEmail.isPresent() && !getIdFromEntity(existingByEmail.get()).equals(excludeId)) {
            throw new DuplicateFieldException("Email", person.getEmail(), resourceType);
        }
    }

    /**
     * Método helper para extraer ID de cualquier entidad Person
     */
    private <T> Long getIdFromEntity(T entity) {
        try {
            // Asumimos que todas las entidades Person tienen getId()
            return (Long) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo ID de entidad", e);
        }
    }
}
