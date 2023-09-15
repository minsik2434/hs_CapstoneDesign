package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

public class UserAllergyReset extends Activity {

    Integer[] integerAllergy = {R.id.checkMilk, R.id.checkFowl, R.id.checkShellfish, R.id.checkFish, R.id.checkNuts,
            R.id.checkBean, R.id.checkWheat, R.id.checkPeanut, R.id.checkMeat};
    CheckBox[] checkAllergy = new CheckBox[integerAllergy.length];

    Integer[] integerDisease = {R.id.diabetes,R.id.highBloodPressure,R.id.hyperlipidemia,R.id.obesity};
    CheckBox[] checkDisease = new CheckBox[integerDisease.length];

    Button btnCancel, btnSave;

    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_reset_allergy);

        dbHelper = new MyDatabaseHelper(this);

        btnCancel = findViewById(R.id.resetAllergyCancel);
        btnSave = findViewById(R.id.resetAllergySave);

        // 알러지
        for(int i=0; i<integerAllergy.length;i++){
            checkAllergy[i] = findViewById(integerAllergy[i]);
        }
        // 지병
        for(int i=0; i<integerDisease.length;i++){
            checkDisease[i] = findViewById(integerDisease[i]);
        }
        
        //닉네임 가져오기
        String nickname = dbHelper.getNickname();

        //체크된 값 가져오기

        // 취소 버튼을 눌렀을 때
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 저장 버튼을 눌렀을 때
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DB 알러지 초기화
                dbHelper.deleteAllRows("allergy");
                //DB 지병 초기화
                dbHelper.deleteAllRows("disease");
                //DB에 알러지 추가
                addAllergies();
                //DB에 지병 추가
                addDiseases();

                //사용자 알러지, 지병 테이블의 모든 행 삭제
                dbHelper.deleteAllRows("allergy_user");
                dbHelper.deleteAllRows("disease_user");

                //DB에 알러지 추가
                addUserAllergies(nickname);
                //DB에 지병 추가
                addUserDiseases(nickname);

                finish();
            }

        });
    }

    // 알러지를 테이블에 추가
    void addAllergies() {
        for (int i = 0; i < checkAllergy.length; i++) {
            dbHelper.addAllergies(i, checkAllergy[i].getText().toString());
        }
    }

    // 지병을 테이블에 추가
    void addDiseases() {
        for (int i = 0; i < checkDisease.length; i++) {
            dbHelper.addDiseases(i, checkDisease[i].getText().toString());
        }
    }


    // 사용자 알러지를 테이블에 추가
    void addUserAllergies(String nickname) {
        for (int i = 0; i < checkAllergy.length; i++) {
            if (checkAllergy[i].isChecked()) {
                dbHelper.addUserAllergies(nickname, i);
            }
        }
    }

    // 사용자 지병을 테이블에 추가
    void addUserDiseases(String nickname) {
        for (int i = 0; i < checkDisease.length; i++) {
            if (checkDisease[i].isChecked()) {
                dbHelper.addUserDiseases(nickname, i);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

}
