package com.smuniov.restaurantServiceSystem.service.impl;

import com.smuniov.restaurantServiceSystem.Exception.BadRequestException;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.repository.ClientRepository;
import com.smuniov.restaurantServiceSystem.repository.EmployeeRepository;
import com.smuniov.restaurantServiceSystem.service.ClientServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements ClientServiceI {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void update(Client client){
        clientRepository.saveAndFlush(client);
    }

    @Override
    public Client getClientByEmail(String emailToCheck) {
        return clientRepository.findByEmail(emailToCheck);
    }


    @Override
    public void create(Client client) {
        if(client.getEmail() == null ||
                client.getPassword() == null ||
                client.getName() == null || client.getSurname() == null){
            throw new BadRequestException("incorrect user data!");
        }
        Client existedClient = clientRepository.findByEmail(client.getEmail());
        Employee existedEmployee = employeeRepository.findByEmail(client.getEmail());
        if(existedClient == null && existedEmployee == null) {
            client.setCreated(System.currentTimeMillis());
            client.setDeposit(100);
            clientRepository.save(client);
        } else {
            throw new BadRequestException("user with email " + client.getEmail() + "is existed!");
        }
    }

    @Override
    public List readAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        clientRepository.deleteById(id);
    }
}
