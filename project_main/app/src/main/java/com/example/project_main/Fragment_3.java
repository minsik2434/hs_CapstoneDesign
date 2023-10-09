package com.example.project_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frame_3, container, false);

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getContext());
        int rank=3;
//        String foodName = dbHelper.getNthMostEatenFoodForWeek(rank);
        String foodName = "삼양라면";

        TextView foodTop3 = rootView.findViewById(R.id.foodTop3);
        foodTop3.setText(foodName);

        return rootView;
    }
}

