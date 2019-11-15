package company;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement(name="shop")
public class Shop {
    public String name = "Example";
    public String title = "ExampleTitle";

    public List<Users> getUsers() {
        return users;
    }

    @XmlElement(name="users")
    public void setUsers(List<Users> users) {
        this.users = users;
    }

    List<Users> users = new ArrayList<>();

    public List<Categories> getCategories() {
        return categories;
    }
    @XmlElement(name="categories")
    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    List<Categories> categories = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }
    @XmlElement(name="items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    List<Item> items = new ArrayList<>();

    public List<Cart> getCarts() {
        return carts;
    }
    @XmlElement(name="carts")
    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    List<Cart> carts = new ArrayList<>();

    public List<Orders> getOrders() {
        return orders;
    }
    @XmlElement(name="orders")
    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    List<Orders> orders = new ArrayList<>();

    public void addCategory(String name) {
        System.out.println("Добавлена категория: " + name);
        categories.add(new Categories(name));
    }

    public boolean isItExists(String name) {
        for (int i = 0; i < this.categories.size(); i++) {
            if (categories.get(i).name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addItem(String name, Double price, String info, String category) {
        if (isItExists(category)) {
            items.add(new Item(name, price, info));
            Item item = items.get(items.size() - 1);
            System.out.println("Добавлен товар: " + item.name);
            for (int i = 0; i < this.categories.size(); i++) {
                if (this.categories.get(i).name.equals(category)) {
                    item.setId_category(this.categories.get(i));
                }
            }
        } else {
            addCategory(category);
            items.add(new Item(name, price, info));
            Item item = items.get(items.size() - 1);
            System.out.println("Добавлен товар: " + item.name);
            item.setId_category(this.categories.get(this.categories.size() - 1));
        }
    }

    public Users addUser(String email, String password, String address, boolean isAdmin) {
        Users user = new Users(email, password, address, isAdmin);
        users.add(user);
        Cart cart = user.cart;
        carts.add(cart);
        return user;
    }

    public void makeOrder(Shop shop, Users user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) {
                user.cart.makeOrder(shop, String.valueOf(new Date()), user);
            }
        }
    }

    public Users findUserByEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).email.equals(email)) {
                System.out.println("Пользователь с email: " + email + " найден! Адрес: " + users.get(i).address);
                return users.get(i);
            }
        }
        System.out.println("Пользователь с email: " + email + " не найден!");
        return null;
    }

    public void getSortedCategories() {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            out.add(categories.get(i).name);
        }
        System.out.println("\n~ Сортированный список: ");
        out.stream().sorted().forEach(s -> System.out.println(s));
        System.out.println("\n~ Несортированный список: ");
        out.stream().forEach(s -> System.out.println(s));
    }

}
