package com.example.foodspace;

public class restaurantData {
    String title;
    Integer price;
    Integer image;

    public restaurantData(String title, Integer price, Integer image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
