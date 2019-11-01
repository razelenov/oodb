package company;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws JAXBException, IOException {
        Shop shop = new Shop();
        Users user1 = shop.addUser("mail@mail.to", "123456", "Ret Street, 56", false);
        Users user2 = shop.addUser("example@yandex.ru", "pass124", "Example 45, 34", false);
        Users user3 = shop.addUser("admin@shop.biz", "Pa$$w0rD!", "None", true);

        shop.addCategory("Телефоны");
        shop.addCategory("Принтеры");
        Item item1 = new Item("Nokia", 144.23, "Описание1");
        shop.addItem(item1.name, item1.price, item1.info, "Телефоны");
        shop.addItem("Sony Xperia Z1", 100.99, "Описание2", "Телефоны");
        shop.addItem("Canon Pixma 2B", 10003.34, "Описание3", "Принтеры");
        shop.addItem("SkyAir", 123.55, "Описание4", "Кондиционеры");

        user1.cart.addItem(item1);
        shop.makeOrder(shop, user1);

        /*shop.orders.get(0).getItems();

        Json json = new Json();
        json.saveJSON(shop);*/

        Database database = new Database();
        int i = database.saveToDB(shop);
        database.loadFromDB();
        database.searchInDB();
        database.sortByASC();

        /*XML xml = new XML();

        Shop shop1 = xml.loadXML();
        shop1.findUserByEmail("mail@mail.to");
        shop1.getSortedCategories();*/
    }
}
