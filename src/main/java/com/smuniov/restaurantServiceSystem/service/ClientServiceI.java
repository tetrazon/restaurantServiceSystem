package com.smuniov.restaurantServiceSystem.service;

import com.smuniov.restaurantServiceSystem.entity.users.Client;

import java.util.List;
import java.util.Optional;

public interface ClientServiceI{

    void create(Client client);
    public List readAll();
    void delete(Integer id);
    void update(Client client);
    Client getClientByEmail(String emailToCheck);
    Client findById(Integer id);


}
