package com.pepotec.cooperative_taxi_managment.services;

import org.springframework.stereotype.Service;
import com.pepotec.cooperative_taxi_managment.models.dto.MemberDTO;
import com.pepotec.cooperative_taxi_managment.models.entities.MemberEntity;
import com.pepotec.cooperative_taxi_managment.repositories.MemberRepository;
import com.pepotec.cooperative_taxi_managment.validators.MemberValidator;
import com.pepotec.cooperative_taxi_managment.exceptions.ResourceNotFoundException;
import com.pepotec.cooperative_taxi_managment.exceptions.MemberAlreadyInactiveException;
import com.pepotec.cooperative_taxi_managment.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private MemberValidator memberValidator;
    
    public MemberDTO createMember(MemberDTO member) {
        // Validar datos del miembro
        memberValidator.validateMemberData(member);
        
        // Validar campos únicos (DNI, CUIT, Email)
        memberValidator.validateUniqueFields(member, null);
        
        MemberEntity memberSaved = convertToEntity(member);
        if(memberSaved.getJoinDate() == null) {
            memberSaved.setJoinDate(LocalDate.now());
        }
        return convertToDTO(memberRepository.save(memberSaved));
    }

    public MemberDTO getMemberById(Long id) {
        MemberEntity member = memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id, "Miembro"));
        return convertToDTO(member);
    }

    public MemberDTO getMemberByDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new InvalidDataException("El DNI no puede estar vacío");
        }
        
        MemberEntity member = memberRepository.findByDniAndLeaveDateIsNull(dni)
            .orElseThrow(() -> new ResourceNotFoundException(null, "Miembro con DNI " + dni));
        return convertToDTO(member);
    }

    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<MemberDTO> getAllMembersActive() {
        return memberRepository.findAll().stream().filter(member -> member.getLeaveDate() == null).map(this::convertToDTO).collect(Collectors.toList());
    }

    public MemberDTO updateMember(MemberDTO member) {
        // Validar que el ID no sea nulo
        if (member.getId() == null) {
            throw new InvalidDataException("El ID no puede ser nulo para actualizar");
        }
        
        // Buscar el miembro existente
        MemberEntity memberSaved = memberRepository.findById(member.getId())
            .orElseThrow(() -> new ResourceNotFoundException(member.getId(), "Miembro"));
        
        // Validar los datos del miembro
        memberValidator.validateMemberData(member);
        
        // Validar campos únicos (excluyendo el ID actual)
        memberValidator.validateUniqueFields(member, member.getId());

        
        // Actualizar campos
        memberSaved.setFirstName(member.getFirstName());
        memberSaved.setSecondName(member.getSecondName());
        memberSaved.setFatherSurname(member.getFatherSurname());
        memberSaved.setMotherSurname(member.getMotherSurname());
        memberSaved.setEmail(member.getEmail());
        memberSaved.setPhone(member.getPhone());
        memberSaved.setAddress(addressService.convertToEntity(member.getAddress()));
        memberSaved.setDni(member.getDni());
        memberSaved.setCuit(member.getCuit());
        memberSaved.setBirthDate(member.getBirthDate());
        memberSaved.setJoinDate(member.getJoinDate());
        memberSaved.setLeaveDate(member.getLeaveDate());
        memberSaved.setRole(member.getRole());
        
        return convertToDTO(memberRepository.save(memberSaved));
    }

    public void deleteMember(Long id) {
        if(id == null) {
            throw new InvalidDataException("El ID no puede ser nulo");
        }

        MemberEntity memberSaved = memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id, "Miembro"));
        
        // Verificar que no esté ya inactivo
        if (memberSaved.getLeaveDate() != null) {
            throw new MemberAlreadyInactiveException(id);
        }
        
        memberSaved.setLeaveDate(LocalDate.now());
        memberRepository.save(memberSaved);
    }

    public void deleteMember(Long id, LocalDate leaveDate) {
        if(id == null) {
            throw new InvalidDataException("El ID no puede ser nulo");
        }

        MemberEntity memberSaved = memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id, "Miembro"));
        
        // Verificar que no esté ya inactivo
        if (memberSaved.getLeaveDate() != null) {
            throw new MemberAlreadyInactiveException(id);
        }
        
        memberSaved.setLeaveDate(leaveDate);
        memberRepository.save(memberSaved);
    }
    
    public void deleteMember(MemberDTO member) {
        if(member.getLeaveDate() != null) {
            throw new MemberAlreadyInactiveException(member.getId());
        }
        
        MemberEntity memberSaved = memberRepository.findById(member.getId())
            .orElseThrow(() -> new ResourceNotFoundException(member.getId(), "Miembro"));
        
        memberSaved.setLeaveDate(LocalDate.now());
        memberRepository.save(memberSaved);
    }    

    public void deleteMember(MemberDTO member, LocalDate leaveDate) {
        if(member.getLeaveDate() != null) {
            throw new MemberAlreadyInactiveException(member.getId());
        }
        
        MemberEntity memberSaved = memberRepository.findById(member.getId())
            .orElseThrow(() -> new ResourceNotFoundException(member.getId(), "Miembro"));
        
        memberSaved.setLeaveDate(leaveDate);
        memberRepository.save(memberSaved);
    }

    private MemberEntity convertToEntity(MemberDTO member) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setFirstName(member.getFirstName());
        memberEntity.setSecondName(member.getSecondName());
        memberEntity.setFatherSurname(member.getFatherSurname());
        memberEntity.setMotherSurname(member.getMotherSurname());
        memberEntity.setEmail(member.getEmail());
        memberEntity.setPhone(member.getPhone());
        memberEntity.setAddress(addressService.convertToEntity(member.getAddress()));
        memberEntity.setDni(member.getDni());
        memberEntity.setCuit(member.getCuit());
        memberEntity.setBirthDate(member.getBirthDate());
        memberEntity.setJoinDate(member.getJoinDate());
        memberEntity.setLeaveDate(member.getLeaveDate());
        memberEntity.setRole(member.getRole());
        return memberEntity;
    }

    private MemberDTO convertToDTO(MemberEntity member) {
        if (member == null) {
            return null;
        }
        
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setFirstName(member.getFirstName());
        memberDTO.setSecondName(member.getSecondName());
        memberDTO.setFatherSurname(member.getFatherSurname());
        memberDTO.setMotherSurname(member.getMotherSurname());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPhone(member.getPhone());
        memberDTO.setAddress(addressService.convertToDTO(member.getAddress()));
        memberDTO.setDni(member.getDni());
        memberDTO.setCuit(member.getCuit());
        memberDTO.setBirthDate(member.getBirthDate());
        memberDTO.setJoinDate(member.getJoinDate());
        memberDTO.setLeaveDate(member.getLeaveDate());
        memberDTO.setRole(member.getRole());
        memberDTO.setActive(member.getLeaveDate() == null);
        return memberDTO;
    }
}
