package com.example.project_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frame_1, container, false);


        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getContext());
        int rank=1;
        String foodName = dbHelper.getNthMostEatenFoodForWeek(rank);


        TextView foodTop1 = rootView.findViewById(R.id.foodTop1);
        foodTop1.setText(foodName);

        return rootView;
    }
}
