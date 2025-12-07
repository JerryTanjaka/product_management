package porduct_management.DAO;

import porduct_management.model.Category;
import porduct_management.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private DBConnection dbConnection;

    public DataRetriever(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private Connection getConnection() {
        return dbConnection.getDBConnection();
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int productId = rs.getInt("product_id");

                Category category = new Category(id, name, productId);
                categories.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }
}
