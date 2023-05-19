package com.example.project_main;

public class ListItem {
    private String name;
    private String kcal;
    private String info;
    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    ListItem(String name,String kcal, String info){
        this.name = name;
        this.kcal = kcal;
        this.info = info;
    }

}
