package com.smuniov.restaurantServiceSystem.service.impl;

import com.smuniov.restaurantServiceSystem.DTO.UserDTO;
import com.smuniov.restaurantServiceSystem.Exception.BadRequestException;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.repository.ClientRepository;
import com.smuniov.restaurantServiceSystem.repository.EmployeeRepository;
import com.smuniov.restaurantServiceSystem.service.ClientServiceI;
import org.apache.logging.log4j.LogManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientServiceI {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ClientServiceImpl.class.getName());

    private final ClientRepository clientRepository;

    private final EmployeeRepository employeeRepository;

    public ClientServiceImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void update(UserDTO clientDTO){
        if (clientDTO.getEmail() == null || clientDTO.getId() == 0){
            throw new BadRequestException("bad user");
        }
        Client clientToUpdate = clientRepository.findByEmail(clientDTO.getEmail());
        if (clientToUpdate == null){
            throw new BadRequestException("user doesnt exist");
        }
        if (clientToUpdate.getId() != clientDTO.getId() || !clientToUpdate.getName().equals(clientDTO.getName())
                || !clientToUpdate.getEmail().equals(clientDTO.getEmail())
                || !clientToUpdate.getSurname().equals(clientDTO.getSurname())
                || clientToUpdate.getCreated() != clientDTO.getCreated()
        ){
            throw new BadRequestException("wrong user data");
        }
        clientToUpdate.setDeposit(clientDTO.getDeposit());
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


//    public Page<Client> readAll(Pageable pageable) {
//        return clientRepository.findAll(pageable);
//    }

    public Page<UserDTO> readAll(Pageable pageable){
        List<Client> clients = clientRepository.findAll(pageable).getContent();
        List<UserDTO> clientsDto = UserDTO.toDTOList(clients);
        Page<UserDTO> userDTOPage= new PageImpl<>(clientsDto, pageable, clientsDto.size());
        return userDTOPage;
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
