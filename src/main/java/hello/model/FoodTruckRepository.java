package hello.model;

import org.springframework.data.repository.CrudRepository;
/**
 * Created by yuxij on 7/19/17.
 * This will be AUTO IMPLEMENTED by Spring into a Bean called FoodTruckRepository
   CRUD refers Create, Read, Update, Delete
 */
public interface FoodTruckRepository extends CrudRepository<FoodTruck, Long>{
}
