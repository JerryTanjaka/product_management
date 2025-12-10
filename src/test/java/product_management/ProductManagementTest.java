package product_management;
import org.junit.jupiter.api.Test;
import product_management.DAO.DataRetriever;
import product_management.model.Category;
import product_management.model.Product;
import product_management.util.DBConnection;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductManagementTest {

    private final DataRetriever retriever = new DataRetriever(new DBConnection());

    @Test
    void testGetAllCategories() {
        List<Category> categories = retriever.getAllCategories();

        assertEquals(7, categories.size(), "Should return 7 categories");

        Category first = categories.get(0);
        assertEquals(1, first.getId());
        assertEquals("Informatique", first.getName());
        assertEquals(1, first.getProductId());
    }

    @Test
    void testGetProductList() {
        List<Product> products = retriever.getProductList(1, 10);

        assertEquals(7, products.size(), "Should return 7 rows due to multiple categories");
    }

    @Test
    void testGetProductsByCriteria() {
        List<Product> dell = retriever.getProductsByCriteria("Dell", null, null, null);

        assertEquals(1, dell.size(), "Should return only 1 Dell product");
        assertEquals("Laptop Dell XPS", dell.get(0).getName());
    }

    @Test
    void testGetProductsByCriteriaCategory() {
        List<Product> phone = retriever.getProductsByCriteria(null, "Téléphonie", null, null);

        assertEquals(1, phone.size(), "iPhone 13 should be the only one in Téléphonie");
        assertEquals("iPhone 13", phone.get(0).getName());
    }

    @Test
    void testGetProductsByDateRange() {
        Instant min = Instant.parse("2024-02-01T00:00:00Z");
        Instant max = Instant.parse("2024-03-01T00:00:00Z");

        List<Product> results = retriever.getProductsByCriteria(null, null, min, max);

        assertEquals(3, results.size(), "Should return 3 rows because iPhone has 2 categories");
    }

}
