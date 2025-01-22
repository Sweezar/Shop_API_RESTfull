package org.berneick.service;

import jakarta.annotation.Nonnull;
import org.berneick.exception.ClientNotFoundException;
import org.berneick.mapper.AddressMapper;
import org.berneick.mapper.ClientMapper;
import org.berneick.model.Address;
import org.berneick.model.AddressDTO;
import org.berneick.model.Client;
import org.berneick.model.ClientDTO;
import org.berneick.repository.AddressRepository;
import org.berneick.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Nonnull
    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client savedClient = clientRepository.save(client);

        return clientMapper.toDTO(savedClient);
    }

    @Nonnull
    @Transactional(readOnly = true)
    public List<ClientDTO> findByClientNameAndClientSurname(@Nonnull String clientName, @Nonnull String clientSurname) {
        List<Client> clients = clientRepository.findByClientNameAndClientSurname(clientName, clientSurname);
        return clients.stream()
                .map(clientMapper::toDTO)
                .toList();
    }

    @Nonnull
    @Transactional(readOnly = true)
    public List<ClientDTO> findAll(Integer limit, Integer offset) {
        if(limit != null && offset != null) {
            Page<Client> page = clientRepository.findAll(PageRequest.of(offset / limit, limit));
            return page.getContent().stream()
                    .map(clientMapper::toDTO)
                    .toList();
        } else {
            return clientRepository.findAll().stream()
                    .map(clientMapper::toDTO)
                    .toList();
        }
    }

    @Nonnull
    @Transactional
    public ClientDTO updateAddress(@Nonnull UUID clientId, @Nonnull AddressDTO addressDTO) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + clientId));

        Address address = addressMapper.toEntity(addressDTO);
        address.setId(client.getAddress().getId());
        client.setAddress(address);

        Client savedClient = clientRepository.save(client);
        return clientMapper.toDTO(savedClient);
    }

    @Transactional
    public void delete(@Nonnull UUID clientId) throws ClientNotFoundException {
        if(!clientRepository.existsById(clientId)) {
            throw new ClientNotFoundException("Client not found with id: " + clientId);
        }
        clientRepository.deleteById(clientId);
    }

}
