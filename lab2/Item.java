package com.company;

public class Item {
    public String name;
    public Categories id_category;
    public Double price;
    public String info;

    public Item(String name, Double price, String info) {
        this.name = name;
        this.price = price;
        this.info = info;
    }

    public void setId_category(Categories id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public Categories getId_category() {
        return id_category;
    }

    public Double getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }
}
