package porduct_management;

import porduct_management.DAO.DataRetriever;
import porduct_management.model.Category;
import porduct_management.util.DBConnection;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        DataRetriever retriever = new DataRetriever(dbConnection);
        List<Category> categories = retriever.getAllCategories();
        for (Category cat : categories) {
            System.out.println("ID: " + cat.getId() + ", Name: " + cat.getName() + ", ProductID: " + cat.getProductId());
        }
    }
}
