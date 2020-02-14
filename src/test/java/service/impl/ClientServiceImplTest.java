package service.impl;

import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.repository.ClientRepository;
import com.smuniov.restaurantServiceSystem.repository.EmployeeRepository;
import com.smuniov.restaurantServiceSystem.service.impl.ClientServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

        @InjectMocks
        ClientServiceImpl clientService;

        @Mock
        private ClientRepository mockClientRepository;
        @Mock
        private EmployeeRepository mockEmployeeRepository;

    @Test
    public void testCreateClient(){
        Client client = new Client("rere@mail.ru", "1234", "Rere", "Rere", 0);
        when(mockClientRepository.existsByEmail(client.getEmail())).thenReturn(false);
        when(mockEmployeeRepository.existsByEmail(client.getEmail())).thenReturn(false);//save
        when(mockClientRepository.save(client)).thenReturn(client);
        clientService.create(client);
        verify(mockClientRepository, times(1)).existsByEmail(client.getEmail());
        verify(mockEmployeeRepository, times(1)).existsByEmail(client.getEmail());
        verify(mockClientRepository, times(1)).save(client);
    }

}
