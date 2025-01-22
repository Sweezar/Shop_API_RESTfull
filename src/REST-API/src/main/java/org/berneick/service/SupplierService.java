package org.berneick.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.berneick.exception.SupplierNotFoundException;
import org.berneick.mapper.AddressMapper;
import org.berneick.mapper.SupplierMapper;
import org.berneick.model.*;
import org.berneick.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Nonnull
    @Transactional
    public SupplierDTO createSupplier(@Nonnull SupplierDTO supplierDTO) {
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    @Nonnull
    @Transactional
    public SupplierDTO updateAddress(@Nonnull UUID supplierId, @Nonnull AddressDTO addressDTO) throws SupplierNotFoundException {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id: " + supplierId));

        Address address = addressMapper.toEntity(addressDTO);
        address.setId(supplier.getAddress().getId());
        supplier.setAddress(address);

        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    @Nonnull
    @Transactional(readOnly = true)
    public List<SupplierDTO> findAll() {
        return supplierRepository.findAll().stream()
                .map(supplierMapper::toDTO)
                .toList();
    }

    @Nonnull
    @Transactional(readOnly = true)
    public SupplierDTO findById(@Nonnull UUID supplierId) throws SupplierNotFoundException {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id: " + supplierId));

        return supplierMapper.toDTO(supplier);
    }

    @Transactional
    public void delete(@Nonnull UUID supplierId) throws SupplierNotFoundException {
        if(!supplierRepository.existsById(supplierId)) {
            throw new SupplierNotFoundException("Supplier not found with id: " + supplierId);
        }

        supplierRepository.deleteById(supplierId);
    }

}
