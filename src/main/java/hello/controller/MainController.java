package hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.model.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private FoodTruckRepository foodTruckRepository;
    @PersistenceContext
    EntityManager em;

    @GetMapping(path="/alltrucks")
    public @ResponseBody Iterable<FoodTruck> getAllFoodTrucks() {
        // This returns a JSON or XML with the users
        return foodTruckRepository.findAll();
    }

    @GetMapping(path="/foodtrucks")
    public @ResponseBody List<FoodTruckDetailWithDistance> findAllTrucksNearby(@RequestParam("lat") Double latitude, @RequestParam("long") Double longitude){
        double distance = 1.0;
        // Each mile is approximately 69 degree latitude and 55 degree longitude.
        double latitudeDiff = distance/69;
        double longitudeDiff = distance/55;

        double startLatitude = latitude - latitudeDiff;
        double endLatitude = latitude + latitudeDiff;
        double startLongitude = longitude - longitudeDiff;
        double endLongitude = longitude + longitudeDiff;

        //Query query = em.createNativeQuery("SELECT json, ( 3959 * acos( cos( radians(?1) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?2) ) + sin( radians(?1) ) * sin( radians( latitude ) ) ) ) AS distance FROM foodtruck HAVING distance < 11 ORDER BY distance", "FoodTruckDetail");
        Query query = em.createNativeQuery("SELECT json, (3959 * acos( cos( radians(?1) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?2) ) + sin( radians(?1) ) * sin( radians( latitude ) ) ) ) AS distance FROM foodtruck WHERE locationId in (SELECT locationId FROM foodtruck WHERE latitude >=?3 and latitude <=?4 and longitude >=?5 and longitude <=?6) HAVING distance < ?7 ORDER BY distance", "FoodTruckDetailWithDistance");
        query.setParameter(1, latitude);
        query.setParameter(2, longitude);

        query.setParameter(3, startLatitude);
        query.setParameter(4, endLatitude);

        query.setParameter(5, startLongitude);
        query.setParameter(6, endLongitude);

        query.setParameter(7, distance);

        List<FoodTruckDetailWithDistance> res = query.getResultList();
        logger.info("Found " + res.size() + " foodtrucks within " + distance +  " miles from location {" +" latitude: " + latitude + ", longitude: " + longitude + "}");
        return res;
    }

}
