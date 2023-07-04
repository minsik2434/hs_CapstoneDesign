package com.example.project_main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaRouter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NutritionFirst extends Fragment {

    private ProgressBar mainCircleProgressbar;
    private ProgressBar progressbarCarbohydrate;
    private ProgressBar progressbarProtein;
    private ProgressBar progressbarProvince;

    MyDatabaseHelper dbHelper;
    private ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();
    private ArrayList<UserDto> userInfo = new ArrayList<UserDto>();


    String sql_sentence = "SELECT intake_table.foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat  from intake_table join food_table on intake_table.foodname = food_table.foodname where substr(date,1,10) = date('now','localtime');";

    private TextView kcalPercentage;
    private TextView carboPercentage;
    private TextView proteinPercentage;
    private TextView provincePercentage;
    private TextView remainKcal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_nutri1, container, false);

        mainCircleProgressbar = view.findViewById(R.id.mainCircleProgressbar);
        progressbarCarbohydrate = view.findViewById(R.id.progressCarbohydrate);
        progressbarProtein = view.findViewById(R.id.progressProtein);
        progressbarProvince = view.findViewById(R.id.progressProvince);

        kcalPercentage = view.findViewById(R.id.kcalNutri1Text);
        carboPercentage = view.findViewById(R.id.carboNutri1Text);
        proteinPercentage = view.findViewById(R.id.proteinNutri1Text);
        provincePercentage = view.findViewById(R.id.provinceNutri1Text);
        remainKcal = view.findViewById(R.id.remainKcal);

        //DB Connect get intake food
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        intake_food = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);
        userInfo = dbHelper.ExecuteQueryGetUserInfo();


        //변수 선언
        int totalKcal=0;
        Float totalCarbohydrate=0.0f;
        Float totalProtein=0.0f;
        Float totalProvince=0.0f;

        //총합
        for (int i = 0; i < intake_food.size(); i++)
        {
            totalKcal += (int) intake_food.get(i).getKcal();
            totalCarbohydrate += intake_food.get(i).getCarbohydrate();
            totalProtein += intake_food.get(i).getProtein();
            totalProvince += intake_food.get(i).getProvince();
        }

        //프로그레스 바 설정. 2200.0f 는 임시값. (총 먹은 칼로리/권장 칼로리)
        mainCircleProgressbar.setProgress( Math.round( (totalKcal/userInfo.get(0).getRecommendedKcal())*100) ) ;
        progressbarCarbohydrate.setProgress( Math.round( (totalCarbohydrate/100.0f)*100 ) );
        progressbarProtein.setProgress( Math.round( (totalProtein/100.0f)*100 ) );
        progressbarProvince.setProgress( Math.round( (totalProvince/100.0f)*100 ) );

        //탄단지 총섭취량/권장섭취량 텍스트 설정. 권장섭취량은 임시값
        kcalPercentage.setText(totalKcal + "");
        carboPercentage.setText( String.format("%.2f",totalCarbohydrate) + " / 100.0 g");
        proteinPercentage.setText( String.format("%.2f",totalProtein)+ " / 100.0 g");
        provincePercentage.setText( String.format("%.2f",totalProvince)+ " / 100.0 g");

        //region code(탄단지 초과 섭취 시)
        if (100 - totalCarbohydrate < 0){
            carboPercentage.setTextColor(Color.parseColor("#ff0000"));
        }
        else if (1000 - totalProtein < 0){
            proteinPercentage.setTextColor(Color.parseColor("#ff0000"));
        }
        else if (100 - totalProvince < 0){
            provincePercentage.setTextColor(Color.parseColor("#ff0000"));
        }
        else;
        //endregion

        //region 남은 칼로리 표시. 초과하면 주의 알림. 2200은 권장칼로리 임시값
        if (userInfo.get(0).getRecommendedKcal() - totalKcal < 0) {
            //주의
            remainKcal.setText( ( totalKcal - userInfo.get(0).getRecommendedKcal() )  + " Kcal를 더 먹었습니다! " + "\n주의해주세요!");
            remainKcal.setBackgroundResource(R.drawable.main_food_alert_yellow);
            kcalPercentage.setTextColor(Color.parseColor("#ff0000"));
        }
        else
            remainKcal.setText( "남은 칼로리는 " + ( userInfo.get(0).getRecommendedKcal() - totalKcal)  +" Kcal 입니다" );
        //endregion

        return view;
    }



}