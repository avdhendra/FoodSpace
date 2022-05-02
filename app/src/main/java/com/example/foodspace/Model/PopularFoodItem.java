package com.example.foodspace.Model;

import android.content.Context;
import android.graphics.Bitmap;

public class PopularFoodItem {
    private String title;
    private String bitmap;

public PopularFoodItem(){}
    public PopularFoodItem(String title, String bitmap) {
        this.title = title;
        this.bitmap = bitmap;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }
}
