package org.berneick.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.berneick.exception.ClientNotFoundException;
import org.berneick.exception.SupplierNotFoundException;
import org.berneick.model.AddressDTO;
import org.berneick.model.SupplierDTO;
import org.berneick.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/suppliers")
@Tag(name = "Supplier API", description = "API для управления поставщиками")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @PostMapping
    @Operation(summary = "Создание нового поставщика", description = "Создает нового поставщика с предоставленными данными.")
    @ApiResponse(responseCode = "201", description = "Поставщик успешно создан")
    public ResponseEntity<SupplierDTO> createSupplier(@Valid @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO createdSupplier = supplierService.createSupplier(supplierDTO);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    @PatchMapping("/{supplierId}")
    @Operation(summary = "Изменение адреса поставщика", description = "Изменяет адрес поставщика по его id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изменение адреса успешно"),
            @ApiResponse(responseCode = "404", description = "Поставщик с таким id не найден")
    })
    public ResponseEntity<?> updateAddress(
            @Parameter(description = "Id поставщика", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
            @PathVariable UUID supplierId,
            @Parameter(description = "Новый адрес")
            @Valid @RequestBody AddressDTO addressDTO
    ) {
        try {
            SupplierDTO supplierDTO = supplierService.updateAddress(supplierId, addressDTO);
            return new ResponseEntity<>(supplierDTO, HttpStatus.OK);
        } catch (ClientNotFoundException e) {
            return new ResponseEntity<>("Supplier not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Получение всех поставщиков", description = "Возвращает информацию о поставщиках.")
    @ApiResponse(responseCode = "200", description = "Поставщики найдены")
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.findAll();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @GetMapping("/{supplierId}")
    @Operation(summary = "Получение поставщика по id", description = "Возвращает информацию о поставщике по его id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поставщик найден"),
            @ApiResponse(responseCode = "404", description = "Поставщик с таким id не найден")
    })
    public ResponseEntity<?> getSupplierById(
            @Parameter(description = "Id поставщика", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
            @PathVariable UUID supplierId
    ) {
        try {
            SupplierDTO supplier = supplierService.findById(supplierId);
            return new ResponseEntity<>(supplier, HttpStatus.OK);
        } catch (SupplierNotFoundException e) {
            return new ResponseEntity<>("Supplier not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{supplierId}")
    @Operation(summary = "Удаление поставщика по id", description = "Удаляет поставщика по его id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Поставщик удален"),
            @ApiResponse(responseCode = "404", description = "Поставщик с таким id не найден")
    })
    public ResponseEntity<Void> deleteSupplier(
            @Parameter(description = "Id поставщика", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
            @PathVariable UUID supplierId
    ) {
        try {
            supplierService.delete(supplierId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SupplierNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
