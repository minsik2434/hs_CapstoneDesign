package com.example.project_main;

public class RecodeSelectDto {

    private String foodImg;
    private String foodName;
    private int foodKcal;
    private float foodCarbohydrate;
    private float foodProtein;
    private float foodProvince;
    private String raw_material;


    public String getFoodImage()
    {
        return this.foodImg;
    }

    public String getFoodName()
    {
        return this.foodName;
    }

    public int getFoodKcal()
    {
        return this.foodKcal;
    }

    public float getFoodCarbohydrate() {
        return this.foodCarbohydrate;
    }

    public float getFoodProtein() { return this.foodProtein; }

    public float getFoodProvince() {
        return this.foodProvince;
    }

    public String getRaw_material() {
        return this.raw_material;
    }

    public void setFoodImage(String imageUrl) { this.foodImg = imageUrl; }
    public void setFoodName(String name) {
        this.foodName = name;
    }
    public void setFoodKcal(int kcal) {
        this.foodKcal = kcal;
    }
    public void setFoodCarbohydrate(float Carbohydrate){
        this.foodCarbohydrate = Carbohydrate;
    }
    public void setFoodProtein(float Protein){
        this.foodProtein = Protein;
    }
    public void setFoodProvince(float Province){
        this.foodProvince = Province;
    }
    public void setRaw_material(String material) { this.raw_material = material; }
}
