package service;

import dao.ClientDAO;
import entity.people.Client;

public class ApplicationService {
    private ClientDAO clientDAO;

    public ApplicationService(){
        clientDAO = new ClientDAO();
    }

    public String registerClient(Client client){
        clientDAO.create(client);
        return client.getName();
    }
}
