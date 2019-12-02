package сontroller;

import people.Client;
import service.ApplicationService;

public class ApplicationController {
    private ApplicationService appService;

    public ApplicationController() {
        appService = new ApplicationService();
    }

    public String registerClient(Client client){
        return appService.registerClient(client);
    }
}
