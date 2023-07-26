package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserInfoReset extends Activity {

    private EditText userName;
    private EditText userHeight;
    private EditText userWeight;
    private EditText userAge;
    private RadioGroup userSex;
    private Spinner userActivity;
    private String sex;
    private String activity;
    private int recommendKcal;

    Button resetCancel;
    Button resetSave;

    MyDatabaseHelper dbHelper;
    ArrayList<UserDto> userInfo = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_reset);

        dbHelper = new MyDatabaseHelper(getApplicationContext());
        userInfo = dbHelper.ExecuteQueryGetUserInfo();

        userName = findViewById(R.id.resetName);
        userHeight = findViewById(R.id.resetHeight);
        userWeight = findViewById(R.id.resetWeight);
        userAge = findViewById(R.id.resetAge);
        userSex = findViewById(R.id.resetSexGroup);
        userActivity = findViewById(R.id.resetActivity);

        resetCancel = findViewById(R.id.resetCancel);
        resetSave = findViewById(R.id.resetSave);

        if(userInfo.size() != 0) {
            userName.setText(userInfo.get(0).getNickname());
            userHeight.setText(userInfo.get(0).getHeight()+"");
            userWeight.setText(userInfo.get(0).getWeight()+"");
            userAge.setText(userInfo.get(0).getAge()+"");
            sex = userInfo.get(0).getSex();
            activity = userInfo.get(0).getActivity();
        }

        switch(sex) {
            case "male":
                userSex.check(R.id.resetMale);
                break;
            case "female":
                userSex.check(R.id.resetFemale);
                break;
            default:
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<CharSequence> exerciseAdapter = ArrayAdapter.createFromResource(this, R.array.exerciselist
                , android.R.layout.simple_spinner_item);
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userActivity.setAdapter(exerciseAdapter);

        switch(activity){
            case "운동 안함":
                userActivity.setSelection(0);
                break;
            case "운동 거의 안함":
                userActivity.setSelection(1);
                break;
            case "보통":
                userActivity.setSelection(2);
                break;
            case "운동 조금 함":
                userActivity.setSelection(3);
                break;
            case "운동 많이 함":
                userActivity.setSelection(4);
                break;
        }

        // 버튼 이벤트
        resetCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resetSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String afterName = userName.getText().toString();
                float afterHeight = Float.parseFloat(userHeight.getText().toString());
                float afterWeight = Float.parseFloat(userWeight.getText().toString());
                int afterAge = Integer.parseInt(userAge.getText().toString());
                RadioButton selectedSex = findViewById(userSex.getCheckedRadioButtonId());
                String afterSex = selectedSex.getText().toString();
                activity = userActivity.getSelectedItem().toString();
                recommendKcal = (int) Harris_Benedict(afterAge,afterWeight,afterHeight,afterSex, activity);

                dbHelper.resetUserInfo(afterName,afterAge,afterSex,afterHeight,afterWeight, activity, recommendKcal);

                Intent intent = new Intent(getApplicationContext(), UserInfoResetResult.class);
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

}