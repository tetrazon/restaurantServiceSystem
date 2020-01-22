package controller;

import entity.users.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.hardCode.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    private ClientService clientService;

    @Autowired
    public ClientsController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping
    public @ResponseBody List<Client> getClients(){
        return clientService.getAll();
    }

    @GetMapping(value="/{id}")
    public  Client getClient(@PathVariable int id){
        return clientService.getClientById(id);
    }

    @DeleteMapping(value="/{id}")
    public  ResponseEntity deleteClient(@PathVariable int id){
        clientService.deleteClient(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping
    public  ResponseEntity updateClient(@RequestBody Client client){
        if (client == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        clientService.updateClient(client);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value="/new")
    public  ResponseEntity addClient(@RequestBody Client client){
        clientService.add(client);
        if(client == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
