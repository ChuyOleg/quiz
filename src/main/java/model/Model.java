package model;

import model.db.Database;
import model.entities.Category;

import java.sql.SQLException;
import java.util.List;

public class Model {

    public void getThreeRandomCategories() throws SQLException {
        List<Category> three_random_categories = Database.getThreeRandomCategories();
        for (Category ct : three_random_categories) {
            System.out.println(ct);
        }
    }

}
