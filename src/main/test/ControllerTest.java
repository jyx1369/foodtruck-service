import hello.controller.MainController;
import hello.model.FoodTruckDetailWithDistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by yuxij on 7/23/17.
 */
@SpringBootTest(classes=MainController.class)
@RunWith(SpringRunner.class)
public class ControllerTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MainController clientRepository;

    @Test
    public void testNormalInputForFindAllTrucksNearby() {
        try {
            List<FoodTruckDetailWithDistance> client = clientRepository.findAllTrucksNearby(37.0,-122.0);
            assertEquals(1, client.size());
        } catch(Exception e) {
            System.out.println("Bad thing happened");
        }
    }

    @Test
    public void testNullInputForFindAllTrucksNearby() {
        try {
            List<FoodTruckDetailWithDistance> client = clientRepository.findAllTrucksNearby(null,-122.0);
            assertEquals(0, client.size());
        } catch(Exception e) {
            System.out.println("Bad thing happened");
        }
    }

    @Test
    public void testInvalidInputForFindAllTrucksNearby() {
        try {
            // test when input is above the range of latitude(-90 ~ 90) or longitude(180 ~ 180)
            List<FoodTruckDetailWithDistance> client = clientRepository.findAllTrucksNearby(1000.0, -122.0);
            assertEquals(0, client.size());
        } catch(Exception e) {
            System.out.println("Bad thing happened");
        }
    }

}
