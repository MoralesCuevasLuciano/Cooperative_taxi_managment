package com.pepotec.cooperative_taxi_managment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pepotec.cooperative_taxi_managment.models.entities.SubscriberEntity;
import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberEntity, Long> {
    Optional<SubscriberEntity> findByDni(String dni);
    Optional<SubscriberEntity> findByCuit(String cuit);
    Optional<SubscriberEntity> findByEmail(String email);
}
