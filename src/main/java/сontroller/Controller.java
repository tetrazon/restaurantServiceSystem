package сontroller;

import people.Client;

import java.util.List;

public interface Controller {
    void registerClient(Client client);
    void deleteClient(Client client);
    List<Client> showAllClients();
    void changeClientParams(Client client);
}
