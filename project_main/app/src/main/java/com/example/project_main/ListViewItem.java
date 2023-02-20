package com.example.project_main;

public class ListViewItem {
    private int image;
    private String foodName;
    private int kcal;

    public ListViewItem(int image, String foodName, int kcal) {
        this.image = image;
        this.foodName = foodName;
        this.kcal = kcal;
    }

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
}
