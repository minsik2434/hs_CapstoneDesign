package com.example.project_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frame_2, container, false);

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getContext());
        int rank=2;
//        String foodName = dbHelper.getNthMostEatenFoodForWeek(rank);
        String foodName = "라면";

        TextView foodTop2 = rootView.findViewById(R.id.foodTop2);
        foodTop2.setText(foodName);

        return rootView;
    }
}
