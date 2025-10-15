package com.pepotec.cooperative_taxi_managment.validators;

import com.pepotec.cooperative_taxi_managment.exceptions.InvalidDataException;
import com.pepotec.cooperative_taxi_managment.models.dto.SubscriberDTO;
import com.pepotec.cooperative_taxi_managment.validators.PersonValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import com.pepotec.cooperative_taxi_managment.repositories.SubscriberRepository;

/**
 * Validador específico para campos de Subscriber
 */
@Component
public class SubscriberValidator {

    @Autowired
    private SubscriberRepository subscriberRepository;
    
    @Autowired
    private PersonValidator personValidator;

    /**
     * Valida solo los campos específicos de Subscriber
     */
    public void validateSubscriberSpecificFields(SubscriberDTO subscriber) {
        // Validar números de licencia
        if (subscriber.getLicenceNumbers() == null) {
            throw new InvalidDataException("Los números de licencia no pueden ser nulos");
        }

        validateLicenceNumbersFormat(subscriber.getLicenceNumbers());
        validateLicenceNumbersNoDuplicates(subscriber.getLicenceNumbers());
    }

    /**
     * Valida que cada número de licencia tenga exactamente 4 dígitos
     */
    private void validateLicenceNumbersFormat(java.util.List<String> licenceNumbers) {
        for (String licence : licenceNumbers) {
            if (licence == null || licence.trim().isEmpty()) {
                throw new InvalidDataException("Los números de licencia no pueden estar vacíos");
            }
            if (!licence.matches("^\\d{4}$")) {
                throw new InvalidDataException("Cada número de licencia debe tener exactamente 4 dígitos: " + licence);
            }
        }
    }

    /**
     * Valida que no haya números de licencia duplicados
     */
    private void validateLicenceNumbersNoDuplicates(java.util.List<String> licenceNumbers) {
        Set<String> seenLicences = new HashSet<>();
        for (String licence : licenceNumbers) {
            if (!seenLicences.add(licence)) {
                throw new InvalidDataException("No se permiten números de licencia duplicados: " + licence);
            }
        }
    }

        /**
     * Valida que DNI, CUIT y Email no estén duplicados en la base de datos
     */
    public void validateUniqueFields(SubscriberDTO subscriber, Long excludeId) {
        personValidator.validatePersonUniqueFields(
            subscriber,
            excludeId,
            subscriberRepository::findByDni,
            subscriberRepository::findByCuit,
            subscriberRepository::findByEmail,
            "suscriptor"
        );
    }
}
