package com.example.foodspace.Model;

import android.graphics.Bitmap;

public class settingItem1 {
    private String title;
    private Integer bitmap;


    public settingItem1(String title, Integer bitmap) {
        this.title = title;
        this.bitmap = bitmap;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBitmap() {
        return bitmap;
    }

    public void setBitmap(Integer bitmap) {
        this.bitmap = bitmap;
    }
}
