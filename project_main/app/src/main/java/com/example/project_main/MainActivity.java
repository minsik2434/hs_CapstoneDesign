package com.example.project_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TabHost;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    MainFragment fragment_main;
    RecordFragment fragment_record;
    StaticsFragment fragment_statics;
    PedometerFragment fragment_pedometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(getApplicationContext(), init_setup1.class);
//        startActivity(intent);
        //fragment 추가
        fragment_main = new MainFragment();
        fragment_record = new RecordFragment();
        fragment_statics = new StaticsFragment();
        fragment_pedometer = new PedometerFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_main).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

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
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_statics).commit();
                                return true;
                            case R.id.pedometer:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_pedometer).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );



    }
}