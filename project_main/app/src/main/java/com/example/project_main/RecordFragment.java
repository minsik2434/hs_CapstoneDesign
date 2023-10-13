package com.example.project_main;


import static android.app.Activity.RESULT_OK;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecordFragment extends Fragment {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    Button recordBtn;
    TextView recordFoodName, recordFoodKcal, recordFoodInfo;
    TextView raw_mtrl;
    ImageButton record_breakfast_btn, record_lunch_btn, record_dinner_btn;
    ImageView foodImg;
    TextView searchedFoodName;
    TextView searchedFoodKcal;
    TextView searchedFoodNutriInfo;
    AllergyList allergyList = new AllergyList();
    TextView underFoodImageText;
    ArrayList<String> userAllergy = new ArrayList<>();

    boolean morningImgBtn = false;
    boolean lunchImgBtn = false;
    boolean dinnerImgBtn = false;

    private ImageButton dateButton;

    private TextView dateTextView;
    private Calendar selectedDate = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        searchedFoodName = view.findViewById(R.id.recordFoodName);
        searchedFoodKcal = view.findViewById(R.id.recordFoodKcal);
        searchedFoodNutriInfo = view.findViewById(R.id.recordFoodInfo);
        recordBtn = view.findViewById(R.id.recordBtn);
        recordFoodName = view.findViewById(R.id.recordFoodName);
        recordFoodKcal = view.findViewById(R.id.recordFoodKcal);
        recordFoodInfo = view.findViewById(R.id.recordFoodInfo);
        raw_mtrl = view.findViewById(R.id.raw_material_text);
        foodImg = view.findViewById(R.id.recordFoodImage);
        record_breakfast_btn = view.findViewById(R.id.record_breakfast_btn);
        record_lunch_btn = view.findViewById(R.id.record_lunch_btn);
        record_dinner_btn = view.findViewById(R.id.record_dinner_btn);


        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());


        dateButton = view.findViewById(R.id.dateButton);
        dateTextView = view.findViewById(R.id.dateTextView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA);
        dateTextView.setText(dateFormat.format(selectedDate.getTime()));

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        foodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View customLayout = getLayoutInflater().inflate(R.layout.record_popup, null);
                builder.setView(customLayout);

                AlertDialog dialog = builder.create(); // AlertDialog를 final로 선언

                Button barcodebtn = customLayout.findViewById(R.id.barcodeBtn);
                barcodebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 팝업 닫기
                        dialog.dismiss();
                        if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_GRANTED) {
                            startBarcodeScanner();
                        } else {
                            ActivityCompat.requestPermissions(requireActivity(),
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                        }
                    }
                });

                Button searchbtn = customLayout.findViewById(R.id.searchbtn);
                searchbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 팝업 닫기
                        dialog.dismiss();
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });

                Button button3 = customLayout.findViewById(R.id.button3);
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 팝업 닫기
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "3번 버튼이 눌림", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });




        record_breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (morningImgBtn) {
                    morningImgBtn = false;
                    record_breakfast_btn.setAlpha(0.3f);
                } else {
                    morningImgBtn = true;
                    record_breakfast_btn.setAlpha(1.0f);
                }
            }
        });
        record_lunch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lunchImgBtn) {
                    lunchImgBtn = false;
                    record_lunch_btn.setAlpha(0.3f);
                } else {
                    lunchImgBtn = true;
                    record_lunch_btn.setAlpha(1.0f);
                }
            }
        });
        record_dinner_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dinnerImgBtn) {
                    dinnerImgBtn = false;
                    record_dinner_btn.setAlpha(0.3f);
                } else {
                    dinnerImgBtn = true;
                    record_dinner_btn.setAlpha(1.0f);
                }
            }
        });



        // 기록하기 버튼을 눌렀을 때
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = dbHelper.getNickname();
                String foodname = recordFoodName.getText().toString();

                // dateTextView에서 날짜 문자열 가져오기
                String dateText = dateTextView.getText().toString();

                // dateText를 "yy년 MM월 dd일" 형식에서 "yyyy-MM-dd" 형식으로 변환
                SimpleDateFormat inputFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA);
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                String date = "";
                try {
                    Date parsedDate = inputFormat.parse(dateText);
                    date = outputFormat.format(parsedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 현재 날짜 가져오기
                SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                String currentDate = currentDateFormat.format(new Date());

                // 현재 시간을 "hh:mm:ss" 형식으로 가져오기
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
                String time = timeFormat.format(new Date());

                int count = 0;
                if (morningImgBtn) {
                    count++;
                }
                if (lunchImgBtn) {
                    count++;
                }
                if (dinnerImgBtn) {
                    count++;
                }

                if (foodname.equals("음식 이름")) {
                    Toast.makeText(getActivity(), "음식을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                } else if (date.compareTo(currentDate) > 0) {
                    Toast.makeText(getActivity(), "미래 날짜를 선택할 수 없습니다."+date, Toast.LENGTH_SHORT).show();
                } else {
                    if (count == 0) {
                        Toast.makeText(getActivity(), "아침, 점심, 저녁 중 하나를 선택하세요.", Toast.LENGTH_SHORT).show();
                    } else if (count > 1) {
                        Toast.makeText(getActivity(), "아침, 점심, 저녁 중 하나만 선택하세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        String dateTime = date + " " + time;

                        String mealTime = "";

                        if(morningImgBtn)
                            mealTime="아침";
                        else if(lunchImgBtn)
                            mealTime="점심";
                        else
                            mealTime="저녁";
                        dbHelper.addIntake(nickname, foodname, dateTime, mealTime);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Handler handler = new Handler(Looper.getMainLooper());
//        사용자 알레르기 정보 가져오기
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Integer> userAllergyListNum = dbHelper.getUserAllergy(); //사용자 알레르기 번호 목록

//        //알레르기 정보 가져오기
        for(int i = 0; i < userAllergyListNum.size(); i++) {

            for (int j = 0; j < allergyList.getAllergyArrayList(userAllergyListNum.get(i)).size(); j++) {
                userAllergy.add(allergyList.getAllergyArrayList(userAllergyListNum.get(i)).get(j));
            }
        }

        if(resultCode == RESULT_OK) {
            String fn = data.getStringExtra("fname");
            String kcal = data.getStringExtra("kcal");
            String info = data.getStringExtra("foodinfo");
            String mtrl = data.getStringExtra("rawmtrl");
            String imgurl = data.getStringExtra("imgurl");

            if(imgurl == null && mtrl == null){
                foodImg.setImageResource(R.drawable.noimg);
                raw_mtrl.setText("알 수 없음");

            }
            else if (imgurl == null){
                foodImg.setImageResource(R.drawable.noimg);
                SpannableString spannableString = new SpannableString(mtrl);
                for (int i = 0; i < userAllergy.size(); i++) {
                    int startIndex = mtrl.indexOf(userAllergy.get(i));

                    if (startIndex != -1) {
                        int endIndex = startIndex + userAllergy.get(i).length();
                        spannableString.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex, 0);

                    }
                }
                raw_mtrl.setText(spannableString);
            }
            else if(mtrl == null){
                raw_mtrl.setText("알 수 없음");
                Glide.with(this).load(imgurl).into(foodImg);
            }
            else{
                SpannableString spannableString = new SpannableString(mtrl);
                for (int i = 0; i < userAllergy.size(); i++) {
                    int startIndex = mtrl.indexOf(userAllergy.get(i));
                    if (startIndex != -1) {
                        int endIndex = startIndex + userAllergy.get(i).length();
                        spannableString.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex, 0);

                    }
                }
                raw_mtrl.setText(spannableString);
                Glide.with(this).load(imgurl).into(foodImg);
            }
            recordFoodName.setText(fn);
            recordFoodKcal.setText(kcal);
            recordFoodInfo.setText(info);
        }
    }



    // DatePickerDialog를 표시하는 메서드
    private void showDatePickerDialog() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA);
                String formattedDate = dateFormat.format(selectedDate.getTime());

                dateTextView.setText(formattedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }


    private String dateFormat(String pattern){
        Date date = new Date();
        return new SimpleDateFormat(pattern).format(date);
    }

    @Override
    public void onResume() {
        super.onResume();

        // 현재 날짜를 가져와서 dateTextView 업데이트
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA);
        String currentDate = currentDateFormat.format(new Date());
        dateTextView.setText(currentDate);
    }

}