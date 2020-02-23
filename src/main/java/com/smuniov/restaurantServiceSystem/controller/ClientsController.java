package com.smuniov.restaurantServiceSystem.controller;

import com.smuniov.restaurantServiceSystem.DTO.UserDTO;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.service.ClientServiceI;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static org.springframework.http.HttpStatus.*;

@Transactional
@RestController
@RequestMapping("/clients")
public class ClientsController {


    private final ClientServiceI clientService;

    public ClientsController(ClientServiceI clientService) {
        this.clientService = clientService;
    }

    @GetMapping(produces="application/json")
    //@JsonView(ClientDataAccess.class)
    @RolesAllowed("ROLE_MANAGER")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})

    public ResponseEntity<Page<UserDTO>> getClients(@PageableDefault(page=0, size = 10, sort = "name", direction = Sort.Direction.DESC) Pageable pageable){
        Page<UserDTO> userDTOPage= clientService.readAll(pageable);
        return new ResponseEntity<>(userDTOPage, FOUND);
    }

    @GetMapping(value="/{id}")
    @RolesAllowed("ROLE_MANAGER")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public UserDTO getClient(@PathVariable int id){
        UserDTO<Client> clientDTO = UserDTO.toDTO(clientService.findById(id));
        return clientDTO;
    }

    @DeleteMapping(value="/{id}")
    @RolesAllowed("ROLE_MANAGER")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public  ResponseEntity deleteClient(@PathVariable int id){
        clientService.delete(id);
        return new ResponseEntity(OK);
    }

    @PutMapping
    @RolesAllowed("ROLE_MANAGER")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public  ResponseEntity updateClientDeposit(@RequestBody UserDTO client){

        clientService.update(client);
        return new ResponseEntity(OK);
    }

    @PostMapping(value="/new")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public  ResponseEntity addClient(@RequestBody Client clientToAdd){
        clientService.create(clientToAdd);
        return new ResponseEntity(CREATED);
    }

}
