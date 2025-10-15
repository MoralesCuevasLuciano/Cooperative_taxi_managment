package com.pepotec.cooperative_taxi_managment.repositories;

import com.pepotec.cooperative_taxi_managment.models.entities.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    Optional<DriverEntity> findByDniAndLeaveDateIsNull(String dni);
    Optional<DriverEntity> findByCuitAndLeaveDateIsNull(String cuit);
    Optional<DriverEntity> findByEmailAndLeaveDateIsNull(String email);
}
