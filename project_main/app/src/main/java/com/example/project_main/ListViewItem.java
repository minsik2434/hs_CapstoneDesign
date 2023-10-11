package com.example.project_main;

import android.graphics.drawable.Drawable;

public class ListViewItem {





    private int icon;
    private String foodName;
    private String foodKcal;
    private String foodInfo;

    ListViewItem(int icon, String name, String kcal, String info){
        this.icon = icon;
        this.foodName = name;
        this.foodKcal = kcal;
        this.foodInfo = info;
    }

    public int getIcon() {
        return icon;
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

    public void setIcon(int icon) {
        this.icon = icon;
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

