package service;

import dao.ClientDAO;
import dao.hibernate.ClientDAOHibernate;
import dao.jdbc.ClientDAOJdbc;
import entity.users.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RoundDouble;

import java.util.List;

public class ClientService {
    private static Logger logger = LoggerFactory.getLogger(ClientService.class);
    private static ClientDAO clientDAO = new ClientDAOHibernate();

public void add(Client client){
    clientDAO.create(client);
}

public String getPasswordByEmail(String email){
    Client client = clientDAO.getClientByEmail(email);
    if(client == null){
        logger.info("no such client");
        return null;
    }
        return clientDAO.getClientByEmail(email).getPassword();
}

    public Integer getIdByEmail(String email){
        return clientDAO.getClientByEmail(email).getId();
    }

public List<Client> getAll(){
        return clientDAO.getAll();
}

    public double checkDeposit(Client client, double invoice){
        Double clientDeposit = client.getDeposit();
        if(clientDeposit == null){
            logger.error("wrong input");
            throw new NullPointerException();
        }
        return clientDeposit - invoice;
    }

    public void updateDeposit(Client client, double newDeposit){
    client.setDeposit(RoundDouble.roundDouble(newDeposit, 2));
        clientDAO.updateClientDeposit(client);
    }

    public void deleteClient(int id){
    clientDAO.deleteClientById(id);
    }

    public Client getClientById(int id){
    return clientDAO.getClientById(id);
    }
}
