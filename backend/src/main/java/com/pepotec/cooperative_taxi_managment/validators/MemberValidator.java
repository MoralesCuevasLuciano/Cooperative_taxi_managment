package com.pepotec.cooperative_taxi_managment.validators;

import com.pepotec.cooperative_taxi_managment.exceptions.InvalidDataException;
import com.pepotec.cooperative_taxi_managment.models.dto.MemberDTO;
import com.pepotec.cooperative_taxi_managment.validators.PersonValidator;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import com.pepotec.cooperative_taxi_managment.repositories.MemberRepository;

@Component
public class MemberValidator {

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private PersonValidator personValidator;


    /**
     * Valida solo los campos específicos de Member
     */
    public void validateMemberSpecificFields(MemberDTO member) {
        // Validar campos obligatorios específicos de Member
        if (member.getRole() == null) {
            throw new InvalidDataException("El rol no puede estar vacío");
        }
        
        if (member.getAddress() == null) {
            throw new InvalidDataException("La dirección no puede estar vacía");
        }
        
        // Validar fechas específicas de Member
        validateMemberDates(member);

    }


    /**
     * Valida las fechas específicas de Member (ingreso, salida)
     */
    private void validateMemberDates(MemberDTO member) {
        LocalDate today = LocalDate.now();
        
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
        personValidator.validatePersonUniqueFields(
            member,
            excludeId,
            memberRepository::findByDniAndLeaveDateIsNull,
            memberRepository::findByCuitAndLeaveDateIsNull,
            memberRepository::findByEmailAndLeaveDateIsNull,
            "miembro"
        );
    }
}