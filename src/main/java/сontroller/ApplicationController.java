package сontroller;

import service.ClientService;

public class ApplicationController {
    private ClientService appService;

    public ApplicationController() {
        appService = new ClientService();
    }

}
