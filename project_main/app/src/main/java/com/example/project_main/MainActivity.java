package com.example.project_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    MainFragment fragment_main;
    RecordFragment fragment_record;
    StatisticsTabFragment fragment_statistics;
    PedometerFragment fragment_pedometer;
    NutritionFirst fragment_nutri1;
    NutritionSecond fragment_nutri2;
    StateBreakfast fragment_state_breakfast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(getApplicationContext(), init_setup1.class);
//        startActivity(intent);

        //fragment 추가
        fragment_main = new MainFragment();
        fragment_record = new RecordFragment();
        fragment_statistics = new StatisticsTabFragment();
        fragment_pedometer = new PedometerFragment();
        //영양성분
        fragment_nutri1 = new NutritionFirst();
        fragment_nutri2 = new NutritionSecond();
        //아침리스트뷰
        fragment_state_breakfast = new StateBreakfast();




        //메인화면에 fragment_main 프래그먼트 교체
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_main).commit();

        //영양소1 화면교체
        getSupportFragmentManager().beginTransaction().replace(R.id.nutri_content, fragment_nutri1).commit();

        //아침 리스트뷰 프래그먼트
        getSupportFragmentManager().beginTransaction().replace(R.id.time_state_frame, fragment_state_breakfast).commit();



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        //바텀 바 터치 시 교체
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.home:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_main).commit();
                                return true;
                            case R.id.record:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_record).commit();
                                return true;
                            case R.id.statics:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_statistics).commit();
                                return true;
                            case R.id.mypage:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_pedometer).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );


    }
}