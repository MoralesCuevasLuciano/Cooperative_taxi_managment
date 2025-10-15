package com.pepotec.cooperative_taxi_managment.services;

import org.springframework.stereotype.Service;
import com.pepotec.cooperative_taxi_managment.models.dto.DriverDTO;
import com.pepotec.cooperative_taxi_managment.models.entities.DriverEntity;
import com.pepotec.cooperative_taxi_managment.repositories.DriverRepository;
import com.pepotec.cooperative_taxi_managment.validators.DriverValidator;
import com.pepotec.cooperative_taxi_managment.validators.MemberValidator;
import com.pepotec.cooperative_taxi_managment.validators.PersonValidator;
import com.pepotec.cooperative_taxi_managment.validators.AddressValidator;
import com.pepotec.cooperative_taxi_managment.exceptions.ResourceNotFoundException;
import com.pepotec.cooperative_taxi_managment.exceptions.InvalidDataException;
import com.pepotec.cooperative_taxi_managment.exceptions.MemberAlreadyInactiveException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
public class DriverService {
    
    @Autowired
    private DriverRepository driverRepository;
    
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private PersonValidator personValidator;
    
    @Autowired
    private DriverValidator driverValidator;
    
    @Autowired
    private AddressValidator addressValidator;
    
    @Autowired
    private MemberValidator memberValidator;
    
    public DriverDTO createDriver(DriverDTO driver) {
        // Validar datos básicos de Person
        personValidator.validatePersonData(driver);

        // Validar datos básicos de Member
        memberValidator.validateMemberSpecificFields(driver);
        
        // Validar dirección
        addressValidator.validateAddress(driver.getAddress());
        
        // Validar campos específicos de Driver (incluye validaciones de Member)
        driverValidator.validateDriverSpecificFields(driver);
        
        // Validar campos únicos (DNI, CUIT, Email)
        driverValidator.validateUniqueFields(driver, null);
        
        DriverEntity driverSaved = convertToEntity(driver);
        if(driverSaved.getJoinDate() == null) {
            driverSaved.setJoinDate(LocalDate.now());
        }
        return convertToDTO(driverRepository.save(driverSaved));
    }

    public DriverDTO getDriverById(Long id) {
        DriverEntity driver = driverRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id, "Conductor"));
        return convertToDTO(driver);
    }

    public DriverDTO getDriverByDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new InvalidDataException("El DNI no puede estar vacío");
        }
        
        DriverEntity driver = driverRepository.findByDniAndLeaveDateIsNull(dni)
            .orElseThrow(() -> new ResourceNotFoundException(null, "Conductor con DNI " + dni));
        return convertToDTO(driver);
    }

    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<DriverDTO> getAllDriversActive() {
        return driverRepository.findAll().stream()
            .filter(driver -> driver.getLeaveDate() == null)
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public DriverDTO updateDriver(DriverDTO driver) {
        // Validar que el ID no sea nulo
        if (driver.getId() == null) {
            throw new InvalidDataException("El ID no puede ser nulo para actualizar");
        }
        
        // Buscar el conductor existente
        DriverEntity driverSaved = driverRepository.findById(driver.getId())
            .orElseThrow(() -> new ResourceNotFoundException(driver.getId(), "Conductor"));
        
        // Validar datos básicos de Person
        personValidator.validatePersonData(driver);
        
        // Validar dirección
        addressValidator.validateAddress(driver.getAddress());
        
        // Validar campos específicos de Driver (incluye validaciones de Member)
        driverValidator.validateDriverSpecificFields(driver);
        
        // Validar campos únicos (excluyendo el ID actual)
        driverValidator.validateUniqueFields(driver, driver.getId());

        // Actualizar campos
        updateEntityFromDTO(driverSaved, driver);
        
        return convertToDTO(driverRepository.save(driverSaved));
    }

    public void deleteDriver(Long id) {
        if(id == null) {
            throw new InvalidDataException("El ID no puede ser nulo");
        }

        DriverEntity driverSaved = driverRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id, "Conductor"));
        
        // Verificar que no esté ya inactivo
        if (driverSaved.getLeaveDate() != null) {
            throw new MemberAlreadyInactiveException(id);
        }
        
        driverSaved.setLeaveDate(LocalDate.now());
        driverRepository.save(driverSaved);
    }


    private DriverEntity convertToEntity(DriverDTO driver) {
        DriverEntity driverEntity = new DriverEntity();
        updateEntityFromDTO(driverEntity, driver);
        return driverEntity;
    }

    private void updateEntityFromDTO(DriverEntity entity, DriverDTO dto) {
        entity.setFirstName(dto.getFirstName());
        entity.setSecondName(dto.getSecondName());
        entity.setFatherSurname(dto.getFatherSurname());
        entity.setMotherSurname(dto.getMotherSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAddress(addressService.convertToEntity(dto.getAddress()));
        entity.setDni(dto.getDni());
        entity.setCuit(dto.getCuit());
        entity.setBirthDate(dto.getBirthDate());
        entity.setJoinDate(dto.getJoinDate());
        entity.setLeaveDate(dto.getLeaveDate());
        entity.setRole(dto.getRole());
        entity.setExpirationRegistrationDate(dto.getExpirationRegistrationDate());
    }

    private DriverDTO convertToDTO(DriverEntity driver) {
        if (driver == null) {
            return null;
        }
        
        return DriverDTO.builder()
            .id(driver.getId())
            .firstName(driver.getFirstName())
            .secondName(driver.getSecondName())
            .fatherSurname(driver.getFatherSurname())
            .motherSurname(driver.getMotherSurname())
            .email(driver.getEmail())
            .phone(driver.getPhone())
            .dni(driver.getDni())
            .cuit(driver.getCuit())
            .birthDate(driver.getBirthDate())
            .joinDate(driver.getJoinDate())
            .leaveDate(driver.getLeaveDate())
            .role(driver.getRole())
            .address(addressService.convertToDTO(driver.getAddress()))
            .active(driver.getLeaveDate() == null)
            .expirationRegistrationDate(driver.getExpirationRegistrationDate())
            .build();
    }
}
