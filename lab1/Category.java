import java.util.ArrayList;

public class Category {
    public String name;

    public Category(String name) {
        this.name = name;
    }

    public ArrayList<Item> items = new ArrayList<>();

    public boolean addItem(String info, Double cost) {
        try {
            items.add(new Item(info, cost));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getName() {
        return name;
    }
}
