package com.example.project_main;

public class UserDto {

    private String nickname;
    private int age;
    private String sex;
    private float height;
    private float weight;
    private String activity;
    private int recommendedKcal;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public float getRecommendedKcal() {
        return recommendedKcal;
    }

    public void setRecommendedKcal(int recommendedKcal) {
        this.recommendedKcal = recommendedKcal;
    }

}
