package service;

import dao.ClientDAO;
import entity.people.Client;

import java.util.List;

public class ClientService {
    private ClientDAO clientDAO;

    public ClientService(){
        clientDAO = new ClientDAO();
    }

public void add(String email, String password, String name, String surname){
    Client client = new Client(email, password, name, surname, System.currentTimeMillis());
    clientDAO.create(client);
}

public List<Client> getAll(){
        return clientDAO.getAll();
}

}
