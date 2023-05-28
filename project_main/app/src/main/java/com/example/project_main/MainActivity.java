package com.example.project_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {


    MainFragment fragment_main;
    RecordFragment fragment_record;
    StatisticsTabFragment fragment_statistics;
    MypageFragment fragment_mypage;
    NutritionFirst fragment_nutri1;
    NutritionSecond fragment_nutri2;

    StateBreakfast fragment_state_breakfast;
    BottomAppBar barcodeBar;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;

    private Button scanButton;


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
        fragment_mypage = new MypageFragment();
        //영양성분
        fragment_nutri1 = new NutritionFirst();
        fragment_nutri2 = new NutritionSecond();
        //아침리스트뷰
        fragment_state_breakfast = new StateBreakfast();




        //바코드
        barcodeBar = findViewById(R.id.bottomAppBar);
        barcodeBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    startBarcodeScanner();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });


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
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_mypage).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );

    }

    private void startBarcodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String barcode = result.getContents();
                Toast.makeText(this, "Scanned: " + barcode, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No barcode scanned", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startBarcodeScanner();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}