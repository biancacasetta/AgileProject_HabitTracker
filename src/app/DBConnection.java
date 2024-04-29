package app;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DBConnection {
    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        props.load(new FileInputStream("db.properties"));
        String url = props.getProperty("db.url");
        return DriverManager.getConnection(url);
    }
}
