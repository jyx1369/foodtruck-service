package hello.dataloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import hello.model.FoodTruckDetail;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by yuxij on 7/16/17.
 */
public class CsvUtil {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CsvUtil.class);
    private static final char DEFAULT_SEPARATOR = ',';
    public static void storeFileToDb(String path) throws IOException{
        //Json util
        ObjectMapper mapper = new ObjectMapper();
        // create jdbc connection
        Connection conn = MyDataSourceFactory.getConnection();
        String insertTableSQL = "INSERT INTO foodtruck"
                + "(locationid, latitude, longitude, json) VALUES"
                + "(?,?,?,?)";
        //create CSVReader object
        CSVReader reader = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            reader = new CSVReader(new InputStreamReader(loader.getResourceAsStream(path)), DEFAULT_SEPARATOR);
            //read line by line
            String[] record = null;
            //skip header row
            String[] fieldNames = reader.readNext();
            int locationidPos = -1;
            int applicantPos = -1;
            int addressPos = -1;
            int foodItemsPos = -1;
            int latitudePos = -1;
            int longitudePos = -1;
            int locationPos = -1;
            int dayshoursPos = -1;
            for (int i = 0; i < fieldNames.length; i++) {
                switch (fieldNames[i].trim()) {
                    case "\uFEFFlocationid":
                        locationidPos = i;
                    case "Applicant":
                        applicantPos = i;
                        break;
                    case "Address":
                        addressPos = i;
                        break;
                    case "FoodItems":
                        foodItemsPos = i;
                        break;
                    case "Latitude":
                        latitudePos = i;
                        break;
                    case "Longitude":
                        longitudePos = i;
                        break;
                    case "Location":
                        locationPos = i;
                        break;
                    case "dayshours":
                        dayshoursPos = i;
                        break;
                    default:
                        break;
                }
            }
            int count = 0;
            while ((record = reader.readNext()) != null) {
                FoodTruckDetail foodTruck = new FoodTruckDetail(record[locationidPos], record[applicantPos], record[addressPos],
                        record[foodItemsPos], record[latitudePos], record[longitudePos], record[locationPos], record[dayshoursPos]);
                String jsonInString = mapper.writeValueAsString(foodTruck);
                PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
                preparedStatement.setInt(1, foodTruck.getLocationid());
                preparedStatement.setDouble(2, foodTruck.getLatitude());
                preparedStatement.setDouble(3, foodTruck.getLongitude());
                preparedStatement.setString(4, jsonInString);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                count++;
            }
            System.out.println(count);
        }catch (Exception e) {
            logger.error("Exception thrown: " + e);
        } finally{
            try {
                if(reader != null) reader.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                logger.error("Unable to close reader or connection: " + e);
            }
        }
    }

    public static void dbTableInit(String tableName) {
        Connection conn = MyDataSourceFactory.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String createTableSql = "CREATE TABLE IF NOT EXISTS %s (locationid INT NOT NULL, latitude DOUBLE, longitude DOUBLE, json TEXT, PRIMARY KEY ( locationid), CONSTRAINT CHK_LOCATION_RANGE CHECK (latitude>=-90 AND latitude<=90 AND longitude>=-180 AND longitude<=180));";
            stmt.executeUpdate(String.format(createTableSql, tableName));
            String createIndexSql = "CREATE INDEX %s on %s(%s);";
            stmt.executeUpdate(String.format(createIndexSql, "lat_idx", tableName, "latitude"));
            stmt.executeUpdate(String.format(createIndexSql, "long_idx", tableName, "longitude"));
        } catch (SQLException e) {
            logger.error("Exception thrown: " + e);
        } finally{
            try {
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                logger.error("Unable to close sql statement or connection: " + e);
            }
        }

    }
    public static void main(String[] args) throws Exception{
        dbTableInit("foodtruck");
        storeFileToDb("data/Mobile_Food_Facility_Permit.csv");
    }


}
