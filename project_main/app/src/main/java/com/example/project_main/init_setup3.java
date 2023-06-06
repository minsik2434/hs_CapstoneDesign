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


    Integer[] integer = {R.id.checkFowl, R.id.checkMilk, R.id.checkBuckwheat, R.id.checkPeanut, R.id.checkBean,
            R.id.checkWheat, R.id.checkMackerel, R.id.checkcrab, R.id.checkShrimp, R.id.checkPork, R.id.checkPeach,
            R.id.checkTomato, R.id.checkSulfurous, R.id.checkWalnut, R.id.checkChicken, R.id.checkBeef, R.id.checkSquid,
            R.id.checkClam};
    CheckBox[] check = new CheckBox[integer.length];
    ArrayList<String> list = new ArrayList<>();

    Button btnNext3, btnPre3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_setup3);

        btnNext3 = findViewById(R.id.btnNext3);
        btnPre3 = findViewById(R.id.btnPre3);

        for(int i=0; i<integer.length;i++){
            check[i] = findViewById(integer[i]);
        }

        // 이전 버튼을 눌렀을 때
        btnPre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), init_setup2.class);

                startActivity(intent);
            }
        });

        // 다음 버튼을 눌렀을 때
        btnNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), init_setup4.class);
                startActivity(intent);
            }

        });


    }
}
