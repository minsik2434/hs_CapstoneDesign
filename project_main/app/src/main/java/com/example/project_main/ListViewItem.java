package com.example.project_main;

import android.graphics.drawable.Drawable;

public class ListViewItem {

    private int foodImage;
    private String foodName;
    private String foodKcal;
    private String foodInfo;

    ListViewItem(int image, String name,String kcal, String info){
        this.foodImage = image;
        this.foodName = name;
        this.foodKcal = kcal;
        this.foodInfo = info;
    }

    public int getFoodImage()
    {
        return this.foodImage;
    }

    public String getFoodName()
    {
        return this.foodName;
    }

    public String getFoodKcal()
    {
        return this.foodKcal;
    }

    public String getFoodInfo() {
        return this.foodInfo;
    }

    public void setFoodImage(int image) {
        this.foodImage = image;
    }
    public void setFoodName(String name) {
        this.foodName = name;
    }
    public void setFoodKcal(String kcal) {
        this.foodKcal = kcal;
    }
    public void setFoodInfo(String info){
        this.foodInfo = info;
    }

}
