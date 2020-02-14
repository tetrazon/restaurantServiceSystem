package controller;

import com.smuniov.restaurantServiceSystem.DTO.UserDTO;
import com.smuniov.restaurantServiceSystem.controller.ClientsController;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.service.impl.ClientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Main.class)
//@EnableSwagger2
//@RunWith(MockitoJUnitRunner.class)
//@WebMvcTest(ClientsController.class)
//@WebMvcTest(SpringBootTest.WebEnvironment.MOCK)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableSpringDataWebSupport //registers a couple of Spring MVC extensions that allow the easy consumption of Pageable and Sort instances as well as automatic identifier-to-entity conversion
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebFluxTest
//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc

public class ClientsControllerTest {
//    @Mock
//    ClientServiceImpl clientService;
//    @LocalServerPort
//    private int port;
//    @Test
//    public void shouldReturnClients() {
//        List<Client> clients = new ArrayList<>();
//        clients.add(new Client("rere@mail.ru", "1234", "Rere", "Rere", 0));
//        clients.add(new Client("ololo@mail.ru", "1234", "Ololo", "Ololo", 0));
//
//        Pageable pageable = PageRequest.of(0, 10);
//        when(clientService.readAll(any(Pageable.class))).thenReturn(new PageImpl<>(clients, pageable, clients.size()));
//        WebTestClient testClient = WebTestClient.bindToController(new ClientsController(clientService)).build();
//        testClient.get().uri("http://localhost:" + port + "/clients").exchange().expectStatus().isFound();
//        .expectBody()
//        .jsonPath("$").isNotEmpty();
//
//    }

    private MockMvc mvc;

    @Mock
    private ClientServiceImpl clientService;
    @InjectMocks
    ClientsController clientsController;

    @Before
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);

        this.mvc = MockMvcBuilders.standaloneSetup(clientsController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();

    }
    @Test
    public void testGetClients() throws Exception {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client("rere@mail.ru", "1234", "Rere", "Rere", 0));
        clients.add(new Client("ololo@mail.ru", "1234", "Ololo", "Ololo", 0));
        //UserDTO
        String userDTOString = "{\"id\":0,\"email\":\"rere@mail.ru\",\"name\":\"Rere\",\"surname\":\"Rere\",\"created\":0,\"position\":\"CLIENT\",\"deposit\":100.0}";
        String clientString = new ObjectMapper().writeValueAsString(clients.get(0));
        given(clientService.readAll(any(Pageable.class))).willReturn(new PageImpl<>(clients, PageRequest.of(0, 10), clients.size()));
        mvc.perform(get("/clients").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$..content.[0].name").value("Rere"))//jsonPath("$.content[0].name").value("Rere")
                //.andExpect(content().json(clientString))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isFound());
//        ObjectMapper mapper = new ObjectMapper();
//        MvcResult mvcResult = (MvcResult) mvc.perform(get("/clients")).andReturn();
//        //Page<UserDTO> userDTOPage= new PageImpl<>(clientsDto, pageable, clientsDto.size());
//        Page<UserDTO> page = mapper.readValue(mvcResult.getResponse().getContentAsString(), RestResponsePage.class);
//        List<UserDTO> userDTOS = (List<UserDTO>)page.getContent();
//        System.out.println(userDTOS.get(0).getCreated());
//        assertEquals(userDTOS.get(0).getName(),"Rere");
//        for (int i = 0; i < userDTOS.size(); i++) {
//            assertEquals(clients.get(i).getName(), (userDTOS.get(i).getName()));
//        }

    }
}

//@Configuration
//@EnableSpringDataWebSupport
//class WebMvcConfig implements WebMvcConfigurer {
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add( new PageableHandlerMethodArgumentResolver());
//    }
//}
