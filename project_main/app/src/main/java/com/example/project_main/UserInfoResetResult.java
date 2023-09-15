package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserInfoResetResult extends Activity {

    private Button resetResultBtn;
    private Button allergyResetBtn;

    MyDatabaseHelper dbHelper;
    ArrayList<UserDto> userInfo = new ArrayList<>();

    private TextView resetKcal;
    private TextView resetCarbohydrate;
    private TextView resetProtein;
    private TextView resetProvince;
    private String sex;
    private int age;
    private float height;
    private float weight;
    private String activity;
    private double recommendKcal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_reset_result);

        dbHelper = new MyDatabaseHelper(getApplicationContext());
        userInfo = dbHelper.ExecuteQueryGetUserInfo();

        resetKcal = findViewById(R.id.resetKcal);
        resetCarbohydrate = findViewById(R.id.resetCarbohydrate);
        resetProtein = findViewById(R.id.resetProtein);
        resetProvince = findViewById(R.id.resetProvince);

        resetResultBtn = findViewById(R.id.resetResultBtn);
        allergyResetBtn = findViewById(R.id.setAllergyBtn);

        if(userInfo.size() != 0) {
            sex = userInfo.get(0).getSex();
            age = userInfo.get(0).getAge();
            height = userInfo.get(0).getHeight();
            weight = userInfo.get(0).getWeight();
            activity = userInfo.get(0).getActivity();

            recommendKcal = Harris_Benedict(age,weight,height,sex,activity);

            resetKcal.setText( String.format("%.0f",recommendKcal) );
            resetCarbohydrate.setText( String.format("%.0f",calCarbohydrate(recommendKcal)) );
            resetProtein.setText( String.format("%.0f",calProtein(recommendKcal)) );
            resetProvince.setText( String.format("%.0f",calProvince(recommendKcal)) );

        }

        resetResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        allergyResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserAllergyReset.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 칼로리 계산식 (통합 필요)
    double Harris_Benedict(int age, float weight, float height, String sex, String activity){
        double recommendCal = 0.0;

        if(sex.equals("male")) {
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