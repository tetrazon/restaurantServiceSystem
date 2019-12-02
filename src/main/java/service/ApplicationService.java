package service;

import dao.db_controller.PgControllerWithConnPool;
import people.Client;

public class ApplicationService {
    PgControllerWithConnPool pgControllerWithConnPool;

    public ApplicationService(){
        pgControllerWithConnPool = new PgControllerWithConnPool();
    }

    public String registerClient(Client client){
        pgControllerWithConnPool.registerClient(client);
        return client.getName();
    }
}
