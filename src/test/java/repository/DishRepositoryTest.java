package repository;

import com.smuniov.restaurantServiceSystem.Main;
import com.smuniov.restaurantServiceSystem.entity.food.Dish;
import com.smuniov.restaurantServiceSystem.repository.DishRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
//@TestPropertySource(locations="src/test/resource/test.properties") //doesnt work
public class DishRepositoryTest {

    static {
        System.setProperty("spring.config.location", "classpath:test.properties");
    }

    @Autowired
    private DishRepository dishRepository;

    @Test
    public void testDishWriteRead(){
        Dish dish = new Dish("Fish", 10.5);
        dish.setFoodCategory("SECOND_COURSE");
        dish.setDescription("bla");
        dishRepository.save(dish);
        Dish dishFromDB = dishRepository.findAll().get(0);
        System.out.println((dishFromDB.getId()));
        assertNotNull(dishFromDB);
        assertEquals(dish.getName(), dishFromDB.getName());
    }
}
