package com.example.project_main;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecordFragment extends Fragment {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    Button barcodebtn, recordBtn, morningBtn, lunchBtn, dinnerBtn;
    TextView recordFoodName, recordFoodKcal, recordFoodInfo;

    boolean morningClicked = false;
    boolean lunchClicked = false;
    boolean dinnerClicked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);


        Button searchbtn = (Button) view.findViewById(R.id.searchbtn);


        barcodebtn = view.findViewById(R.id.barcodeBtn);
        recordBtn = view.findViewById(R.id.recordBtn);
        recordFoodName = view.findViewById(R.id.recordFoodName);
        recordFoodKcal = view.findViewById(R.id.recordFoodKcal);
        recordFoodInfo = view.findViewById(R.id.recordFoodInfo);

        morningBtn = view.findViewById(R.id.morningBtn);
        lunchBtn = view.findViewById(R.id.lunchBtn);
        dinnerBtn = view.findViewById(R.id.dinnerBtn);


        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());


        barcodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    startBarcodeScanner();
                } else {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                startActivity(intent);
            }
        });



        morningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(morningClicked == false){
                    morningClicked = true;
                    morningBtn.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                }
                else{
                    morningClicked = false;
                    morningBtn.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }
            }
        });

        lunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lunchClicked == false){
                    lunchClicked = true;
                    lunchBtn.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                }
                else{
                    lunchClicked = false;
                    lunchBtn.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }

            }
        });

        dinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dinnerClicked == false){
                    dinnerClicked = true;
                    dinnerBtn.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                }
                else{
                    dinnerClicked = false;
                    dinnerBtn.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }

            }
        });

        // 기록하기 버튼을 눌렀을 때
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = 0;
                if(morningClicked==true)
                    count+=1;
                if(lunchClicked==true)
                    count+=1;
                if(dinnerClicked==true)
                    count+=1;

                if(count == 0){
                    Toast.makeText(getActivity(),"아침 점심 저녁 중 하나를 선택하세요.",Toast.LENGTH_LONG).show();
                }
                else if(count != 1){
                    Toast.makeText(getActivity(),"아침 점심 저녁 중 하나만 선택하세요.",Toast.LENGTH_LONG).show();
                }
                else{
                    String nickname = dbHelper.getNickname();
                    String foodname = recordFoodName.getText().toString();
                    String date = getCurrentDateTime();
                    String time;
                    if(morningClicked == true){
                        time = morningBtn.getText().toString();
                    }
                    else if(lunchClicked == true){
                        time = lunchBtn.getText().toString();
                    }
                    else {
                        time = dinnerBtn.getText().toString();
                    }


//                    dbHelper.addIntake(nickname, foodname, date, time);

                     morningClicked = false;
                     lunchClicked = false;
                     dinnerClicked = false;
                     Intent intent = new Intent(getActivity(), MainActivity.class);
                     startActivity(intent);

                }

            }
        });

        return view;

    }

    private void startBarcodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(requireActivity());
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    // 시간을 가지고 오는 함수
    public String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDateTime = new Date();
        return dateFormat.format(currentDateTime);
    }



}