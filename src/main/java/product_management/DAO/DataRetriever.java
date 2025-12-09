package product_management.DAO;

import product_management.model.Category;
import product_management.model.Product;
import product_management.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
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
        String sql = "SELECT id, name,product_id FROM product_category";
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

    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT p.id,p.name,p.creation_datetime,c.id AS category_id,c.name AS category_name,c.product_id
                FROM product p
                LEFT JOIN product_category c ON p.id = c.product_id
                ORDER BY p.id
                LIMIT ? OFFSET ?
                """;

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, size);
            statement.setInt(2, (page - 1) * size);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    Instant creationDatetime = rs.getTimestamp("creation_datetime").toInstant();

                    int categoryId = rs.getInt("category_id");
                    String categoryName = rs.getString("category_name");
                    int productId = rs.getInt("product_id");

                    Category category = new Category(categoryId, categoryName, productId);
                    Product product = new Product(id, name, creationDatetime, category);

                    products.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin, Instant creationMax
    ) {
        List<Product> products = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String baseSql = """
                SELECT p.id, p.name, p.creation_datetime,c.id AS category_id, c.name AS category_name, c.product_id
                FROM product p
                LEFT JOIN product_category c ON p.id = c.product_id
                """;

        if (productName != null) {
            conditions.add("p.name ILIKE ?");
            params.add("%" + productName + "%");
        }

        if (categoryName != null) {
            conditions.add("c.name ILIKE ?");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            conditions.add("p.creation_datetime >= ?");
            params.add(java.sql.Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            conditions.add("p.creation_datetime <= ?");
            params.add(java.sql.Timestamp.from(creationMax));
        }

        String finalSql = baseSql;
        if (!conditions.isEmpty()) {
            finalSql += " WHERE " + String.join(" AND ", conditions);
        }

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(finalSql)
        ) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Instant creationDatetime = rs.getTimestamp("creation_datetime").toInstant();

                int categoryId = rs.getInt("category_id");
                String catName = rs.getString("category_name");
                int productId = rs.getInt("product_id");

                Category category = new Category(categoryId, catName, productId);
                Product product = new Product(id, name, creationDatetime, category);

                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> getProductsByCriteria(
            String productName, String categoryName, Instant creationMin, Instant creationMax, int page, int size
    ) throws SQLException {
        List<Product> allFilteredProducts = getProductsByCriteria(productName, categoryName, creationMin, creationMax);
        return allFilteredProducts.stream().skip((long)size * (page - 1)).limit(size).toList();
    }
}
