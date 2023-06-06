package com.example.project_main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MypageFragment extends Fragment {

    MyDatabaseHelper dbHelper;
    SQLiteDatabase database;

    private String nickname;
    private int age;
    private String sex;
    private float height;
    private float weight;
    private String activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        TextView userProfileName = view.findViewById(R.id.userProfileName);
        TextView userProfileAge = view.findViewById(R.id.userProfileAge);
        TextView userProfileHeight = view.findViewById(R.id.userProfileHeight);
        TextView userProfileWeight = view.findViewById(R.id.userProfileWeight);

        //DB Connect
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        database = dbHelper.getWritableDatabase();
        executeQuery();

        userProfileName.setText(nickname);
        userProfileAge.setText(age+"");
        userProfileHeight.setText(height+"");
        userProfileWeight.setText(weight+"");

        //db테스트
        String testString = dbHelper.getResult();
        Toast.makeText(getActivity().getApplicationContext(),testString,Toast.LENGTH_SHORT).show();



        return view;
    }

    private void executeQuery(){

        Cursor cursor = database.rawQuery("select nickname, age, sex, height, weight, activity from user_table",null);
        int recordCount = cursor.getCount();
        for (int i = 0; i < recordCount; i++){
            cursor.moveToNext();
            nickname = cursor.getString(0);
            age = cursor.getInt(1);
            sex = cursor.getString(2);
            height = cursor.getFloat(3);
            weight = cursor.getFloat(4);
            activity = cursor.getString(5);
        }
        cursor.close();

    }

}