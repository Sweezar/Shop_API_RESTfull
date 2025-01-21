package org.berneick.repository;

import org.berneick.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    List<Client> findByClientNameAndClientSurname(String clientName, String clientSurname);
}
