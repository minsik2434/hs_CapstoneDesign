package com.example.project_main;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private int image;
    private String foodName;
    private int kcal;


    public int getImage()
    {
        return this.image;
    }

    public String getFoodName()
    {
        return this.foodName;
    }

    public int getKcal()
    {
        return this.kcal;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

}
