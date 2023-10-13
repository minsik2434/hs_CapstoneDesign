package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class init_setup5 extends Activity {


    TextView textKcal, carbohydrateGram, proteinGram, fatGram;
    Button btnStart;

    SharedPreferences preferences;

    MyDatabaseHelper dbHelper;
    UserRecommendAmount userRecAmount;
    private ArrayList<Integer> userDisease = new ArrayList<>();
    private ArrayList<Integer> userDiseaseListNum = new ArrayList<>();
    private ArrayList<NutritionDto> nutriInfo = new ArrayList<>();

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

        //지병에 따른 권장량 가져오기
        dbHelper = new MyDatabaseHelper(this);
        userRecAmount = new UserRecommendAmount();
        userDisease = dbHelper.getUserDisease(); //사용자 지병

        //지병 없다면
        if (userDisease.size() == 0) {
            userDiseaseListNum.add(0);
        }
        //지병 있다면
        else {
            for (int i = 0; i < userDisease.size(); i++) {

                userDiseaseListNum.add(userDisease.get(i) + 1);
            }
        }

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

        nutriInfo = userRecAmount.getUserRecommendAmount(userDiseaseListNum, age, height, weight, sex, activity);

        double recommendKcal = nutriInfo.get(0).getKcal();

        double carbohydrate = nutriInfo.get(0).getCarbohydrate();
        double protein=nutriInfo.get(0).getProtein();
        double fat=nutriInfo.get(0).getProvince();

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
}
