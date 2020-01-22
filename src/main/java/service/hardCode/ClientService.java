package service.hardCode;

import dao.ClientDAO;
import entity.users.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import utils.RoundDouble;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)//(propagation = Propagation.REQUIRED)
public class ClientService {
    private static Logger logger = LoggerFactory.getLogger(ClientService.class);
    private ClientDAO clientDAO;

    @Autowired
    public ClientService(ClientDAO clientDAO){
        this.clientDAO = clientDAO;
    }

    public void add(Client client){
        //client.setCreated(System.currentTimeMillis());
        clientDAO.create(client);
    }
    //@Transactional(propagation=Propagation.REQUIRED)
    public void updateClient(Client client) {
        clientDAO.updateClient(client);
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
        clientDAO.updateClient(client);
    }

    public void deleteClient(int id){
        clientDAO.deleteClientById(id);
    }

    public Client getClientById(int id){
        return clientDAO.getClientById(id);
    }
}
