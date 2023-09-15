package com.example.project_main;
import android.widget.Toast;

import java.util.ArrayList;

public class UserRecommendAmount {

    private int kcal=0;
    private float carbohydrate=0;
    private float protein=0;
    private float province=0;
    private float sugars=0;
    private float salt=0;
    private float cholesterol=0;
    private float saturated_fat=0;
    private float trans_fat=0;

    //비교
    public ArrayList<NutritionDto> getUserRecommendAmount(ArrayList<Integer> disease, int age, float height, float weight, String sex, String activity){
        ArrayList<NutritionDto> recommendNutriArray = new ArrayList<>();
        NutritionDto nutri = new NutritionDto();

        int userKcal=0;
        float userCarbohydrate=0;
        float userProtein=0;
        float userProvince=0;
        float userSugars=0;
        float userSalt=0;
        float userCholesterol=0;
        float userSaturated_fat=0;
        float userTrans_fat=0;

        //지병 개수만큼
        for(int i = 0; i < disease.size(); i++){
            calculateRecommendNutri(disease.get(i),age,height,weight,sex,activity);
            if(i==0){
                userKcal = kcal;
                userCarbohydrate = carbohydrate;
                userProtein = protein;
                userProvince = province;
                userSugars = sugars;
                userSalt = salt;
                userCholesterol = cholesterol;
                userSaturated_fat = saturated_fat;
                userTrans_fat = trans_fat;
            }

            userKcal = kcal < userKcal ? kcal : userKcal;
            userCarbohydrate = carbohydrate < userCarbohydrate ? carbohydrate : userCarbohydrate;
            userProtein = protein < userProtein ? protein : userProtein;
            userProvince = province < userProvince ? province : userProvince;
            userSugars = sugars < userSugars ? sugars : userSugars;
            userSalt = salt < userSalt ? salt : userSalt;
            userCholesterol = cholesterol < userCholesterol ? cholesterol : userCholesterol;
            userSaturated_fat = saturated_fat < userSaturated_fat ? saturated_fat : userSaturated_fat;
            userTrans_fat = trans_fat < userTrans_fat ? trans_fat : userTrans_fat;

        }
        nutri.setKcal(userKcal);
        nutri.setCarbohydrate(userCarbohydrate);
        nutri.setProtein(userProtein);
        nutri.setProvince(userProvince);
        nutri.setSugars(userSugars);
        nutri.setSalt(userSalt);
        nutri.setCholesterol(userCholesterol);
        nutri.setSaturated_fat(userSaturated_fat);
        nutri.setTrans_fat(userTrans_fat);

        recommendNutriArray.add(nutri);

        return recommendNutriArray;
    }


