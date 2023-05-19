package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class init_setup4 extends Activity {


    Button btnPre4, btnNext4;
    RadioGroup rGroup4;
    Integer[] rbtnId = {R.id.btnMin, R.id.btnLess, R.id.btnGeneral, R.id.btnActive, R.id.btnMax};
    RadioButton[] rBtn = new RadioButton[rbtnId.length];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_setup4);

        btnPre4 = findViewById(R.id.btnPre4);
        btnNext4 = findViewById(R.id.btnNext4);

        rGroup4 = findViewById(R.id.rGroup4);

        for(int i=0; i<rbtnId.length;i++){
            rBtn[i] = (RadioButton) findViewById(rbtnId[i]);
        }

        Bundle bnd = this.getIntent().getExtras();
        ArrayList<String> list = bnd.getStringArrayList("allergy");
        String age, weight, height, sex, nickname;
        Intent information = getIntent();
        age = information.getStringExtra("age");
        weight = information.getStringExtra("weight");
        height = information.getStringExtra("height");
        sex = information.getStringExtra("sex");
        nickname = information.getStringExtra("nickname");



        btnPre4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), init_setup3.class);
                startActivity(intent);
            }
        });

        btnNext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String min, less, general, active, max;
                min = rBtn[0].getText().toString();
                less = rBtn[1].getText().toString();
                general = rBtn[2].getText().toString();
                active = rBtn[3].getText().toString();
                max = rBtn[4].getText().toString();

                switch (rGroup4.getCheckedRadioButtonId()){
                    case R.id.btnMin:{
                        Intent intent = new Intent(getApplicationContext(), init_setup5.class);
                        bnd.putStringArrayList("allergy",list);
                        intent.putExtras(bnd);
                        intent.putExtra("age", age);
                        intent.putExtra("height", height);
                        intent.putExtra("weight", weight);
                        intent.putExtra("sex", sex);
                        intent.putExtra("exercise", min);
                        intent.putExtra("nickname", nickname);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.btnLess:{
                        Intent intent = new Intent(getApplicationContext(), init_setup5.class);
                        bnd.putStringArrayList("allergy",list);
                        intent.putExtras(bnd);
                        intent.putExtra("age", age);
                        intent.putExtra("height", height);
                        intent.putExtra("weight", weight);
                        intent.putExtra("sex", sex);
                        intent.putExtra("exercise", less);
                        intent.putExtra("nickname", nickname);
                        startActivity(intent);
                        break;
                    }
                    case R.id.btnGeneral:{
                        Intent intent = new Intent(getApplicationContext(), init_setup5.class);
                        bnd.putStringArrayList("allergy",list);
                        intent.putExtras(bnd);
                        intent.putExtra("age", age);
                        intent.putExtra("height", height);
                        intent.putExtra("weight", weight);
                        intent.putExtra("sex", sex);
                        intent.putExtra("exercise", general);
                        intent.putExtra("nickname", nickname);
                        startActivity(intent);
                        break;
                    }
                    case R.id.btnActive:{
                        Intent intent = new Intent(getApplicationContext(), init_setup5.class);
                        bnd.putStringArrayList("allergy",list);
                        intent.putExtras(bnd);
                        intent.putExtra("age", age);
                        intent.putExtra("height", height);
                        intent.putExtra("weight", weight);
                        intent.putExtra("sex", sex);
                        intent.putExtra("exercise", active);
                        intent.putExtra("nickname", nickname);
                        startActivity(intent);
                        break;
                    }
                    case R.id.btnMax:{
                        Intent intent = new Intent(getApplicationContext(), init_setup5.class);
                        bnd.putStringArrayList("allergy",list);
                        intent.putExtras(bnd);
                        intent.putExtra("age", age);
                        intent.putExtra("height", height);
                        intent.putExtra("weight", weight);
                        intent.putExtra("sex", sex);
                        intent.putExtra("exercise", max);
                        intent.putExtra("nickname", nickname);
                        startActivity(intent);
                        break;
                    }
                    default:
                        Toast.makeText(getApplicationContext(), "먼저 선택하세요.", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

}
