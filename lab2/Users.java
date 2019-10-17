package com.company;

public class Users {
    public String email;
    public String password;
    public String address;
    public boolean isAdmin;
    public Cart cart;

    public Users(String email, String password, String address, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.isAdmin = isAdmin;
        this.cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void addItem(Item item) {
        cart.addItem(item);
    }
}
