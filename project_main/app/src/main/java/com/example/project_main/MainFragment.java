package com.example.project_main;

import android.content.res.ColorStateList;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment {

    private int change = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ImageButton breakfast_btn = view.findViewById(R.id.breakfast_btn);
        ImageButton lunch_btn = view.findViewById(R.id.lunch_btn);
        ImageButton dinner_btn = view.findViewById(R.id.dinner_btn);

        TextView timeState= view.findViewById(R.id.time_state);

        //아이콘 색 변경 drawable 변수
        Drawable changeBreakfastIconColor = DrawableCompat.wrap(getResources().getDrawable(R.drawable.breakfast_icon)).mutate();
        Drawable changeLunchIconColor = DrawableCompat.wrap(getResources().getDrawable(R.drawable.lunch_icon)).mutate();
        Drawable changeDinnerIconColor = DrawableCompat.wrap(getResources().getDrawable(R.drawable.dinner_icon)).mutate();

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
                timeState.setText("아침");


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
                timeState.setText("점심");

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
                timeState.setText("저녁");

            }
        });


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
}