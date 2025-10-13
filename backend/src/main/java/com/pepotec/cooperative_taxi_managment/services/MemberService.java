package com.pepotec.cooperative_taxi_managment.services;

import org.springframework.stereotype.Service;
import com.pepotec.cooperative_taxi_managment.models.dto.MemberDTO;
import com.pepotec.cooperative_taxi_managment.models.entities.MemberEntity;
import com.pepotec.cooperative_taxi_managment.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AddressService addressService;
    
    public MemberDTO createMember(MemberDTO member) {
        MemberEntity memberSaved = convertToEntity(member);
        if(memberSaved.getJoinDate() == null) {
            memberSaved.setJoinDate(LocalDate.now());
        }
        return convertToDTO(memberRepository.save(memberSaved));
    }

    public MemberDTO getMemberById(Long id) {
        return convertToDTO(memberRepository.findById(id).orElse(null));
    }

    public MemberDTO getMemberByDni(String dni) {
        return convertToDTO(memberRepository.findByDniAndLeaveDateIsNull(dni).orElse(null));
    }

    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<MemberDTO> getAllMembersActive() {
        return memberRepository.findAll().stream().filter(member -> member.getLeaveDate() == null).map(this::convertToDTO).collect(Collectors.toList());
    }

    public MemberDTO updateMember(MemberDTO member) {
        MemberEntity memberSaved = memberRepository.findById(member.getId()).orElse(null);
        if(memberSaved == null) {
            throw new RuntimeException("Member not found");
        }
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
            throw new RuntimeException("Id cannot be null");
        }

        MemberEntity memberSaved = memberRepository.findById(id).orElse(null);
        if(memberSaved == null) {
            throw new RuntimeException("Member not found");
        }
        memberSaved.setLeaveDate(LocalDate.now());
        memberRepository.save(memberSaved);
    }
    
    public void deleteMember(MemberDTO member) {
        if(member.getLeaveDate() != null) {
            throw new RuntimeException("Member is not active");
        }
        MemberEntity memberSaved = memberRepository.findById(member.getId()).orElse(null);
        if(memberSaved == null) {
            throw new RuntimeException("Member not found");
        }
        memberSaved.setLeaveDate(LocalDate.now());
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
