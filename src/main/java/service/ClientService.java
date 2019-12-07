package service;

import dao.ClientDAO;
import entity.people.Client;

import java.util.List;

public class ClientService {
    private ClientDAO clientDAO;

    public ClientService(){
        clientDAO = new ClientDAO();
    }

public void add(Client client){
    clientDAO.create(client);
}

public String getPasswordByEmail(String email){
        return clientDAO.getPasswordByEmail(email);
}

public List<Client> getAll(){
        return clientDAO.getAll();
}

}
