package com.example.project_main;


import static android.app.Activity.RESULT_OK;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecordFragment extends Fragment {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;


    Button barcodebtn, recordBtn,searchbtn;
    TextView recordFoodName, recordFoodKcal, recordFoodInfo;
    TextView raw_mtrl;
    RadioGroup timeToEat_group;
    RadioButton morningBtn, lunchBtn, dinnerBtn;

    TextView searchedFoodName;
    TextView searchedFoodKcal;
    TextView searchedFoodNutriInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);




        searchedFoodName = view.findViewById(R.id.recordFoodName);
        searchedFoodKcal = view.findViewById(R.id.recordFoodKcal);
        searchedFoodNutriInfo = view.findViewById(R.id.recordFoodInfo);

        searchbtn = (Button) view.findViewById(R.id.searchbtn);
        barcodebtn = view.findViewById(R.id.barcodeBtn);
        recordBtn = view.findViewById(R.id.recordBtn);
        recordFoodName = view.findViewById(R.id.recordFoodName);
        recordFoodKcal = view.findViewById(R.id.recordFoodKcal);
        recordFoodInfo = view.findViewById(R.id.recordFoodInfo);
        raw_mtrl = view.findViewById(R.id.raw_material_text);
        timeToEat_group = view.findViewById(R.id.timeToEat_group);

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
                startActivityForResult(intent,0);
            }
        });


        // 기록하기 버튼을 눌렀을 때
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nickname = dbHelper.getNickname();
                String foodname = recordFoodName.getText().toString();
                String date = getCurrentDateTime();
                String time = getTimeStringFromRadioGroup();

                if(time.isEmpty()){
                    Toast.makeText(getActivity(), "아침, 점심, 저녁 중 하나를 선택하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                AlarmController alarmController = new AlarmController(getContext());
//                alarmController.cancelAlarm(002);
//                alarmController.setAlarm(001,0);

                dbHelper.addIntake(nickname, foodname, date, time);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);


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



    private String getTimeStringFromRadioGroup() {
        int checkedRadioButtonId = timeToEat_group.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.morningBtn) {
            return morningBtn.getText().toString();
        } else if (checkedRadioButtonId == R.id.lunchBtn) {
            return lunchBtn.getText().toString();
        } else if (checkedRadioButtonId == R.id.dinnerBtn) {
            return dinnerBtn.getText().toString();
        }

        return ""; // 기본 빈 시간 문자열로 설정합니다.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Handler handler = new Handler(Looper.getMainLooper());
        String target = "대두";
        if(resultCode == RESULT_OK) {
            String fn = data.getStringExtra("fname");
            String kcal = data.getStringExtra("kcal");
            String info = data.getStringExtra("foodinfo");
            String mtrl = data.getStringExtra("rawmtrl");
            SpannableString spannableString = new SpannableString(mtrl);
            int startIndex = mtrl.indexOf(target);
            if(startIndex != -1){
                int endIndex = startIndex + target.length();
                spannableString.setSpan(new ForegroundColorSpan(Color.RED),startIndex,endIndex,0);

            }
            recordFoodName.setText(fn);
            recordFoodKcal.setText(kcal);
            recordFoodInfo.setText(info);
            raw_mtrl.setText(spannableString);
        }
    }
}