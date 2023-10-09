package com.example.project_main;

import android.app.Activity;
import android.app.FragmentTransaction;
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
    UserRecommendAmount userRecAmount;
    private ArrayList<Integer> userDisease = new ArrayList<>();
    private ArrayList<Integer> userDiseaseListNum = new ArrayList<>();
    private ArrayList<NutritionDto> nutriInfo = new ArrayList<>();

    private TextView resetKcal;
    private TextView resetCarbohydrate;
    private TextView resetProtein;
    private TextView resetProvince;
    private String sex;
    private int age;
    private float height;
    private float weight;
    private String activity;

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

        if(userInfo.size() != 0) {
            sex = userInfo.get(0).getSex();
            age = userInfo.get(0).getAge();
            height = userInfo.get(0).getHeight();
            weight = userInfo.get(0).getWeight();
            activity = userInfo.get(0).getActivity();

            nutriInfo = userRecAmount.getUserRecommendAmount(userDiseaseListNum, age, height, weight, sex, activity);
            resetKcal.setText( String.valueOf( (int) nutriInfo.get(0).getKcal()) );
            resetCarbohydrate.setText( String.valueOf( (int) nutriInfo.get(0).getCarbohydrate()) );
            resetProtein.setText( String.valueOf( (int) nutriInfo.get(0).getProtein()) );
            resetProvince.setText( String.valueOf( (int) nutriInfo.get(0).getProvince()) );

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

}