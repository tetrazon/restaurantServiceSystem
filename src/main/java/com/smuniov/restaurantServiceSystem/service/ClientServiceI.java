package com.smuniov.restaurantServiceSystem.service;

import com.smuniov.restaurantServiceSystem.DTO.UserDTO;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientServiceI{

    void create(Client client);
    void delete(Integer id);
    void update(UserDTO clientDTO);
    Client getClientByEmail(String emailToCheck);
    Client findById(Integer id);
    Page<UserDTO> readAll(Pageable pageable);


}
