package dao;

import entity.users.Client;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientDAO {
    void deleteClientById(int id);
    List<Client> getAll();
    Client getClientByEmail(String emailToCheck);
    Client getClientById(int clientId);
    void updateClient(Client client);
    void create(Client client);


}
