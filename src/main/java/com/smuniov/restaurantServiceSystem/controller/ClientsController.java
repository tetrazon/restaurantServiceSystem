package com.smuniov.restaurantServiceSystem.controller;

import com.smuniov.restaurantServiceSystem.DTO.UserDTO;
import com.smuniov.restaurantServiceSystem.Exception.BadRequestException;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.service.impl.ClientServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/clients")

public class ClientsController {


    private final ClientServiceImpl clientServiceImpl;

    public ClientsController(ClientServiceImpl clientServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
    }

    @GetMapping(produces="application/json")
    //@JsonView(ClientDataAccess.class)
    @RolesAllowed("ROLE_MANAGER")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})

    public ResponseEntity<Page<UserDTO>> getClients(@PageableDefault(page=0, size = 10, sort = "name") Pageable pageable){//List<UserDTO>
        List<Client> clients = clientServiceImpl.readAll(pageable).getContent();
        List<UserDTO> clientsDto = new UserDTO<Client>().toDTO(clients);
        Page<UserDTO> userDTOPage= new PageImpl<>(clientsDto, pageable, clientsDto.size());
        return new ResponseEntity<>(userDTOPage, FOUND);
    }

    @GetMapping(value="/{id}")
    @RolesAllowed("ROLE_MANAGER")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public Client getClient(@PathVariable int id){
        return clientServiceImpl.findById(id);
    }

    @DeleteMapping(value="/{id}")
    @RolesAllowed("ROLE_MANAGER")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public  ResponseEntity deleteClient(@PathVariable int id){
        clientServiceImpl.delete(id);
        return new ResponseEntity(OK);
    }

    @PutMapping
    @RolesAllowed("ROLE_MANAGER")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public  ResponseEntity updateClient(@RequestBody Client client){
        if (client == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        clientServiceImpl.update(client);
        return new ResponseEntity(OK);
    }

    @PostMapping(value="/new")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public  ResponseEntity addClient(@RequestBody Client clientToAdd){
        clientServiceImpl.create(clientToAdd);
        return new ResponseEntity(CREATED);
    }

}
