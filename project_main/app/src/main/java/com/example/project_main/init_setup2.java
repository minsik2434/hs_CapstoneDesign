package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class init_setup2 extends Activity {

    Button btnPre2, btnNext2;
    EditText editAge, editHeight, editWeight;
    RadioGroup rGroup;
    RadioButton btnMan, btnWoman;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_setup2);

        btnPre2 = findViewById(R.id.btnPre2);
        btnNext2 = findViewById(R.id.btnNext2);

        editAge = findViewById(R.id.editAge);
        editHeight = findViewById(R.id.editHeight);
        editWeight = findViewById(R.id.editWeight);

        rGroup = findViewById(R.id.rGroup);
        btnMan = findViewById(R.id.btnMan);
        btnWoman = findViewById(R.id.btnWoman);

        String nickname;
        Intent information = getIntent();
        nickname = information.getStringExtra("nickname");




        btnPre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), init_setup1.class);

                startActivity(intent);
            }
        });

        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String age = editAge.getText().toString();
                String height = editHeight.getText().toString();
                String weight = editWeight.getText().toString();
                String man = btnMan.getText().toString();
                String woman = btnWoman.getText().toString();

                if(age.length()==0 || height.length()==0 || weight.length()==0){
                    Toast.makeText(init_setup2.this, "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if (btnMan.isChecked()==false&&btnWoman.isChecked()==false){
                    Toast.makeText(init_setup2.this, "성별을 골라주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(btnMan.isChecked()==true){
                        Intent intent = new Intent(getApplicationContext(), init_setup3.class);
                        intent.putExtra("age", age);
                        intent.putExtra("height", height);
                        intent.putExtra("weight", weight);
                        intent.putExtra("nickname", nickname);
                        intent.putExtra("sex", man);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(), init_setup3.class);
                        intent.putExtra("age", age);
                        intent.putExtra("height", height);
                        intent.putExtra("weight", weight);
                        intent.putExtra("nickname", nickname);
                        intent.putExtra("sex", woman);
                        startActivity(intent);
                        finish();
                    }


                }
            }
        });

    }

}
