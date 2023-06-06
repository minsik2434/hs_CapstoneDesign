package com.example.project_main;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFragment extends Fragment {

    private int change = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ImageButton breakfast_btn = view.findViewById(R.id.breakfast_btn);
        ImageButton lunch_btn = view.findViewById(R.id.lunch_btn);
        ImageButton dinner_btn = view.findViewById(R.id.dinner_btn);

        TextView timeState_now = view.findViewById(R.id.time_state_now);
        TextView todayMainTextview = view.findViewById(R.id.todayMainTextview);

        String year = "yyyy";
        String month = "MM";
        String day = "dd";

        todayMainTextview.setText(dateFormat(year)+"년 " + dateFormat(month)+"월 " + dateFormat(day)+"일");

        //아이콘 색 변경 drawable 변수
        Drawable changeBreakfastIconColor = DrawableCompat.wrap(getResources().getDrawable(R.drawable.breakfast_icon)).mutate();
        Drawable changeLunchIconColor = DrawableCompat.wrap(getResources().getDrawable(R.drawable.lunch_icon)).mutate();
        Drawable changeDinnerIconColor = DrawableCompat.wrap(getResources().getDrawable(R.drawable.dinner_icon)).mutate();

        //버튼 애니메이션
        final Animation rotation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);

        //영양성분 전환 버튼
        view.findViewById(R.id.nutriChangeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NutriFragmentView(change);
                view.startAnimation(rotation);
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
                timeState_now.setText("아침");

                //아침 리스트 불러오기
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                TimeState breakfast = new TimeState();

                //상태 아침으로 변경
                breakfast.timeStateStr = "morning";

                transaction.replace(R.id.time_state_frame, breakfast);
                transaction.commit();

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
                timeState_now.setText("점심");

                //점심 리스트 불러오기
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                TimeState lunch = new TimeState();
                //상태 점심으로 변경
                lunch.timeStateStr = "lunch";
                transaction.replace(R.id.time_state_frame, lunch);
                transaction.commit();

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
                timeState_now.setText("저녁");

                //저녁 리스트 불러오기
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                TimeState dinner = new TimeState();
                //상태 저녁으로 변경
                dinner.timeStateStr = "dinner";
                transaction.replace(R.id.time_state_frame, dinner);
                transaction.commit();

            }
        });

        //트랜잭션 실행(fragment 변경 시 실행됨)
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //영양성분(탄단지)
        NutritionFirst nutri1 = new NutritionFirst();
        transaction.replace(R.id.nutri_content, nutri1);
        //섭취음식정보(아침)
        TimeState breakfast = new TimeState();
        transaction.replace(R.id.time_state_frame, breakfast);
        transaction.commit();

        return view;
    }

    //프래그먼트 호출(영양성분)
    private void NutriFragmentView(int fragment){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (fragment){
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

    private String dateFormat(String pattern){
        Date date = new Date();
        return new SimpleDateFormat(pattern).format(date);
    }
}