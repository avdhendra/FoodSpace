package com.example.foodspace.Model;

import android.graphics.Bitmap;

public class PopularPlaces {
    private String title;
    private Bitmap place;

    public PopularPlaces(String title, Bitmap place) {
        this.title = title;
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getPlace() {
        return place;
    }

    public void setPlace(Bitmap place) {
        this.place = place;
    }
}
