package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class init_setup3 extends Activity {

    Integer[] integerAllergy = {R.id.checkMilk, R.id.checkFowl, R.id.checkShellfish, R.id.checkFish, R.id.checkNuts,
            R.id.checkBean, R.id.checkWheat, R.id.checkPeanut, R.id.checkMeat};
    CheckBox[] checkAllergy = new CheckBox[integerAllergy.length];

    Integer[] integerDisease = {R.id.diabetes,R.id.highBloodPressure,R.id.hyperlipidemia,R.id.obesity};
    CheckBox[] checkDisease = new CheckBox[integerDisease.length];

    Button btnNext3, btnPre3;

    MyDatabaseHelper allergyDB;
    MyDatabaseHelper diseaseDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_setup3);

        allergyDB = new MyDatabaseHelper(this);
        diseaseDB = new MyDatabaseHelper(this);

        btnNext3 = findViewById(R.id.btnNext3);
        btnPre3 = findViewById(R.id.btnPre3);

        // 알러지
        for(int i=0; i<integerAllergy.length;i++){
            checkAllergy[i] = findViewById(integerAllergy[i]);
        }
        // 지병
        for(int i=0; i<integerDisease.length;i++){
            checkDisease[i] = findViewById(integerDisease[i]);
        }

        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickname");
        String age = intent.getStringExtra("age");
        String height = intent.getStringExtra("height");
        String weight = intent.getStringExtra("weight");
        String sex = intent.getStringExtra("sex");

        // 이전 버튼을 눌렀을 때
        btnPre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DB 알러지 초기화
                allergyDB.deleteAllRows("allergy");
                //DB 지병 초기화
                diseaseDB.deleteAllRows("disease");
                //DB에 알러지 추가
                addAllergies();
                //DB에 지병 추가
                addDiseases();
                //DB에 알러지 추가
                addUserAllergies(nickname);
                //DB에 지병 추가
                addUserDiseases(nickname);

                Intent intent = new Intent(getApplicationContext(), init_setup2.class);
                intent.putExtra("nickname", nickname);
                intent.putExtra("age", age);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("sex", sex);
                startActivity(intent);
                finish();
            }
        });

        // 다음 버튼을 눌렀을 때
        btnNext3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //DB 알러지 초기화
                allergyDB.deleteAllRows("allergy");
                //DB 지병 초기화
                diseaseDB.deleteAllRows("disease");
                //DB에 알러지 추가
                addAllergies();
                //DB에 지병 추가
                addDiseases();

                //DB에 알러지 추가
                addUserAllergies(nickname);
                //DB에 지병 추가
                addUserDiseases(nickname);
                Intent intent = new Intent(getApplicationContext(), init_setup4.class);
                intent.putExtra("nickname", nickname);
                intent.putExtra("age", age);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("sex", sex);
                startActivity(intent);
                finish();
            }

        });

    }

    // 알러지를 테이블에 추가
    void addAllergies() {
        for (int i = 0; i < checkAllergy.length; i++) {
            allergyDB.addAllergies(i, checkAllergy[i].getText().toString());
        }
    }

    // 지병을 테이블에 추가
    void addDiseases() {
        for (int i = 0; i < checkDisease.length; i++) {
            diseaseDB.addDiseases(i, checkDisease[i].getText().toString());
        }
    }

    // 사용자 알러지를 테이블에 추가
    void addUserAllergies(String nickname) {
        for (int i = 0; i < checkAllergy.length; i++) {
            if (checkAllergy[i].isChecked()) {
                allergyDB.addUserAllergies(nickname, i);
            }
        }
    }

    // 사용자 지병을 테이블에 추가
    void addUserDiseases(String nickname) {
        for (int i = 0; i < checkDisease.length; i++) {
            if (checkDisease[i].isChecked()) {
                diseaseDB.addUserDiseases(nickname, i);
            }
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

}
