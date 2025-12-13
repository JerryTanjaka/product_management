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
        System.out.println("--- All Categories ---");
        retriever.getAllCategories().forEach(System.out::println);

        // --- Product List With Pages
        System.out.println("\n--- Product List With Pages ---");

        System.out.println("\n- Page 1 : 10 produits par page, page 1");
        retriever.getProductList(1, 10).forEach(System.out::println);

        System.out.println("\n- Page 2 : 5 produits par page, page 1");
        retriever.getProductList(1, 5).forEach(System.out::println);

        System.out.println("\n- Page 3 : 3 produits par page, page 1");
        retriever.getProductList(1, 3).forEach(System.out::println);

        System.out.println("\n- Page 4 : 2 produits par page, page 2");
        retriever.getProductList(2, 2).forEach(System.out::println);

        // --- Products by Criteria
        System.out.println("\n--- Products by Criteria ---");

        System.out.println("\n- Test 1 : Filtrage nom='Dell', aucun filtre catégorie ou date");
        retriever.getProductsByCriteria("Dell", null, null, null)
                .forEach(System.out::println);

        System.out.println("\n- Test 2 : Filtrage catégorie contenant 'info', nom=null, dates=null");
        retriever.getProductsByCriteria(null, "info", null, null)
                .forEach(System.out::println);

        System.out.println("\n- Test 3 : Filtrage nom='iPhone', catégorie='mobile', dates=null");
        retriever.getProductsByCriteria("iPhone", "mobile", null, null)
                .forEach(System.out::println);

        System.out.println("\n- Test 4 : Filtrage par date entre 2024-02-01 et 2024-03-01, nom et catégorie=null");
        retriever.getProductsByCriteria(
                null,
                null,
                Instant.parse("2024-02-01T00:00:00Z"),
                Instant.parse("2024-03-01T00:00:00Z")
        ).forEach(System.out::println);

        System.out.println("\n- Test 5 : Filtrage nom='Samsung', catégorie='bureau', dates=null");
        retriever.getProductsByCriteria("Samsung", "bureau", null, null)
                .forEach(System.out::println);

        System.out.println("\n- Test 6 : Filtrage nom='Sony', catégorie='informatique', dates=null (devrait renvoyer rien)");
        retriever.getProductsByCriteria("Sony", "informatique", null, null)
                .forEach(System.out::println);

        System.out.println("\n- Test 7 : Filtrage catégorie='audio', date entre 2024-01-01 et 2024-12-01, nom=null");
        retriever.getProductsByCriteria(
                null,
                "audio",
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2024-12-01T00:00:00Z")
        ).forEach(System.out::println);

        System.out.println("\n- Test 8 : Aucun filtre, tous les produits");
        retriever.getProductsByCriteria(null, null, null, null)
                .forEach(System.out::println);

        // --- Paginated version
        System.out.println("\n--- Products by Criteria (Paginated) ---");

        System.out.println("\n- Paginated 1 : Aucun filtre, page 1, 10 produits par page");
        retriever.getProductsByCriteria(null, null, null, null, 1, 10)
                .forEach(System.out::println);

        System.out.println("\n- Paginated 2 : Filtrage nom='Dell', page 1, 5 produits par page");
        retriever.getProductsByCriteria("Dell", null, null, null, 1, 5)
                .forEach(System.out::println);

        System.out.println("\n- Paginated 3 : Filtrage catégorie='informatique', page 1, 10 produits par page");
        retriever.getProductsByCriteria(null, "informatique", null, null, 1, 10)
                .forEach(System.out::println);
    }
}
