package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class init_setup5 extends Activity {

    TextView textCal;
    Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_setup5);

        textCal = findViewById(R.id.textKcal);
        btnStart = findViewById(R.id.btnStart);

        Bundle bnd = this.getIntent().getExtras();
        ArrayList<String> list = bnd.getStringArrayList("allergy");
        String age, weight, height, sex, nickname, exercise;
        Intent information = getIntent();
        age = information.getStringExtra("age");
        weight = information.getStringExtra("weight");
        height = information.getStringExtra("height");
        sex = information.getStringExtra("sex");
        nickname = information.getStringExtra("nickname");
        exercise = information.getStringExtra("exercise");

        textCal.setText(exercise);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



   }
}
