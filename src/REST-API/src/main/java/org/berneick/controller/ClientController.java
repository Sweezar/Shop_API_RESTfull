package org.berneick.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Client API", description = "API для управления клиентами")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    @Operation(summary = "Создание нового клиента", description = "Создает нового пользователя с предоставленными данными.")
    @ApiResponse(responseCode = "201", description = "Клиент успешно создан")
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        ClientDTO createdClient = clientService.createClient(clientDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    @Operation(summary = "Получение клиента по имени и фамилии", description = "Возвращает информацию о клиенте по его имени и фамилии.")
    @ApiResponse(responseCode = "200", description = "Клиенты найдены")
    public ResponseEntity<List<ClientDTO>> getClientsByNameAndSurname(
            @Parameter(description = "Имя пользователя", example = "Иван")
            @RequestParam String name,
            @Parameter(description = "Фамилия пользователя", example = "Иванов")
            @RequestParam String surname
    ) {
        List<ClientDTO> clients = clientService.findByClientNameAndClientSurname(name, surname);

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Получение всех клиентов", description = "Возвращает информацию о клиентах.")
    @ApiResponse(responseCode = "200", description = "Клиенты найдены")
    public ResponseEntity<List<ClientDTO>> getAllClients(
            @Parameter(description = "Лимит возвращаемых значений", example = "10")
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Количество значений, которое стоит пропустить", example = "0")
            @RequestParam(defaultValue = "0") int offset
    ) {
        List<ClientDTO> clients = clientService.findAll(limit, offset);

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PatchMapping("/{clientId}")
    @Operation(summary = "Изменение адреса клиента", description = "Изменяет адрес клиента по его id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изменение адреса успешно"),
            @ApiResponse(responseCode = "404", description = "Клиент с таким id не найден")
    })
    public ResponseEntity<?> updateAddress(
            @Parameter(description = "Id клиента", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
            @PathVariable UUID clientId,
            @Parameter(description = "Новый адрес")
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
    @Operation(summary = "Удаление клиента", description = "Удаляет клиента по его id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Клиент удален"),
            @ApiResponse(responseCode = "404", description = "Клиент с таким id не найден")
    })
    public ResponseEntity<Void> deleteClient(
            @Parameter(description = "Id клиента", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
            @PathVariable UUID clientId
    ) {
        try {
            clientService.delete(clientId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
