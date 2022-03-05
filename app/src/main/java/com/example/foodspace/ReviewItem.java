package com.example.foodspace;

public class ReviewItem {
    String name;
    Integer image;
    Float rate;
    String Message;

    public ReviewItem(String name, Integer image, Float rate, String message) {
        this.name = name;
        this.image = image;
        this.rate = rate;
        Message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
