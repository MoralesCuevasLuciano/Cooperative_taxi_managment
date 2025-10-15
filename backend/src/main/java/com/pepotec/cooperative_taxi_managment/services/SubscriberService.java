package com.pepotec.cooperative_taxi_managment.services;

import org.springframework.stereotype.Service;
import com.pepotec.cooperative_taxi_managment.models.dto.SubscriberDTO;
import com.pepotec.cooperative_taxi_managment.models.entities.SubscriberEntity;
import com.pepotec.cooperative_taxi_managment.repositories.SubscriberRepository;
import com.pepotec.cooperative_taxi_managment.validators.SubscriberValidator;
import com.pepotec.cooperative_taxi_managment.validators.PersonValidator;
import com.pepotec.cooperative_taxi_managment.exceptions.ResourceNotFoundException;
import com.pepotec.cooperative_taxi_managment.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriberService {
    
    @Autowired
    private SubscriberRepository subscriberRepository;
    
    @Autowired
    private PersonValidator personValidator;
    
    @Autowired
    private SubscriberValidator subscriberValidator;

    public SubscriberDTO createSubscriber(SubscriberDTO subscriber) {
        // Validar datos básicos de Person
        personValidator.validatePersonData(subscriber);
        
        // Validar campos específicos de Subscriber
        subscriberValidator.validateSubscriberSpecificFields(subscriber);
        
        // Validar campos únicos (DNI, CUIT, Email)
        subscriberValidator.validateUniqueFields(subscriber, null);
        
        SubscriberEntity subscriberSaved = convertToEntity(subscriber);
        return convertToDTO(subscriberRepository.save(subscriberSaved));
    }

    public SubscriberDTO getSubscriberById(Long id) {
        SubscriberEntity subscriber = subscriberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id, "Suscriptor"));
        return convertToDTO(subscriber);
    }

    public SubscriberDTO getSubscriberByDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new InvalidDataException("El DNI no puede estar vacío");
        }
        
        SubscriberEntity subscriber = subscriberRepository.findByDni(dni)
            .orElseThrow(() -> new ResourceNotFoundException(null, "Suscriptor con DNI " + dni));
        return convertToDTO(subscriber);
    }

    public List<SubscriberDTO> getAllSubscribers() {
        return subscriberRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public SubscriberDTO updateSubscriber(SubscriberDTO subscriber) {
        // Validar que el ID no sea nulo
        if (subscriber.getId() == null) {
            throw new InvalidDataException("El ID no puede ser nulo para actualizar");
        }
        
        // Buscar el suscriptor existente
        SubscriberEntity subscriberSaved = subscriberRepository.findById(subscriber.getId())
            .orElseThrow(() -> new ResourceNotFoundException(subscriber.getId(), "Suscriptor"));
        
        // Validar datos básicos de Person
        personValidator.validatePersonData(subscriber);
        
        // Validar campos específicos de Subscriber
        subscriberValidator.validateSubscriberSpecificFields(subscriber);
        
        // Validar campos únicos (excluyendo el ID actual)
        subscriberValidator.validateUniqueFields(subscriber, subscriber.getId());

        // Actualizar campos
        updateEntityFromDTO(subscriberSaved, subscriber);
        
        return convertToDTO(subscriberRepository.save(subscriberSaved));
    }

    public void deleteSubscriber(Long id) {
        if(id == null) {
            throw new InvalidDataException("El ID no puede ser nulo");
        }

        SubscriberEntity subscriberSaved = subscriberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id, "Suscriptor"));
        
        subscriberRepository.delete(subscriberSaved);
    }



    private SubscriberEntity convertToEntity(SubscriberDTO subscriber) {
        SubscriberEntity subscriberEntity = new SubscriberEntity();
        updateEntityFromDTO(subscriberEntity, subscriber);
        return subscriberEntity;
    }

    private void updateEntityFromDTO(SubscriberEntity entity, SubscriberDTO dto) {
        entity.setFirstName(dto.getFirstName());
        entity.setSecondName(dto.getSecondName());
        entity.setFatherSurname(dto.getFatherSurname());
        entity.setMotherSurname(dto.getMotherSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setDni(dto.getDni());
        entity.setCuit(dto.getCuit());
        entity.setBirthDate(dto.getBirthDate());
        entity.setLicenceNumbers(dto.getLicenceNumbers());
    }

    private SubscriberDTO convertToDTO(SubscriberEntity subscriber) {
        if (subscriber == null) {
            return null;
        }
        
        return SubscriberDTO.builder()
            .id(subscriber.getId())
            .firstName(subscriber.getFirstName())
            .secondName(subscriber.getSecondName())
            .fatherSurname(subscriber.getFatherSurname())
            .motherSurname(subscriber.getMotherSurname())
            .email(subscriber.getEmail())
            .phone(subscriber.getPhone())
            .dni(subscriber.getDni())
            .cuit(subscriber.getCuit())
            .birthDate(subscriber.getBirthDate())
            .licenceNumbers(subscriber.getLicenceNumbers())
            .build();
    }
}
