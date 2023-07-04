package com.example.project_main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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


    private ProgressBar progressbarSugar;
    private ProgressBar progressbarSalt;
    private ProgressBar progressbarCholesterol;
    private ProgressBar progressbarTransFat;
    private ProgressBar progressbarSaturFat;

    MyDatabaseHelper dbHelper;
    private ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();
    String sql_sentence = "select food_table.foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat\n" +
            "from food_table, intake_table\n" +
            "where food_table.foodname = intake_table.foodname\n" +
            "and intakeID in (select intakeID from intake_table where substr(date,1,10) = date('now'));";

    private TextView sugarPercentage;
    private TextView saltPercentage;
    private TextView cholPercentage;
    private TextView transFatPercentage;
    private TextView saturFatPercentage;
    private TextView remainNutri2;

    private ArrayList<Float> foodSugar = new ArrayList<>();
    private ArrayList<Float> foodSalt = new ArrayList<>();
    private ArrayList<Float> foodCholesterol = new ArrayList<>();
    private ArrayList<Float> foodTransFat = new ArrayList<>();
    private ArrayList<Float> foodSaturFat = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutri2, container, false);

        //region code(findViewById)
        progressbarSugar = view.findViewById(R.id.progressSugar);
        progressbarSalt = view.findViewById(R.id.progressSalt);
        progressbarCholesterol = view.findViewById(R.id.progressCholesterol);
        progressbarTransFat = view.findViewById(R.id.progressTransFat);
        progressbarSaturFat = view.findViewById(R.id.progressSaturFat);

        sugarPercentage = view.findViewById(R.id.sugarNutri2Text);
        saltPercentage = view.findViewById(R.id.saltNutri2Text);
        cholPercentage = view.findViewById(R.id.cholNutri2Text);
        transFatPercentage = view.findViewById(R.id.transFatNutri2Text);
        saturFatPercentage = view.findViewById(R.id.saturFatFatNutri2Text);
        remainNutri2 = view.findViewById(R.id.remainNutri2);
        //endregion

        //DB Connect
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        intake_food = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);

        //변수 선언
        Float totalSugar=0.0f;
        Float totalSalt=0.0f;
        Float totalCholesterol=0.0f;
        Float totalTransFat=0.0f;
        Float totalSaturFat=0.0f;
        int needAlert=0;

        //총합
        for (int i = 0; i < intake_food.size(); i++)
        {
            totalSugar += intake_food.get(i).getSugars();
            totalSalt += intake_food.get(i).getSalt();
            totalCholesterol += intake_food.get(i).getCholesterol();
            totalTransFat += intake_food.get(i).getTrans_fat();
            totalSaturFat += intake_food.get(i).getSaturated_fat();
        }

        //프로그레스 바 설정. (총 먹은 양/권장량)
        progressbarSugar.setProgress( Math.round( (totalSugar/100.0f)*100 ) );
        progressbarSalt.setProgress( Math.round( (totalSalt/1000.0f)*100 ) );
        progressbarCholesterol.setProgress( Math.round( (totalCholesterol/100.0f)*100 ) );
        progressbarTransFat.setProgress( Math.round( (totalTransFat/100.0f)*100 ) );
        progressbarSaturFat.setProgress( Math.round( (totalSaturFat/100.0f)*100 ) );

        //총섭취량/권장섭취량 텍스트 설정. 권장섭취량은 임시값

        sugarPercentage.setText( String.format("%.2f",totalSugar)+ " / 100.0g");
        saltPercentage.setText( String.format("%.2f",totalSalt)+ " / 1000.0mg");
        cholPercentage.setText( String.format("%.2f",totalCholesterol)+ " / 100.0mg");
        transFatPercentage.setText( String.format("%.2f",totalTransFat)+ " / 100.0g");
        saturFatPercentage.setText( String.format("%.2f",totalSaturFat)+ " / 100.0g");

        //초과하면 주의 알림. 100은 권장량 임시값
        //region code(if overeaten, change color to red)
        if (100 - totalSugar < 0){
            sugarPercentage.setTextColor(Color.parseColor("#ff0000"));
            needAlert = 1;
        }
        else if (1000 - totalSalt < 0){
            saltPercentage.setTextColor(Color.parseColor("#ff0000"));
            needAlert = 1;
        }
        else if (100 - totalCholesterol < 0){
            cholPercentage.setTextColor(Color.parseColor("#ff0000"));
            needAlert = 1;
        }
        else if (100 - totalTransFat < 0){
            transFatPercentage.setTextColor(Color.parseColor("#ff0000"));
            needAlert = 1;
        }
        else if (100 - totalSaturFat < 0){
            saturFatPercentage.setTextColor(Color.parseColor("#ff0000"));
            needAlert = 1;
        }
        else;

        if (needAlert == 1){
            remainNutri2.setText("초과하여 먹은 성분이 있습니다." + "\n주의해주세요!");
            remainNutri2.setBackgroundResource(R.drawable.main_food_alert_yellow);
        }
        //endregion

        return view;
    }


}