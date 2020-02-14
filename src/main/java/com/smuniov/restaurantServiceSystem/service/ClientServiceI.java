package com.smuniov.restaurantServiceSystem.service;

import com.smuniov.restaurantServiceSystem.entity.users.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientServiceI{

    void create(Client client);
    public List readAll();
    void delete(Integer id);
    void update(Client client);
    Client getClientByEmail(String emailToCheck);
    Client findById(Integer id);
    public Page<Client> readAll(Pageable pageable);


}
