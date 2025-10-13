package com.pepotec.cooperative_taxi_managment.repositories;

import com.pepotec.cooperative_taxi_managment.models.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interfaz que define los métodos para interactuar con la base de datos
 * relacionados a la entidad AddressEntity.
 */
@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Optional<AddressEntity> findByStreetAndNumeralAndCityAndFloorAndApartment(String street, String numeral, String city, String floor, String apartment);
    Optional<AddressEntity> findByStreetAndNumeralAndCity(String street, String numeral, String city);
    Optional<AddressEntity> findByStreetAndNumeralAndCityAndFloor(String street, String numeral, String city, String floor);
}
