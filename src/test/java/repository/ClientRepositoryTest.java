package repository;

import com.smuniov.restaurantServiceSystem.Main;
import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.repository.ClientRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@TestPropertySource("classpath:test.properties")
public class ClientRepositoryTest {

//    static {
//        System.setProperty("spring.config.location", "classpath:test.properties");//spring.datasource.data=classpath:data.sql
//        //System.setProperty("spring.datasource.data", "classpath:data.sql");
//    }

    @Autowired
    private ClientRepository clientRepository;


    @Test
    public void testWriteReadClient() {

        Client client = new Client("rere@mail.ru", "1234", "Rere", "Rere", 0);
        client.setCreated(System.currentTimeMillis());
        clientRepository.save(client);
        Client clientFromDB = clientRepository.findByEmail("rere@mail.ru");
        assertNotNull(clientFromDB);
        assertEquals(client.getName(), clientFromDB.getName());
    }

    @Test
    public void testTable() {

        Client clientFromDB = clientRepository.findByEmail("olololo@mail.ru");
        assertNotNull(clientFromDB);
    }

}
