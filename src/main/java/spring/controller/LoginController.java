package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ClientService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/login")
public class LoginController {// just for test jsp

    private ClientService clientService;

    @Autowired
    public LoginController(ClientService clientService){
        this.clientService = clientService;
    }

    @RequestMapping(method=GET)
    public String login() {
        return "login";
    }
}
