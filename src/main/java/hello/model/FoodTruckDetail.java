package hello.model;

import hello.dataloader.CsvUtil;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yuxij on 7/16/17.
 */
public class FoodTruckDetail {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FoodTruckDetail.class);

    private int locationid;
    private String applicant;
    private String address;
    private List<String> foodItems;
    private double latitude;
    private double longitude;
    private String location;
    private String dayshours;

    public FoodTruckDetail() {

    }

    public FoodTruckDetail(String locationid, String applicant, String address, String foodItems, String latitude,
                           String longitude, String location, String dayshours) {
        try {
            this.locationid = Integer.parseInt(locationid);
            this.applicant = applicant;
            this.address = address;
            this.foodItems = Arrays.asList(foodItems.split("(:)(\\s+)?", -1));
            this.latitude = Double.parseDouble(latitude);
            this.longitude = Double.parseDouble(longitude);
            this.location = location;
            this.dayshours = dayshours;
        } catch (NumberFormatException e) {
            logger.error("NumberFormatException with " + this.locationid);
        }

    }
    public int getLocationid() {
        return locationid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDayshours() {
        return dayshours;
    }

    public void setDayshours(String dayshours) {
        this.dayshours = dayshours;
    }

    public List<String> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<String> foodItems) {
        this.foodItems = foodItems;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
