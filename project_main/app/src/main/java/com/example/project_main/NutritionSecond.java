package com.example.project_main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NutritionSecond extends Fragment {

    private ProgressBar progressbarCholesterol;
    private ProgressBar progressbarTransFat;
    private ProgressBar progressbarSaturFat;

    MyDatabaseHelper dbHelper;
    SQLiteDatabase database;

    private TextView cholPercentage;
    private TextView transFatPercentage;
    private TextView saturFatPercentage;
    private TextView remainNutri2;

    private ArrayList<Float> foodCholesterol = new ArrayList<>();
    private ArrayList<Float> foodTransFat = new ArrayList<>();
    private ArrayList<Float> foodSaturFat = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutri2, container, false);

        progressbarCholesterol = view.findViewById(R.id.progressCholesterol);
        progressbarTransFat = view.findViewById(R.id.progressTransFat);
        progressbarSaturFat = view.findViewById(R.id.progressSaturFat);

        cholPercentage = view.findViewById(R.id.cholNutri1Text);
        transFatPercentage = view.findViewById(R.id.transFatNutri1Text);
        saturFatPercentage = view.findViewById(R.id.saturFatFatNutri1Text);
        remainNutri2 = view.findViewById(R.id.remainNutri2);

        //DB Connect
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        database = dbHelper.getWritableDatabase();
        executeQuery();

        //변수 선언
        Float totalCholesterol=0.0f;
        Float totalTransFat=0.0f;
        Float totalSaturFat=0.0f;

        //총합
        for (int i = 0; i < foodCholesterol.size(); i++)
        {
            totalCholesterol += foodCholesterol.get(i);
            totalTransFat += foodTransFat.get(i);
            totalSaturFat += foodSaturFat.get(i);
        }

        //프로그레스 바 설정. (총 먹은 양/권장량)
        progressbarCholesterol.setProgress( Math.round( (totalCholesterol/100.0f)*100 ) );
        progressbarTransFat.setProgress( Math.round( (totalTransFat/100.0f)*100 ) );
        progressbarSaturFat.setProgress( Math.round( (totalSaturFat/100.0f)*100 ) );

        //총섭취량/권장섭취량 텍스트 설정. 권장섭취량은 임시값
        cholPercentage.setText( totalCholesterol+ " / 100.0mg");
        transFatPercentage.setText( totalTransFat+ " / 100.0g");
        saturFatPercentage.setText( totalSaturFat+ " / 100.0g");

        //초과하면 주의 알림. 100은 권장량 임시값
        if (100 - totalCholesterol < 0 || 100 - totalTransFat < 0 || 100 - totalSaturFat < 0) {
            remainNutri2.setText("초과하여 먹은 성분이 있습니다." + "\n주의해주세요!");
            remainNutri2.setBackgroundResource(R.drawable.main_food_alert_yellow);
        }
        else;

        return view;
    }


    //오늘 먹은 음식의 영양 정보
    private void executeQuery(){
        Cursor cursor = database.rawQuery("select cholesterol, trans_fat, saturated_fat\n" +
                "from food_table, nutrition, intake_table\n" +
                "where food_table.foodID = nutrition.foodID\n" +
                "and food_table.foodID = intake_table.foodID\n" +
                "and intakeID in (select intakeID from intake_table where substr(date,1,10) = DATE('now') );",null);
        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++){
            cursor.moveToNext();
            foodCholesterol.add(cursor.getFloat(0));
            foodTransFat.add(cursor.getFloat(1));
            foodSaturFat.add(cursor.getFloat(2));
        }

        cursor.close();
    }
}