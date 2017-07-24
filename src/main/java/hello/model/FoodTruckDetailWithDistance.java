package hello.model;

import java.io.IOException;

/**
 * Created by yuxij on 7/22/17.
 */
public class FoodTruckDetailWithDistance {
    private double distance;
    private String truckDetail;

    public FoodTruckDetailWithDistance() {

    }

    public FoodTruckDetailWithDistance(String truckDetail, double distance) throws IOException{
        this.distance = distance;
        this.truckDetail = truckDetail;
    }

    public String getTruckDetail() {
        return truckDetail;
    }

    public void setTruckDetail(String truckDetail) {
        this.truckDetail = truckDetail;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
