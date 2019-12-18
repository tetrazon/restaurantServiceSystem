package service;

import dao.ClientDAO;
import entity.users.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ClientService {
    private static Logger logger = LoggerFactory.getLogger(ClientService.class);
    private static ClientDAO clientDAO = new ClientDAO();

public void add(Client client){
    clientDAO.create(client);
}

public String getPasswordByEmail(String email){
        return clientDAO.getPasswordByEmail(email);
}

    public Integer getIdByEmail(String email){
        return clientDAO.getIdByEmail(email);
    }

public List<Client> getAll(){
        return clientDAO.getAll();
}

    public double checkDeposit(int clientId, double invoice){
        Double clientDeposit = clientDAO.getClientDeposit(clientId);
        if(clientDeposit == null){
            logger.error("wrong input");
            throw new NullPointerException();
        }
        return clientDeposit - invoice;
    }

    public void updateDeposit(int clientId, double newDeposit){
        clientDAO.updateClientDeposit(clientId, newDeposit);
    }

    public void deleteClient(int id){
    clientDAO.deleteClient(id);
    }

}
