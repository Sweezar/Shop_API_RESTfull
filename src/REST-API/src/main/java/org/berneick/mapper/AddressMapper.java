package org.berneick.mapper;

import org.berneick.model.Address;
import org.berneick.model.AddressDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .addressId(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .build();
    }

    public Address toEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setCountry(addressDTO.getCountry());
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());

        return address;
    }
}
