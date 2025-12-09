package porduct_management;

import porduct_management.DAO.DataRetriever;
import porduct_management.model.Category;
import porduct_management.model.Product;
import porduct_management.util.DBConnection;

import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        DataRetriever retriever = new DataRetriever(dbConnection);

        String productName = "iphone";
        String categoryName = "Mobile";

        Instant creationMin = Instant.parse("2020-01-01T00:00:00Z");
        Instant creationMax = Instant.parse("2025-12-31T23:59:59Z");

        var products = retriever.getProductsByCriteria(
                productName,
                categoryName,
                creationMin,
                creationMax
        );
        for (Product prod : products) {
            System.out.println(
                    "ID: " + prod.getId() +
                            ", Name: " + prod.getName() +
                            ", Category: " + prod.getCategory().getName() +
                            ", Created At: " + prod.getCreationDatetime()
            );
        }
    }
}
