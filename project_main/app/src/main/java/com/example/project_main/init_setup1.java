package com.example.project_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class init_setup1 extends AppCompatActivity {

    Button btnNext1;
    EditText editNickname;

    MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_setup1);

        btnNext1 = findViewById(R.id.btnNext1);
        editNickname = findViewById(R.id.editNickname);

        String nickname = editNickname.getText().toString();

        dbHelper = new MyDatabaseHelper(this);


        //키를 눌렀을 때도 동작
        editNickname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String nickname = editNickname.getText().toString();
                    if (nickname == null || nickname.trim().isEmpty()) {
                        Toast.makeText(init_setup1.this, "닉네임을 입력하세요.", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), init_setup2.class);
                        intent.putExtra("nickname", nickname);
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });

        // 다음 버튼을 눌렀을 때
        // 닉네임을 입력하지 않으면 트스트로 알려주고 다음으로 넘어가지 않음
        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nickname = editNickname.getText().toString();
                if(nickname==null||nickname.length()==0){
                    Toast.makeText(init_setup1.this, "닉네임을 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), init_setup2.class);
                    intent.putExtra("nickname", nickname);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


}