package org.berneick.mapper;

import org.berneick.model.Address;
import org.berneick.model.Supplier;
import org.berneick.model.SupplierDTO;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public SupplierDTO toDTO(Supplier supplier) {
        return SupplierDTO.builder()
                .supplierId(supplier.getId())
                .name(supplier.getName())
                .addressId(supplier.getAddress().getId())
                .phoneNumber(supplier.getPhoneNumber())
                .build();
    }

    public Supplier toEntity(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setId(supplierDTO.getSupplierId());
        supplier.setName(supplierDTO.getName());

        Address address = new Address();
        address.setId(supplierDTO.getAddressId());
        supplier.setAddress(address);

        supplier.setPhoneNumber(supplierDTO.getPhoneNumber());

        return  supplier;
    }
}
