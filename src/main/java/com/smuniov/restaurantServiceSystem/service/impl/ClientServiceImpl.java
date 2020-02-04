package com.smuniov.restaurantServiceSystem.service.impl;

import com.smuniov.restaurantServiceSystem.Exception.BadRequestException;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.repository.ClientRepository;
import com.smuniov.restaurantServiceSystem.repository.EmployeeRepository;
import com.smuniov.restaurantServiceSystem.service.ClientServiceI;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements ClientServiceI {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ClientServiceImpl.class.getName());

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
        String emailToCheck = client.getEmail();
        try{
        if(emailToCheck == null ||
                client.getPassword() == null ||
                client.getName() == null || client.getSurname() == null){
            throw new BadRequestException("incorrect user data!");
        }
        if(!clientRepository.existsByEmail(emailToCheck) && !employeeRepository.existsByEmail(emailToCheck)){
            client.setCreated(System.currentTimeMillis());
            client.setDeposit(100);
            clientRepository.save(client);
            logger.info("client with email: " + emailToCheck + "is registered");
        } else {
            throw new BadRequestException("user with email " + client.getEmail() + "is existed!");
        }} catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List readAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Integer id) {
        return clientRepository.getOne(id);
    }

    @Override
    public void delete(Integer id) {
        clientRepository.deleteById(id);
    }
}
