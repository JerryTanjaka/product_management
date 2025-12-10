package product_management;

import product_management.DAO.DataRetriever;
import product_management.model.Category;
import product_management.util.DBConnection;

import java.sql.SQLException;
import java.time.Instant;

public class Main {

    public static void main(String[] args) throws SQLException {

        DataRetriever retriever = new DataRetriever(new DBConnection());

        // --- All Categories
        System.out.println("--- All Categories");
        retriever.getAllCategories().forEach(System.out::println);

        // --- Product List With Pages
        System.out.println("\n--- Product List With Pages");

        System.out.println("- 1");
        retriever.getProductList(1, 10).forEach(System.out::println);

        System.out.println("\n- 2");
        retriever.getProductList(1, 5).forEach(System.out::println);

        System.out.println("\n- 3");
        retriever.getProductList(1, 3).forEach(System.out::println);

        System.out.println("\n- 4");
        retriever.getProductList(2, 2).forEach(System.out::println);

        // --- Products by Criteria
        System.out.println("\n--- Products by Criteria");

        System.out.println("- 1");
        retriever.getProductsByCriteria("Dell", null, null, null)
                .forEach(System.out::println);

        System.out.println("\n- 2");
        retriever.getProductsByCriteria(null, "info", null, null)
                .forEach(System.out::println);

        System.out.println("\n- 3");
        retriever.getProductsByCriteria("iPhone", "mobile", null, null)
                .forEach(System.out::println);

        System.out.println("\n- 4");
        retriever.getProductsByCriteria(
                null,
                null,
                Instant.parse("2024-02-01T00:00:00Z"),
                Instant.parse("2024-03-01T00:00:00Z")
        ).forEach(System.out::println);

        System.out.println("\n- 5");
        retriever.getProductsByCriteria("Samsung", "bureau", null, null)
                .forEach(System.out::println);

        System.out.println("\n- 6");
        retriever.getProductsByCriteria("Sony", "informatique", null, null)
                .forEach(System.out::println);

        System.out.println("\n- 7");
        retriever.getProductsByCriteria(
                null,
                "audio",
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2024-12-01T00:00:00Z")
        ).forEach(System.out::println);

        System.out.println("\n- 8");
        retriever.getProductsByCriteria(null, null, null, null)
                .forEach(System.out::println);

        // --- Paginated version
        System.out.println("\n--- Products by Criteria (Paginated)");

        System.out.println("- 1");
        retriever.getProductsByCriteria(null, null, null, null, 1, 10)
                .forEach(System.out::println);

        System.out.println("\n- 2");
        retriever.getProductsByCriteria("Dell", null, null, null, 1, 5)
                .forEach(System.out::println);

        System.out.println("\n- 3");
        retriever.getProductsByCriteria(null, "informatique", null, null, 1, 10)
                .forEach(System.out::println);
    }
}
