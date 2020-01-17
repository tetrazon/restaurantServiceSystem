package dao;

import entity.users.Client;

import java.util.List;

public interface ClientDAO {
    void deleteClientById(int id);
    List<Client> getAll();
    Client getClientByEmail(String emailToCheck);
    Client getClientById(int clientId);
    //void updateClientDeposit(int clientId, double newDeposit);
    void updateClient(Client client);
    void create(Client client);


}
