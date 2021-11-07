package com.example.foodspace;

import android.content.Context;
import android.graphics.Bitmap;

public class PopularFoodItem {
    private String title;
    private Bitmap bitmap;


    public PopularFoodItem(String title, Bitmap bitmap) {
        this.title = title;
        this.bitmap = bitmap;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
