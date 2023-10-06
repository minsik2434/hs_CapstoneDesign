package com.example.project_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class Fragment_1 extends Fragment {

    TextView foodTop1, foodTop2, foodTop3, foodTop4, foodTop5, foodTop6, foodTop7, foodTop8, foodTop9, foodTop10;

    Integer[] foodTopId = {R.id.foodTop1,R.id.foodTop2,R.id.foodTop3,R.id.foodTop4,R.id.foodTop5,R.id.foodTop6,R.id.foodTop7,R.id.foodTop8,R.id.foodTop9,R.id.foodTop10};
    TextView[] foodTopTextView = new TextView[foodTopId.length];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frame_1, container, false);

        for(int i=0;i<foodTopTextView.length;i++){
            foodTopTextView[i] = rootView.findViewById(foodTopId[i]);
        }

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getContext());
        String[] foodname = new String[10];

        String testFoodname = dbHelper.getNthMostEatenFoodForWeek(1);

        for(int i=1;i<=10;i++){
            foodname[i-1] = dbHelper.getNthMostEatenFoodForWeek(i);
        }

        for(int i=0;i<foodname.length;i++){
            foodTopTextView[i].setText(foodname[i]);
        }

        foodTopTextView[0].setText(testFoodname);


        return rootView;

    }
}
