package com.example.project_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MainFragment fragment_main;
    RecordFragment fragment_record;
    StatisticsTabFragment fragment_statistics;
    MypageFragment fragment_mypage;
    NutritionFirst fragment_nutri1;
    NutritionSecond fragment_nutri2;

    TimeState fragment_state_breakfast;
    //BottomAppBar barcodeBar;
    FloatingActionButton cameraBtn;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;

    MyDatabaseHelper dbHelper;
    SQLiteDatabase database;

    Bitmap bitmap;
    String foodImg;
    String foodName;
    int foodKcal;
    float foodCarbohydrate;
    float foodProtein;
    float foodProvince;
    String foodRaw_material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 앱의 진입점인 Mainactivity.java에서 실행 여부를 체크합니다.
        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("first_run", true);

//        // 테스트를 위해 값을 초기화
//
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("first_run", true);
//        editor.apply();

        // 앱을 처음 실행했을 때
        if (isFirstRun) {
            // init_setup1.java로 이동하는 코드
            Intent intent = new Intent(MainActivity.this, init_setup1.class);
            startActivity(intent);
        }


        //fragment 추가
        fragment_main = new MainFragment();
        fragment_record = new RecordFragment();
        fragment_statistics = new StatisticsTabFragment();
        fragment_mypage = new MypageFragment();
        //영양성분
        fragment_nutri1 = new NutritionFirst();
        fragment_nutri2 = new NutritionSecond();
        //아침리스트뷰
        fragment_state_breakfast = new TimeState();
        //메인 그래프

        //바코드
        cameraBtn = findViewById(R.id.mainCameraBtn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_record).addToBackStack(null).commit();
                Toast.makeText(getApplicationContext(),"눌림",Toast.LENGTH_SHORT).show();
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


        //2023-06-06
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        //바텀 바 터치 시 교체
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.bottomHome:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_main).commit();
                                return true;
                            case R.id.bottomRecord:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_record).commit();
                                return true;
                            case R.id.bottomStatics:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_statistics).commit();
                                return true;
                            case R.id.bottomMypage:
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

        ImageView foodImageView = findViewById(R.id.recordFoodImage);
        TextView searchedFoodName = findViewById(R.id.recordFoodName);
        TextView searchedFoodKcal = findViewById(R.id.recordFoodKcal);
        TextView searchedFoodNutriInfo = findViewById(R.id.recordFoodInfo);
        EditText raw_material_text = (EditText) findViewById(R.id.raw_material_text);

        if (result != null) {
            if (result.getContents() != null) {
                String barcode = result.getContents();
                Toast.makeText(this, "Scanned: " + barcode, Toast.LENGTH_LONG).show();
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);

                Thread uThread = new Thread(new Runnable() {
                    public void run() {
                        try {

                            API_function api_function = new API_function();

                            String dataBybarcode = api_function.dataSearchByBarcode(barcode);
                            System.out.println("dataBybarcode : " + dataBybarcode);
                            String[] PrdNmAndPrdNoData = api_function.itemsByBarcodeJsonParser(dataBybarcode);
                            System.out.println("PrdNmAndPrdNoData : " + PrdNmAndPrdNoData[0] + PrdNmAndPrdNoData[1]);
                            foodName = PrdNmAndPrdNoData[0];

                            String dataByPrdNo = api_function.dataSearchByPrdNo(PrdNmAndPrdNoData[1]);
                            System.out.println("dataByPrdNo : " + dataByPrdNo);
                            String[] PrdRawmtrlAndPrdImgData = api_function.itemsByPrdNoJsonParser(dataByPrdNo);
                            System.out.println("PrdRawmtrlAndPrdImgData : " + PrdRawmtrlAndPrdImgData[0] + PrdRawmtrlAndPrdImgData[1]);
                            foodRaw_material = PrdRawmtrlAndPrdImgData[0];
                            foodImg = PrdRawmtrlAndPrdImgData[1];



                            String sql_sentence = "select foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat from food_table where foodname = '"+foodName+"';";
                            ArrayList<RecodeSelectDto> recode_list = new ArrayList<RecodeSelectDto>();
                            recode_list = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);
                            foodKcal = (int) recode_list.get(0).getKcal();
                            foodCarbohydrate = recode_list.get(0).getCarbohydrate();
                            foodProtein = recode_list.get(0).getProtein();
                            foodProvince = recode_list.get(0).getProvince();


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (foodName != null)
                                    {
                                        //음식 탄단지 정보 저장
                                        String foodInfo = "탄수화물 " + foodCarbohydrate + "g" + " 단백질 " + foodProtein + "g" + " 지방 " + foodProvince + "g";
                                        //이미지 저장해야 함
                                        Glide.with(getApplicationContext()).load(foodImg).into(foodImageView);
                                        searchedFoodName.setText(foodName);
                                        searchedFoodKcal.setText(foodKcal+"Kcal");
                                        searchedFoodNutriInfo.setText(foodInfo);
                                        raw_material_text.setText(foodRaw_material);
                                    }
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                uThread.start();


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