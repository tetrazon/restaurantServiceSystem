package controller;
public class LoginControllerTest {}

//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.servlet.view.InternalResourceView;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
//
//public class LoginControllerTest {
//private ClientServiceInterface clientService;
//
//@Autowired
//public LoginControllerTest(ClientServiceInterface clientService){
//    this.clientService = clientService;
//}
//    @Test
//    public void testLoginPage() throws Exception {
//        LoginController controller = new LoginController(clientService);
//        MockMvc mockMvc = standaloneSetup(controller)
//                .setSingleView(
//                        new InternalResourceView("/WEB-INF/views/login.jsp"))
//                .build();
//        mockMvc.perform(get("/login"))                   //Perform GET
//                .andExpect(view().name("login")); //Expect home view
//
//    }
//}
