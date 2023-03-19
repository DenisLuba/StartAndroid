package com.example.p0541_customadapter;

public class Product {

    private String name;
    private int price;
    private int image;
    private boolean box;

    public Product(String name, int price, int image, boolean box) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.box = box;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean inBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }
}

