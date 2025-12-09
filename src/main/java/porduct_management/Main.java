package porduct_management;

import porduct_management.DAO.DataRetriever;
import porduct_management.model.Product;
import porduct_management.model.Category;
import porduct_management.util.DBConnection;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        DBConnection db = new DBConnection();
        DataRetriever retriever = new DataRetriever(db);
 // Test getAllCategories()
        System.out.println("=== getAllCategories() ===");
        List<Category> categories = retriever.getAllCategories();
        categories.forEach(System.out::println);
        System.out.println();


        // 2) Test getProductList(page, size)
        System.out.println("=== getProductList() ===");

        List<int[]> paginationTests = List.of(
                new int[]{1, 10},
                new int[]{1, 5},
                new int[]{1, 3},
                new int[]{2, 2}
        );

        for (int[] test : paginationTests) {
            int page = test[0];
            int size = test[1];

            System.out.println("-- page=" + page + " size=" + size + " --");
            retriever.getProductList(page, size)
                    .forEach(System.out::println);
            System.out.println();
        }


        // 3) Test getProductsByCriteria()
        System.out.println("=== getProductsByCriteria() ===");

        record Criteria(String product, String category, Instant min, Instant max) {}

        List<Criteria> criteriaTests = List.of(
                new Criteria("Dell", null, null, null),
                new Criteria(null, "info", null, null),
                new Criteria("iPhone", "mobile", null, null),
                new Criteria(null, null,
                        Instant.parse("2024-02-01T00:00:00Z"),
                        Instant.parse("2024-03-01T00:00:00Z")),
                new Criteria("Samsung", "bureau", null, null),
                new Criteria("Sony", "informatique", null, null),
                new Criteria(null, "audio",
                        Instant.parse("2024-01-01T00:00:00Z"),
                        Instant.parse("2024-12-01T00:00:00Z")),
                new Criteria(null, null, null, null)
        );

        for (Criteria c : criteriaTests) {
            System.out.println("-- product=" + c.product() + ", category=" + c.category() +
                    ", min=" + c.min() + ", max=" + c.max() + " --");

            retriever.getProductsByCriteria(c.product(), c.category(), c.min(), c.max())
                    .forEach(System.out::println);

            System.out.println();
        }


        // 4) Version PAGINÉE
        System.out.println("=== getProductsByCriteria() paginé ===");

        record PaginatedCriteria(String product, String category, Instant min, Instant max, int page, int size) {}

        List<PaginatedCriteria> paginatedTests = List.of(
                new PaginatedCriteria(null, null, null, null, 1, 10),
                new PaginatedCriteria("Dell", null, null, null, 1, 5),
                new PaginatedCriteria(null, "informatique", null, null, 1, 10)
        );

        for (PaginatedCriteria pc : paginatedTests) {
            System.out.println("-- PAGINÉ product=" + pc.product() + ", category=" + pc.category()
                    + ", page=" + pc.page() + ", size=" + pc.size() + " --");

            retriever.getProductsByCriteria(
                    pc.product(),
                    pc.category(),
                    pc.min(),
                    pc.max(),
                    pc.page(),
                    pc.size()
            ).forEach(System.out::println);

            System.out.println();
        }
    }
}
