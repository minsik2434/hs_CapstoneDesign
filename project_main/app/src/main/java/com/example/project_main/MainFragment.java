package com.example.project_main;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainFragment extends Fragment {

    private int change = 1;
    MyDatabaseHelper dbHelper;

    private ListView mainFoodList;
    ScrollView mainScrollview;

    private ListViewAdapter listViewAdapter;
    private ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();
    private ArrayList<String> foodInfo = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ImageButton breakfast_btn = view.findViewById(R.id.breakfast_btn);
        ImageButton lunch_btn = view.findViewById(R.id.lunch_btn);
        ImageButton dinner_btn = view.findViewById(R.id.dinner_btn);

        TextView timeState_morning = view.findViewById(R.id.time_state_morning);
        TextView timeState_lunch = view.findViewById(R.id.time_state_lunch);
        TextView timeState_dinner = view.findViewById(R.id.time_state_dinner);

        TextView todayMainTextview = view.findViewById(R.id.todayMainTextview);

        mainFoodList = (ListView) view.findViewById(R.id.mainFoodList);
        mainScrollview = (ScrollView) view.findViewById(R.id.mainScrollview);

        listViewAdapter = new ListViewAdapter();
        ListView mainFoodList = view.findViewById(R.id.mainFoodList);

        String year = "yyyy";
        String month = "MM";
        String day = "dd";

        todayMainTextview.setText(dateFormat(year) + "년 " + dateFormat(month) + "월 " + dateFormat(day) + "일");

        //아이콘 색 변경 drawable 변수
        Drawable changeBreakfastIconColor = DrawableCompat.wrap(getResources().getDrawable(R.drawable.breakfast_icon)).mutate();
        Drawable changeLunchIconColor = DrawableCompat.wrap(getResources().getDrawable(R.drawable.lunch_icon)).mutate();
        Drawable changeDinnerIconColor = DrawableCompat.wrap(getResources().getDrawable(R.drawable.dinner_icon)).mutate();

        //DB
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());

        String sql_sentence = "SELECT intake_table.foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat, time from intake_table join food_table on intake_table.foodname = food_table.foodname where substr(date,1,10) = date('now','localtime')and time= '" + "아침" + "';";
        intake_food = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);

        for (int i = 0; i < intake_food.size(); i++) {
            foodInfo.add("탄수화물 " + intake_food.get(i).getCarbohydrate() + "g" + " 단백질 " + intake_food.get(i).getProtein() + "g" + " 지방 " + intake_food.get(i).getProvince() + "g");
        }

        //어뎁터에 아이템 추가
        for (int i = 0; i < intake_food.size(); i++) {
            listViewAdapter.addItem(0, intake_food.get(i).getFoodName(), Math.round(intake_food.get(i).getKcal()) + " Kcal", foodInfo.get(i));
        }

        mainFoodList.setAdapter(listViewAdapter);
        foodInfo.clear();

        //영양성분 전환 버튼
        view.findViewById(R.id.nutriChangeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NutriFragmentView(change);
            }
        });

        //아침 버튼 이벤트
        view.findViewById(R.id.breakfast_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이콘 색상변경
                DrawableCompat.setTint(changeBreakfastIconColor, ContextCompat.getColor(getContext(), R.color.black));
                DrawableCompat.setTint(changeLunchIconColor, ContextCompat.getColor(getContext(), R.color.grayColor));
                DrawableCompat.setTint(changeDinnerIconColor, ContextCompat.getColor(getContext(), R.color.grayColor));

                breakfast_btn.setImageDrawable(changeBreakfastIconColor);
                lunch_btn.setImageDrawable(changeLunchIconColor);
                dinner_btn.setImageDrawable(changeDinnerIconColor);

                //텍스트 변경
                timeState_morning.setVisibility(View.VISIBLE);
                timeState_lunch.setVisibility(View.INVISIBLE);
                timeState_dinner.setVisibility(View.INVISIBLE);

                //아침 리스트 불러오기
                foodInfo.clear();
                intake_food.clear();
                listViewAdapter.clearItem();

                String sql_sentence = "SELECT intake_table.foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat, time from intake_table join food_table on intake_table.foodname = food_table.foodname where substr(date,1,10) = date('now','localtime')and time= '" + "아침" + "';";
                intake_food = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);

                for (int i = 0; i < intake_food.size(); i++) {
                    foodInfo.add("탄수화물 " + intake_food.get(i).getCarbohydrate() + "g" + " 단백질 " + intake_food.get(i).getProtein() + "g" + " 지방 " + intake_food.get(i).getProvince() + "g");
                }

                //어뎁터에 아이템 추가
                for (int i = 0; i < intake_food.size(); i++) {
                    listViewAdapter.addItem(0, intake_food.get(i).getFoodName(), Math.round(intake_food.get(i).getKcal()) + " Kcal", foodInfo.get(i));
                }

                mainFoodList.setAdapter(listViewAdapter);

            }
        });
        //점심 버튼 이벤트
        view.findViewById(R.id.lunch_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이콘 색상변경
                DrawableCompat.setTint(changeBreakfastIconColor, ContextCompat.getColor(getContext(), R.color.grayColor));
                DrawableCompat.setTint(changeLunchIconColor, ContextCompat.getColor(getContext(), R.color.black));
                DrawableCompat.setTint(changeDinnerIconColor, ContextCompat.getColor(getContext(), R.color.grayColor));

                breakfast_btn.setImageDrawable(changeBreakfastIconColor);
                lunch_btn.setImageDrawable(changeLunchIconColor);
                dinner_btn.setImageDrawable(changeDinnerIconColor);

                //텍스트 변경
                timeState_morning.setVisibility(View.INVISIBLE);
                timeState_lunch.setVisibility(View.VISIBLE);
                timeState_dinner.setVisibility(View.INVISIBLE);

                //점심 리스트 불러오기
                foodInfo.clear();
                intake_food.clear();
                listViewAdapter.clearItem();
                String sql_sentence = "SELECT intake_table.foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat, time from intake_table join food_table on intake_table.foodname = food_table.foodname where substr(date,1,10) = date('now','localtime')and time= '" + "점심" + "';";
                intake_food = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);

                for (int i = 0; i < intake_food.size(); i++) {
                    foodInfo.add("탄수화물 " + intake_food.get(i).getCarbohydrate() + "g" + " 단백질 " + intake_food.get(i).getProtein() + "g" + " 지방 " + intake_food.get(i).getProvince() + "g");
                }

                //어뎁터에 아이템 추가
                for (int i = 0; i < intake_food.size(); i++) {
                    listViewAdapter.addItem(0, intake_food.get(i).getFoodName(), Math.round(intake_food.get(i).getKcal()) + " Kcal", foodInfo.get(i));
                }

                mainFoodList.setAdapter(listViewAdapter);
                foodInfo.clear();

            }
        });
        //저녁 버튼 이벤트
        view.findViewById(R.id.dinner_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이콘 색상변경
                DrawableCompat.setTint(changeBreakfastIconColor, ContextCompat.getColor(getContext(), R.color.grayColor));
                DrawableCompat.setTint(changeLunchIconColor, ContextCompat.getColor(getContext(), R.color.grayColor));
                DrawableCompat.setTint(changeDinnerIconColor, ContextCompat.getColor(getContext(), R.color.black));

                breakfast_btn.setImageDrawable(changeBreakfastIconColor);
                lunch_btn.setImageDrawable(changeLunchIconColor);
                dinner_btn.setImageDrawable(changeDinnerIconColor);

                //텍스트 변경
                timeState_morning.setVisibility(View.INVISIBLE);
                timeState_lunch.setVisibility(View.INVISIBLE);
                timeState_dinner.setVisibility(View.VISIBLE);

                //저녁 리스트 불러오기
                foodInfo.clear();
                intake_food.clear();
                listViewAdapter.clearItem();
                String sql_sentence = "SELECT intake_table.foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat, time from intake_table join food_table on intake_table.foodname = food_table.foodname where substr(date,1,10) = date('now','localtime')and time= '" + "저녁" + "';";
                intake_food = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);

                for (int i = 0; i < intake_food.size(); i++) {
                    foodInfo.add("탄수화물 " + intake_food.get(i).getCarbohydrate() + "g" + " 단백질 " + intake_food.get(i).getProtein() + "g" + " 지방 " + intake_food.get(i).getProvince() + "g");
                }

                //어뎁터에 아이템 추가
                for (int i = 0; i < intake_food.size(); i++) {
                    listViewAdapter.addItem(0, intake_food.get(i).getFoodName(), Math.round(intake_food.get(i).getKcal()) + " Kcal", foodInfo.get(i));
                }

                mainFoodList.setAdapter(listViewAdapter);
                foodInfo.clear();
            }
        });

        //기록 관리 버튼
        view.findViewById(R.id.mainRecordManageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainRecordManage.class);
                startActivityForResult(intent, 100);

            }
        });

        //스크롤 기능 구현
        mainFoodList.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mainScrollview.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //트랜잭션 실행(fragment 변경 시 실행됨)
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //영양성분(탄단지)
        NutritionFirst nutri1 = new NutritionFirst();
        transaction.replace(R.id.nutri_content, nutri1);
        transaction.commit();

        return view;
    }

    //프래그먼트 호출(영양성분)
    private void NutriFragmentView(int fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (fragment) {
            case 0:
                // 첫번 째 프래그먼트 호출
                NutritionFirst nutri1 = new NutritionFirst();
                transaction.replace(R.id.nutri_content, nutri1);
                transaction.commit();
                change = 1;
                break;

            case 1:
                // 두번 째 프래그먼트 호출
                NutritionSecond nutri2 = new NutritionSecond();
                transaction.replace(R.id.nutri_content, nutri2);
                transaction.commit();
                change = 0;
                break;
        }
    }

    private String dateFormat(String pattern) {
        Date date = new Date();
        return new SimpleDateFormat(pattern).format(date);
    }

}