package com.pepotec.cooperative_taxi_managment.repositories;

import com.pepotec.cooperative_taxi_managment.models.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByDniAndLeaveDateIsNull(String dni);
    Optional<MemberEntity> findByCuitAndLeaveDateIsNull(String cuit);
    Optional<MemberEntity> findByEmailAndLeaveDateIsNull(String email);
}
