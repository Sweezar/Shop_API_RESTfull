package org.berneick.mapper;

import org.berneick.model.Address;
import org.berneick.model.Client;
import org.berneick.model.ClientDTO;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public ClientDTO toDTO(Client client) {
        return ClientDTO.builder()
                .clientId(client.getId())
                .clientName(client.getClientName())
                .clientSurname(client.getClientSurname())
                .birthday(client.getBirthday())
                .gender(client.getGender())
                .registrationDate(client.getRegistrationDate())
                .addressId(client.getAddress().getId())
                .build();
    }

    public Client toEntity(ClientDTO clientDTO) {
        Client client = new Client();
        client.setClientName(clientDTO.getClientName());
        client.setClientSurname(clientDTO.getClientSurname());
        client.setBirthday(clientDTO.getBirthday());
        client.setGender(clientDTO.getGender());
        client.setRegistrationDate(clientDTO.getRegistrationDate());

        Address address = new Address();
        address.setId(clientDTO.getAddressId());
        client.setAddress(address);

        return client;
    }
}
