import java.util.ArrayList;

public class Categories {
    public ArrayList<Category> categories = new ArrayList<>();

    public boolean addCategory(String name) {
        try {
            categories.add(new Category(name));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void getCategories() {
        System.out.println("Всего категорий: " + categories.size());
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ") " + categories.get(i).name);
        }
    }

    public void getgetCategoriesAndItems() {
        System.out.println("Всего категорий: " + categories.size());
        for (int i = 0; i < categories.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + categories.get(i).name);
            for (int j = 0; j < categories.get(i).items.size(); j++) {
                System.out.println("    " + (j + 1) + ". " + categories.get(i).items.get(j).info + ": $" + categories.get(i).items.get(j).cost);
            }
        }
    }
}
