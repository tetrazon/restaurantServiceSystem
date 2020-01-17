package spring.controller;

import entity.users.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    private ClientService clientService;

    @Autowired
    public ClientsController(ClientService clientService){
        this.clientService = clientService;
    }

    @RequestMapping(method= RequestMethod.GET, produces="application/json")// this method will only handle requests where JSON output is expected.
    public @ResponseBody List<Client> getClients(){//@RequestBody Client client ;
        return clientService.getAll();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET, produces="application/json")
    public  Client getClient(@PathVariable int id){
        return clientService.getClientById(id);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public  ResponseEntity deleteClient(@PathVariable int id){
        clientService.deleteClient(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.PUT)
    public  ResponseEntity updateClient(@RequestBody Client client){
        if (client == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        clientService.updateClient(client);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="/new", method= RequestMethod.POST)
    public  ResponseEntity addClient(@RequestBody Client client){
        clientService.add(client);
        if(client == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
