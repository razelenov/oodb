package com.company;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    List<Item> items = new ArrayList<>();

    public void makeOrder(Shop shop, String time, Users user) {
        Orders orders = new Orders(time, user);
        orders.setItems(items);
        shop.orders.add(orders);
        items.clear();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void getItems() {
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).name + " ~ $" + items.get(i).price);
        }
    }
}
