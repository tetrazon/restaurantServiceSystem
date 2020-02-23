package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smuniov.restaurantServiceSystem.DTO.UserDTO;
import com.smuniov.restaurantServiceSystem.controller.ClientsController;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.service.impl.ClientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableSpringDataWebSupport //registers a couple of Spring MVC extensions that allow the easy consumption of Pageable and Sort instances as well as automatic identifier-to-entity conversion
@WebMvcTest
@AutoConfigureMockMvc

public class ClientsControllerTest {
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
        List<UserDTO> dtos = new UserDTO<Client>().toDTOList(clients);
        String userDTOString = "{\"id\":0,\"email\":\"rere@mail.ru\",\"name\":\"Rere\",\"surname\":\"Rere\",\"created\":0,\"position\":\"CLIENT\",\"deposit\":100.0}";
        String clientString = new ObjectMapper().writeValueAsString(clients.get(0));
        given(clientService.readAll(any(Pageable.class))).willReturn(new PageImpl<>(dtos, PageRequest.of(0, 10), clients.size()));
        mvc.perform(get("/clients").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$..content.[0].name").value("Rere"))//jsonPath("$.content[0].name").value("Rere")
                .andExpect(jsonPath("$..content.[1].name").value("Ololo"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isFound());

    }
}
