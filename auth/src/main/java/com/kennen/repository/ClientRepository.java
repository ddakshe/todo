package com.kennen.repository;

import com.kennen.data.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity, Long> {

    ClientEntity findByClientId(String clientId);
}
