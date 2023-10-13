package com.example.project_main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainRecordManage extends Activity {

    private Calendar selectedDate = Calendar.getInstance();
    private TextView selectedDateText;
    MyDatabaseHelper dbHelper;
    private ListView selectedDateListview;
    private ListViewAdapter listViewAdapter;
    private ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();
    private ArrayList<String> foodNutriInfo = new ArrayList<>();

    SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_manage);

        selectedDateText = findViewById(R.id.manageSelectedDate);

        //초기값 오늘 날짜
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA);
        String currentDate = currentDateFormat.format(new Date());
        selectedDateText.setText(currentDate);

        //리스트뷰, 리스트뷰 어뎁터 초기화
        listViewAdapter = new ListViewAdapter();
        selectedDateListview = (ListView) findViewById(R.id.selectedDateListview);
        getFoodListFromSelectedDate();


        //리스트뷰 길게 터치 시 이벤트
        selectedDateListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainRecordManage.this);
                builder.setTitle("음식 기록 삭제");
                builder.setMessage("[" + intake_food.get(i).getFoodName() + "] 을/를 삭제하시겠습니까?");

                //삭제
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteFoodIntake(intake_food.get(i).getIntakeID());
                        Toast.makeText(MainRecordManage.this, intake_food.get(i).getFoodName() +" 이/가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                        getFoodListFromSelectedDate();

                    }
                });
                //취소
                builder.setNegativeButton("취소", null);

                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });

        //날짜선택 버튼
        findViewById(R.id.manageSelectDateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //x 버튼
        findViewById(R.id.manageXBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // DatePickerDialog를 표시하는 메서드
    private void showDatePickerDialog() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA);
                String formattedDate = dateFormat.format(selectedDate.getTime());

                selectedDateText.setText(formattedDate);

                getFoodListFromSelectedDate();

            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void getFoodListFromSelectedDate() {
        //db에서 해당 날짜 음식 정보 가져오기
        String dbDate = dbFormat.format(selectedDate.getTime());
        String sql_sentence = "select intakeID, food_table.foodname, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat, time\n" +
                "from food_table, intake_table\n" +
                "where food_table.foodname = intake_table.foodname\n" +
                "and intakeID in (select intakeID from intake_table where substr(date,1,10) = '" + dbDate + "');";

        dbHelper = new MyDatabaseHelper(this);
        intake_food = dbHelper.executeQuerySearchIntakeFoodFromSelectedDate(sql_sentence);

        //음식 탄단지 정보 저장
        for (int i = 0; i < intake_food.size(); i++) {
            foodNutriInfo.add("탄수화물 " + intake_food.get(i).getCarbohydrate() + "g" + " 단백질 " + intake_food.get(i).getProtein() + "g" + " 지방 " + intake_food.get(i).getProvince() + "g");
        }
        //어뎁터 초기화
        listViewAdapter.clearItem();
        //어뎁터에 아이템 추가
        for (int i = 0; i < intake_food.size(); i++) {
            if (intake_food.get(i).getIntakeTime().equals("아침")) {
                listViewAdapter.addItem(R.drawable.breakfast_icon, intake_food.get(i).getFoodName(), Math.round(intake_food.get(i).getKcal()) + " Kcal", foodNutriInfo.get(i));
            }
            else if (intake_food.get(i).getIntakeTime().equals("점심")){
                listViewAdapter.addItem(R.drawable.lunch_icon, intake_food.get(i).getFoodName(), Math.round(intake_food.get(i).getKcal()) + " Kcal", foodNutriInfo.get(i));
            }
            else if (intake_food.get(i).getIntakeTime().equals("저녁")){
                listViewAdapter.addItem(R.drawable.dinner_icon,intake_food.get(i).getFoodName(), Math.round(intake_food.get(i).getKcal()) + " Kcal", foodNutriInfo.get(i));
            }
            else;

        }
        //리스트뷰에 어뎁터 set
        selectedDateListview.setAdapter(listViewAdapter);
        //ArrayList 초기화
        foodNutriInfo.clear();

    }

}
