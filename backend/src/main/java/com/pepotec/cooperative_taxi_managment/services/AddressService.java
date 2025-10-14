package com.pepotec.cooperative_taxi_managment.services;

import org.springframework.stereotype.Service;
import com.pepotec.cooperative_taxi_managment.models.dto.AddressDTO;
import com.pepotec.cooperative_taxi_managment.models.entities.AddressEntity;

@Service
public class AddressService {

    public AddressEntity convertToEntity(AddressDTO address) {
        if(address == null) {
            return null;
        }
        return AddressEntity.builder()
            .street(address.getStreet())
            .numeral(address.getNumeral())
            .city(address.getCity())
            .floor(address.getFloor())
            .apartment(address.getApartment())
            .build();
    }

    public AddressDTO convertToDTO(AddressEntity address) {
        if(address == null) {
            return null;
        }
        return AddressDTO.builder()
            .id(address.getId())
            .street(address.getStreet())
            .numeral(address.getNumeral())
            .city(address.getCity())
            .floor(address.getFloor())
            .apartment(address.getApartment())
            .build();
    }
}
