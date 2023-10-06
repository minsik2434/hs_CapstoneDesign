package com.example.project_main;

import android.graphics.drawable.Drawable;

public class ListViewItem {

    private String foodName;
    private String foodKcal;
    private String foodInfo;

    ListViewItem(String name,String kcal, String info){
        this.foodName = name;
        this.foodKcal = kcal;
        this.foodInfo = info;
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
