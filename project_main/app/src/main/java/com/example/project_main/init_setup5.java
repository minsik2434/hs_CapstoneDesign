package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class init_setup5 extends Activity {

    TextView textKcal, carbohydrateGram, proteinGram, fatGram;
    Button btnStart;


    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_setup5);

        textKcal = findViewById(R.id.textKcal);
        btnStart = findViewById(R.id.btnStart);
        carbohydrateGram = findViewById(R.id.carbohydrateGram);
        proteinGram = findViewById(R.id.proteinGram);
        fatGram = findViewById(R.id.fatGram);

        // SharedPreferences 객체 초기화
        preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickname");
        String ageString = intent.getStringExtra("age");
        String heightString = intent.getStringExtra("height");
        String weightString = intent.getStringExtra("weight");
        String sex = intent.getStringExtra("sex");
        String activity = intent.getStringExtra("activity");

        Integer age = Integer.valueOf(ageString);
        Integer height = Integer.valueOf(heightString);
        Integer weight = Integer.valueOf(weightString);

        double recommendKcal = Harris_Benedict(age, weight, height, sex, activity);

        double carbohydrate = calCarbohydrate(recommendKcal);
        double protein=calProtein(recommendKcal);
        double fat=calFat(recommendKcal);

        textKcal.setText(String.valueOf((int)recommendKcal));
        carbohydrateGram.setText(String.valueOf((int)carbohydrate));
        proteinGram.setText(String.valueOf((int)protein));
        fatGram.setText(String.valueOf((int)fat));

        int intRecommendedKcal = (int)recommendKcal;


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 값을 false로 변경하여 첫 실행이 아니라는 표시를 합니다.
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("first_run", false);
                editor.apply();

                MyDatabaseHelper dbHelper = new MyDatabaseHelper(getApplicationContext());

                dbHelper.addUser(nickname, age, sex, height, weight, activity,intRecommendedKcal);

                Intent intent = new Intent(init_setup5.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


    // 칼로리 계산식
    double Harris_Benedict(int age, int weight, int height, String sex, String activity){
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
    double calFat(double recommendCal){
        double fat=recommendCal*0.25/9;
        return fat;
    }


}
