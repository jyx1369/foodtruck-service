package hello.model;

import javax.persistence.*;

/**
 * Created by yuxij on 7/16/17.
 */

@SqlResultSetMapping(
        name="FoodTruckDetailWithDistance",
        classes={
                @ConstructorResult(
                        targetClass=FoodTruckDetailWithDistance.class,
                        columns={
                                @ColumnResult(name="json", type=String.class),
                                @ColumnResult(name="distance", type=Double.class),
                        }
                )
        }
)
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "foodtruck")
public class FoodTruck {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int locationid;
    private double latitude;
    private double longitude;
    @Column(columnDefinition = "TEXT")
    private String json;

    public FoodTruck() {

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

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
