package org.berneick.controller;

import jakarta.validation.Valid;
import org.berneick.exception.ClientNotFoundException;
import org.berneick.model.AddressDTO;
import org.berneick.model.ClientDTO;
import org.berneick.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        ClientDTO createdClient = clientService.createClient(clientDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientDTO>> getClientsByNameAndSurname(
            @RequestParam String name,
            @RequestParam String surname
    ) {
        List<ClientDTO> clients = clientService.findByClientNameAndClientSurname(name, surname);

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        List<ClientDTO> clients = clientService.findAll(limit, offset);

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PatchMapping("/{clientId}")
    public ResponseEntity<?> updateAddress(
            @PathVariable UUID clientId,
            @Valid @RequestBody AddressDTO addressDTO
    ) {
            try {
                ClientDTO clientDTO = clientService.updateAddress(clientId, addressDTO);
                return new ResponseEntity<>(clientDTO, HttpStatus.OK);
            } catch (ClientNotFoundException e) {
                return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
            }
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID clientId) {
        try {
            clientService.delete(clientId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
