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
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class init_setup4 extends Activity {


    Button btnPre4, btnNext4;

    private Spinner exerciseSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_setup4);

        btnPre4 = findViewById(R.id.btnPre4);
        btnNext4 = findViewById(R.id.btnNext4);
        exerciseSpinner = findViewById(R.id.exerciseSpinner);

        ArrayAdapter<CharSequence> exerciseAdapter = ArrayAdapter.createFromResource(this, R.array.exerciselist
                , android.R.layout.simple_spinner_item);
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(exerciseAdapter);

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String exerciseAtPosition = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnPre4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), init_setup3.class);
                startActivity(intent);
            }
        });

        btnNext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), init_setup5.class);
                startActivity(intent);
            }
        });


    }

}
