package com.smuniov.restaurantServiceSystem.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smuniov.restaurantServiceSystem.DTO.Transfer.ClientDataAccess;
import com.smuniov.restaurantServiceSystem.DTO.UserDTO;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private ClientServiceImpl clientServiceImpl;

    @GetMapping
    @JsonView(ClientDataAccess.class)
    @RolesAllowed("ROLE_MANAGER")
    public ResponseEntity<List<UserDTO>> getClients(){
//        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.add(HttpHeaders.LOCATION, "https://ololo");
        List<Client> clients = clientServiceImpl.readAll();
        List<UserDTO> clientsDto = new UserDTO<Client>().toDTO(clients);
        return new ResponseEntity<>(clientsDto, OK);
//        return new ResponseEntity<>(clientsDto, headers, FOUND);
    }

    @GetMapping(value="/{id}")
    @RolesAllowed("ROLE_MANAGER")
    public Client getClient(@PathVariable int id){
        return clientServiceImpl.findById(id);
    }

    @DeleteMapping(value="/{id}")
    @RolesAllowed("ROLE_MANAGER")
    public  ResponseEntity deleteClient(@PathVariable int id){
        clientServiceImpl.delete(id);
        return new ResponseEntity(OK);
    }

    @PutMapping
    @RolesAllowed("ROLE_MANAGER")
    public  ResponseEntity updateClient(@RequestBody Client client){
        if (client == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        clientServiceImpl.update(client);
        return new ResponseEntity(OK);
    }

    @PostMapping(value="/new")
    public  ResponseEntity addClient(@RequestBody Client clientToAdd){
        clientServiceImpl.create(clientToAdd);
        return new ResponseEntity(OK);
    }

}