    //계산
    private void calculateRecommendNutri(int disease, int age, float height, float weight, String sex, String activity){

        double avgWeight = 0;

        switch(disease){
            //지병 없음
            case 0:
                kcal = (int) Harris_Benedict(age, weight, height, sex, activity);
                carbohydrate = (float) calCarbohydrate(kcal);
                protein = (float) calProtein(kcal);
                province = (float) calProvince(kcal);
                //당(성별)
                if(sex.equals("male")){
                    sugars = 36;
                }
                else if (sex.equals("female")){
                    sugars = 24;
                }
                salt = 2400;
                cholesterol = 300;
                saturated_fat = kcal * 0.1f;
                trans_fat = kcal * 0.01f;
                break;
            //당뇨
            case 1:
                if(sex.equals("male"))
                    avgWeight = (Math.pow(height/100,2) * 22);
                else if(sex.equals("female"))
                    avgWeight = (Math.pow(height/100,2) * 21);

                if(activity.equals("운동 안함") || activity.equals("운동 거의 안함"))
                    kcal = (int) avgWeight * 25;
                else if(activity.equals("보통") || activity.equals("운동 조금 함"))
                    kcal = (int) avgWeight * 30;
                else if(activity.equals("운동 많이 함"))
                    kcal = (int) avgWeight * 35;

                carbohydrate = kcal * 0.45f;
                protein = kcal * 0.1f;
                province = kcal * 0.2f;
                sugars = kcal * 0.1f;
                salt = 2300;
                cholesterol = 200;
                saturated_fat = kcal * 0.1f;
                trans_fat = kcal * 0.01f;
                break;
            //고혈압
            case 2:
                kcal = 1800;
                carbohydrate = kcal * 0.55f;
                protein = kcal * 0.18f;
                province = kcal * 0.27f;
                if (sex.equals("male"))
                    sugars = 36;
                else if (sex.equals("female"))
                    sugars = 25;
                salt = 2300;
                cholesterol = 150;
                saturated_fat = kcal * 0.06f;
                trans_fat = kcal * 0.01f;
                break;
            //고지혈증
            case 3:
                if(sex.equals("male"))
                    kcal = (int) (Math.pow(height/100,2) * 22 * 30);
                else if(sex.equals("female"))
                    kcal = (int) (Math.pow(height/100,2) * 21 * 30);
                carbohydrate = kcal * 0.5f;
                protein = kcal * 0.15f;
                province = kcal * 0.25f;
                sugars = kcal * 0.1f;
                salt = 3450;
                cholesterol = 200;
                saturated_fat = kcal * 0.07f;
                trans_fat = kcal * 0.1f;
                break;

            //비만
            case 4:
                kcal = (int) (weight * 30 - 500);
                carbohydrate = kcal * 0.47f;
                protein = weight * 1;
                province = kcal * 0.25f;
                sugars = kcal * 0.1f;
                salt = 2000;
                cholesterol = 200;
                saturated_fat = kcal * 0.06f;
                trans_fat = kcal * 0.01f;
                break;
        }
    }

    // 칼로리 계산식
    double Harris_Benedict(int age, float weight, float height, String sex, String activity){
        double recommendCal = 0.0;

        if(sex.equals("남성")) {
            switch (activity){
                case "운동 안함":
                    recommendCal = (66 + (13.7 * weight) + (5 * height) - (6.8 * age))*1.2;
                    break;
                case "운동 거의 안함":
                    recommendCal = (66 + (13.7 * weight) + (5 * height) - (6.8 * age))*1.375;
                    break;
                case "보통":
                    recommendCal = (66 + (13.7 * weight) + (5 * height) - (6.8 * age))*1.55;
                    break;
                case "운동 조금 함":
                    recommendCal = (66 + (13.7 * weight) + (5 * height) - (6.8 * age))*1.725;
                    break;
                case "운동 많이 함":
                    recommendCal = (66 + (13.7 * weight) + (5 * height) - (6.8 * age))*1.9;
                    break;
            }
        }
        else{
            switch (activity){
                case "운동 안함":
                    recommendCal = (655 + (13.7 * weight) + (1.8 * height) - (4.7 * age))*1.2;
                    break;
                case "운동 거의 안함":
                    recommendCal = (655 + (13.7 * weight) + (1.8 * height) - (4.7 * age))*1.375;
                    break;
                case "보통":
                    recommendCal = (655 + (13.7 * weight) + (1.8 * height) - (4.7 * age))*1.55;
                    break;
                case "운동 조금 함":
                    recommendCal = (655 + (13.7 * weight) + (1.8 * height) - (4.7 * age))*1.725;
                    break;
                case "운동 많이 함":
                    recommendCal = (655 + (13.7 * weight) + (1.8 * height) - (4.7 * age))*1.9;
                    break;
            }
        }

        return recommendCal;
    }

    //        일일 권장칼로리 = 60%탄수화물, 15%단백질, 25%지방
    //        1그램의 탄수화물은 4칼로리에 해당
    //        1그램의 단백질은 4칼로리에 해당
    //        1그램의 지방은 9칼리로리에 해당
    //탄수화물 계산
    double calCarbohydrate(double recommendCal){
        double carbohydrate=recommendCal*0.6/4;
        return carbohydrate;
    }
    //단백질 계산
    double calProtein(double recommendCal){
        double protein=recommendCal*0.15/4;
        return protein;
    }
    //지방 계산
    double calProvince(double recommendCal){
        double fat=recommendCal*0.25/9;
        return fat;
    }
}