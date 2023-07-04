package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class init_setup4 extends Activity {


    Button btnPre4, btnNext4;
    TextView spinnerText;

    private Spinner exerciseSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_setup4);

        btnPre4 = findViewById(R.id.btnPre4);
        btnNext4 = findViewById(R.id.btnNext4);
        spinnerText = findViewById(R.id.spinnerText);
        exerciseSpinner = findViewById(R.id.exerciseSpinner);


        ArrayAdapter<CharSequence> exerciseAdapter = ArrayAdapter.createFromResource(this, R.array.exerciselist
                , android.R.layout.simple_spinner_item);
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(exerciseAdapter);

        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickname");
        String age = intent.getStringExtra("age");
        String height = intent.getStringExtra("height");
        String weight = intent.getStringExtra("weight");
        String sex = intent.getStringExtra("sex");

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String exerciseAtPosition = parent.getItemAtPosition(position).toString();
                if (exerciseAtPosition.equals("운동 안함")) {
                    spinnerText.setText("운동 안함이 선택되었습니다.");
                } else if (exerciseAtPosition.equals("운동 거의 안함")) {
                    spinnerText.setText("운동 거의 안함이 선택되었습니다.");
                } else if (exerciseAtPosition.equals("보통")) {
                    spinnerText.setText("보통이 선택되었습니다.");
                } else if (exerciseAtPosition.equals("운동 조금 함")) {
                    spinnerText.setText("운동 조금 함이 선택되었습니다.");
                } else if (exerciseAtPosition.equals("운동 많이 함")) {
                    spinnerText.setText("운동 많이 함이 선택되었습니다.");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerText.setText("아무 것도 선택되지 않았습니다.");
            }
        });


        btnPre4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activity = exerciseSpinner.getSelectedItem().toString();

                Intent intent = new Intent(getApplicationContext(), init_setup3.class);
                intent.putExtra("nickname", nickname);
                intent.putExtra("age", age);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("sex", sex);
                intent.putExtra("activity", activity);
                startActivity(intent);
                finish();
            }
        });

        btnNext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activity = exerciseSpinner.getSelectedItem().toString();

                Intent intent = new Intent(getApplicationContext(), init_setup5.class);

                intent.putExtra("nickname", nickname);
                intent.putExtra("age", age);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("sex", sex);
                intent.putExtra("activity", activity);
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
