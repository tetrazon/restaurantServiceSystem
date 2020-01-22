package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.hardCode.ClientService;

@Controller
@RequestMapping("/login")
public class LoginController {// just for test jsp

    private ClientService clientService;

    @Autowired
    public LoginController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping
    public String login() {
        return "login";
    }
}
