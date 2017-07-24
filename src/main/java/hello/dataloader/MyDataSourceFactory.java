package hello.dataloader;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by yuxij on 7/18/17.
 */
public class MyDataSourceFactory {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyDataSourceFactory.class);
    public static Connection getConnection() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Connection conn = null;
        Properties props = new Properties();
        InputStream fis = loader.getResourceAsStream("db.properties");
        MysqlDataSource mysqlDS = null;
        try {
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
            mysqlDS.setDatabaseName(props.getProperty("MYSQL_DB_DB_NAME"));
            conn = mysqlDS.getConnection();
        } catch (Exception e) {
            logger.error("Exception thrown: " + e);
        }
        return conn;
    }
}
