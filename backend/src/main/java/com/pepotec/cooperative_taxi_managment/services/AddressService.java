package com.pepotec.cooperative_taxi_managment.services;

import org.springframework.stereotype.Service;
import com.pepotec.cooperative_taxi_managment.models.dto.AddressDTO;
import com.pepotec.cooperative_taxi_managment.models.entities.AddressEntity;
import com.pepotec.cooperative_taxi_managment.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public AddressEntity convertToEntity(AddressDTO address) {
        return AddressEntity.builder()
            .street(address.getStreet())
            .numeral(address.getNumeral())
            .city(address.getCity())
            .floor(address.getFloor())
            .apartment(address.getApartment())
            .build();
    }

    public AddressDTO convertToDTO(AddressEntity address) {
        return AddressDTO.builder()
            .street(address.getStreet())
            .numeral(address.getNumeral())
            .city(address.getCity())
            .floor(address.getFloor())
            .apartment(address.getApartment())
            .build();
    }
}
