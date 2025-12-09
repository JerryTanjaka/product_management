package porduct_management.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
     final String URL = "jdbc:postgresql://localhost:5432/product_management_db";
     final String USER = "product_manager_user";
     final String PASSWORD = "123456";
    public  Connection getDBConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
