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
import java.util.List;

public class init_setup5 extends Activity {

    TextView textCal;
    Button btnStart;

    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_setup5);

        textCal = findViewById(R.id.textKcal);
        btnStart = findViewById(R.id.btnStart);

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



        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 값을 false로 변경하여 첫 실행이 아니라는 표시를 합니다.
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("first_run", false);
                editor.apply();

                MyDatabaseHelper dbHelper = new MyDatabaseHelper(getApplicationContext());

                dbHelper.addUser(nickname, age, sex, height, weight, activity);

                Intent intent = new Intent(init_setup5.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
   }
}