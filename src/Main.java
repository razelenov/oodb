public class Main {

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.addCategory("Phones");
        shop.addCategory("Computers");
        shop.getCategories();

        System.out.println();

        shop.addItemToCategory("Phones", "Sumsung", 9.49);
        shop.addItemToCategory("Phones", "Sumsung1", 19.49);
        shop.addItemToCategory("Phones", "Sumsung2", 9.49);
        shop.addItemToCategory("Phones2", "Sumsung2", 19.49);
        shop.addItemToCategory("Computers", "iPhone", 999.49);

        System.out.println();

        shop.getgetCategoriesAndItems();

        System.out.println();

        shop.addUser("admin", "admin", "Name");
        shop.addUser("admin", "admin", "Name");
        shop.addUser("admin1", "admin", "Name");
        shop.removeUser("admin1", "admin1");
        shop.removeUser("admin1", "admin");

        System.out.println();

        shop.getUsers();
    }
}
