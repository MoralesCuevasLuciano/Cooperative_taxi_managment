package com.pepotec.cooperative_taxi_managment.validators;

import com.pepotec.cooperative_taxi_managment.exceptions.InvalidDataException;
import com.pepotec.cooperative_taxi_managment.models.dto.MemberDTO;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import com.pepotec.cooperative_taxi_managment.repositories.MemberRepository;
import com.pepotec.cooperative_taxi_managment.models.entities.MemberEntity;
import com.pepotec.cooperative_taxi_managment.exceptions.DuplicateFieldException;
import java.util.Optional;

@Component
public class MemberValidator {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AddressValidator addressValidator;

    // Regex patterns para validaciones
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} ]+$");
    private static final Pattern DNI_PATTERN = Pattern.compile("^[1-9]\\d{6,7}$");
    private static final Pattern CUIT_PATTERN = Pattern.compile("^[1-9]\\d{9,10}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /**
     * Método principal de validación - Punto de entrada
     * Valida todos los datos del miembro antes de crear/actualizar
     */
    public void validateMemberData(MemberDTO member) {
        validateRequiredFields(member);
        validateFormats(member);
        validateCuitDniMatch(member);
        validateDates(member);
        addressValidator.validateAddress(member.getAddress());
    }

    /**
     * Valida que los campos obligatorios no estén vacíos
     */
    private void validateRequiredFields(MemberDTO member) {
        if (member.getFirstName() == null || member.getFirstName().trim().isEmpty()) {
            throw new InvalidDataException("El nombre no puede estar vacío");
        }
        
        if (member.getFatherSurname() == null || member.getFatherSurname().trim().isEmpty()) {
            throw new InvalidDataException("El apellido paterno no puede estar vacío");
        }
        
        if (member.getDni() == null || member.getDni().trim().isEmpty()) {
            throw new InvalidDataException("El DNI no puede estar vacío");
        }
        
        if (member.getCuit() == null || member.getCuit().trim().isEmpty()) {
            throw new InvalidDataException("El CUIT no puede estar vacío");
        }
        
        if (member.getEmail() == null || member.getEmail().trim().isEmpty()) {
            throw new InvalidDataException("El email no puede estar vacío");
        }
        
        if (member.getPhone() == null || member.getPhone().trim().isEmpty()) {
            throw new InvalidDataException("El teléfono no puede estar vacío");
        }
        
        if (member.getBirthDate() == null) {
            throw new InvalidDataException("La fecha de nacimiento no puede estar vacía");
        }
        
        if (member.getRole() == null) {
            throw new InvalidDataException("El rol no puede estar vacío");
        }
        
        if (member.getAddress() == null) {
            throw new InvalidDataException("La dirección no puede estar vacía");
        }
    }

    /**
     * Valida el formato de los campos (DNI, CUIT, email, teléfono, nombres)
     */
    private void validateFormats(MemberDTO member) {
        // Validar formato del nombre
        if (!NAME_PATTERN.matcher(member.getFirstName()).matches()) {
            throw new InvalidDataException("El nombre solo puede contener letras y espacios");
        }
        
        if (member.getFirstName().length() < 2 || member.getFirstName().length() > 50) {
            throw new InvalidDataException("El nombre debe tener entre 2 y 50 caracteres");
        }
        
        // Validar segundo nombre si existe
        if (member.getSecondName() != null && !member.getSecondName().trim().isEmpty()) {
            if (!NAME_PATTERN.matcher(member.getSecondName()).matches()) {
                throw new InvalidDataException("El segundo nombre solo puede contener letras y espacios");
            }
            if (member.getSecondName().length() < 2 || member.getSecondName().length() > 50) {
                throw new InvalidDataException("El segundo nombre debe tener entre 2 y 50 caracteres");
            }
        }
        
        // Validar apellido paterno
        if (!NAME_PATTERN.matcher(member.getFatherSurname()).matches()) {
            throw new InvalidDataException("El apellido paterno solo puede contener letras y espacios");
        }
        
        if (member.getFatherSurname().length() < 2 || member.getFatherSurname().length() > 50) {
            throw new InvalidDataException("El apellido paterno debe tener entre 2 y 50 caracteres");
        }
        
        // Validar apellido materno si existe
        if (member.getMotherSurname() != null && !member.getMotherSurname().trim().isEmpty()) {
            if (!NAME_PATTERN.matcher(member.getMotherSurname()).matches()) {
                throw new InvalidDataException("El apellido materno solo puede contener letras y espacios");
            }
            if (member.getMotherSurname().length() < 2 || member.getMotherSurname().length() > 50) {
                throw new InvalidDataException("El apellido materno debe tener entre 2 y 50 caracteres");
            }
        }
        
        // Validar DNI
        if (!DNI_PATTERN.matcher(member.getDni()).matches()) {
            throw new InvalidDataException("El DNI debe tener 7 u 8 dígitos sin ceros a la izquierda");
        }
        
        // Validar CUIT
        if (!CUIT_PATTERN.matcher(member.getCuit()).matches()) {
            throw new InvalidDataException("El CUIT debe tener 10 u 11 dígitos sin ceros a la izquierda");
        }
        
        // Validar email
        if (!EMAIL_PATTERN.matcher(member.getEmail()).matches()) {
            throw new InvalidDataException("El email no tiene un formato válido");
        }
        
        // Validar teléfono
        if (!PHONE_PATTERN.matcher(member.getPhone()).matches()) {
            throw new InvalidDataException("El teléfono debe tener entre 10 y 15 dígitos");
        }
    }

    /**
     * Valida que el DNI corresponda con el CUIT
     * Formato CUIT: XX-DDDDDDDD-V donde DDDDDDDD debe ser igual al DNI
     */
    private void validateCuitDniMatch(MemberDTO member) {
        String dni = member.getDni();
        String cuit = member.getCuit();
        
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
     * Valida las fechas (nacimiento, ingreso, salida)
     */
    private void validateDates(MemberDTO member) {
        LocalDate today = LocalDate.now();
        
        // Validar que la fecha de nacimiento no sea futura
        if (member.getBirthDate().isAfter(today)) {
            throw new InvalidDataException("La fecha de nacimiento no puede ser futura");
        }
        
        // Validar edad mínima (18 años)
        LocalDate minBirthDate = today.minusYears(18);
        if (member.getBirthDate().isAfter(minBirthDate)) {
            throw new InvalidDataException("El miembro debe tener al menos 18 años");
        }
        
        // Validar joinDate si está presente
        if (member.getJoinDate() != null && member.getJoinDate().isAfter(today)) {
            throw new InvalidDataException("La fecha de ingreso no puede ser futura");
        }
        
        // Validar leaveDate si está presente
        if (member.getLeaveDate() != null) {
            if (member.getLeaveDate().isAfter(today)) {
                throw new InvalidDataException("La fecha de salida no puede ser futura");
            }
            
            // La fecha de salida debe ser posterior a la de ingreso
            if (member.getJoinDate() != null && member.getLeaveDate().isBefore(member.getJoinDate())) {
                throw new InvalidDataException("La fecha de salida no puede ser anterior a la fecha de ingreso");
            }
        }
    }

    /**
     * Valida que DNI, CUIT y Email no estén duplicados en la base de datos
     * @param member - El miembro a validar
     * @param excludeId - ID a excluir de la búsqueda (null para crear, ID del miembro para actualizar)
     */
    public void validateUniqueFields(MemberDTO member, Long excludeId) {
        // Validar DNI único
        Optional<MemberEntity> existingByDni = memberRepository.findByDniAndLeaveDateIsNull(member.getDni());
        if (existingByDni.isPresent() && !existingByDni.get().getId().equals(excludeId)) {
            throw new DuplicateFieldException("DNI", member.getDni(), "miembro");
        }

        // Validar CUIT único
        Optional<MemberEntity> existingByCuit = memberRepository.findByCuitAndLeaveDateIsNull(member.getCuit());
        if (existingByCuit.isPresent() && !existingByCuit.get().getId().equals(excludeId)) {
            throw new DuplicateFieldException("CUIT", member.getCuit(), "miembro");
        }

        // Validar Email único
        Optional<MemberEntity> existingByEmail = memberRepository.findByEmailAndLeaveDateIsNull(member.getEmail());
        if (existingByEmail.isPresent() && !existingByEmail.get().getId().equals(excludeId)) {
            throw new DuplicateFieldException("Email", member.getEmail(), "miembro");
        }
    }
}