package com.example.project_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.utils.Utils;

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

    int foodImg=0;
    String foodName;
    int foodKcal;
    String foodInfo;
    float foodCarbohydrate = 0.0f;
    float foodProtein = 0.0f;
    float foodProvince = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 앱의 진입점인 Mainactivity.java에서 실행 여부를 체크합니다.
        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("first_run", true);

//        // 테스트를 위해 값을 초기화
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("first_run", true);
        editor.apply();

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

        ImageView searchedFoodImg = findViewById(R.id.recordFoodImg);
        TextView searchedFoodName = findViewById(R.id.recordFoodName);
        TextView searchedFoodKcal = findViewById(R.id.recordFoodKcal);
        TextView searchedFoodNutriInfo = findViewById(R.id.recordFoodInfo);
        EditText raw_material_text = (EditText) findViewById(R.id.raw_material_text);

        if (result != null) {
            if (result.getContents() != null) {
                String barcode = result.getContents();
                Toast.makeText(this, "Scanned: " + barcode, Toast.LENGTH_LONG).show();

//                try
//                {
//                    //DB Connect
//                    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
//                    RecodeSelectDto recode_list = new RecodeSelectDto();
//                    recode_list = dbHelper.executeQuerySearchFoodByBarcode(barcode);
//                    String foodImg = recode_list.getFoodImage();
//                    String foodName = recode_list.getFoodName();
//                    int foodKcal = recode_list.getFoodKcal();
//                    float foodCarbohydrate = recode_list.getFoodCarbohydrate();
//                    float foodProtein = recode_list.getFoodProtein();
//                    float foodProvince = recode_list.getFoodProvince();
//                    String foodRaw_material = recode_list.getRaw_material();
//
//                    if (foodName != null)
//                    {
//                        //음식 탄단지 정보 저장
//                        String foodInfo = "탄수화물 " + foodCarbohydrate + "g" + " 단백질 " + foodProtein + "g" + " 지방 " + foodProvince + "g";
//                        //이미지 저장해야 함
//                        searchedFoodName.setText(foodName);
//                        searchedFoodKcal.setText(foodKcal+"Kcal");
//                        searchedFoodNutriInfo.setText(foodInfo);
//                        raw_material_text.setText(foodRaw_material);
//                        Toast.makeText(this, "음식 정보를 불러왔습니다!", Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(this, "음식 정보가 없습니다!", Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.makeText(this, "음식 정보를 불러왔습니다!", Toast.LENGTH_SHORT).show();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(this, "An error occured!", Toast.LENGTH_SHORT).show();
//                }

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

        private void executeQuerySearchByBarcode(String barcodeNum){
        Cursor cursor = database.rawQuery("select food_image, foodname, kcal, carbohydrate, protein, province\n" +
                "from food_table, nutrition\n" +
                "where food_table.foodID = nutrition.foodID\n" +
                "and barcode = '"+barcodeNum+"';",null);
        int recordCount = cursor.getCount();
        for (int i = 0; i < recordCount; i++){
            cursor.moveToNext();
            foodImg = cursor.getInt(0);
            foodName = cursor.getString(1);
            foodKcal = cursor.getInt(2);
            foodCarbohydrate = cursor.getFloat(3);
            foodProtein = cursor.getFloat(4);
            foodProvince = cursor.getFloat(5);
        }
        cursor.close();
    }

}