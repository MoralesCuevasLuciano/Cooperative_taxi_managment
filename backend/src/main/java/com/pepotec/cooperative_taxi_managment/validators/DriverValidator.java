package com.pepotec.cooperative_taxi_managment.validators;

import com.pepotec.cooperative_taxi_managment.exceptions.InvalidDataException;
import com.pepotec.cooperative_taxi_managment.models.dto.DriverDTO;
import com.pepotec.cooperative_taxi_managment.repositories.DriverRepository;
import com.pepotec.cooperative_taxi_managment.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Validador específico para campos de Driver
 */
@Component
public class DriverValidator {

    @Autowired
    private DriverRepository driverRepository;
    
    @Autowired
    private PersonValidator personValidator;

    /**
     * Valida SOLO los campos específicos de Driver
     */
    public void validateDriverSpecificFields(DriverDTO driver) {
        // Validar fecha de vencimiento del registro
        if (driver.getExpirationRegistrationDate() == null) {
            throw new InvalidDataException("La fecha de vencimiento del registro no puede estar vacía");
        }
    }

        /**
     * Valida que DNI, CUIT y Email no estén duplicados en la base de datos
     */
    public void validateUniqueFields(DriverDTO driver, Long excludeId) {
        personValidator.validatePersonUniqueFields(
            driver,
            excludeId,
            driverRepository::findByDniAndLeaveDateIsNull,
            driverRepository::findByCuitAndLeaveDateIsNull,
            driverRepository::findByEmailAndLeaveDateIsNull,
            "conductor"
        );
    }
}
