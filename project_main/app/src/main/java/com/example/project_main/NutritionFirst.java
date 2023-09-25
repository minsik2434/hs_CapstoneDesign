package com.example.project_main;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NutritionFirst extends Fragment {

    private ProgressBar mainCircleProgressbar;
    private ProgressBar progressbarCarbohydrate;
    private ProgressBar progressbarProtein;
    private ProgressBar progressbarProvince;

    MyDatabaseHelper dbHelper;
    private ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();
    private ArrayList<UserDto> userInfo = new ArrayList<UserDto>();

    UserRecommendAmount userRecAmount;
    private ArrayList<NutritionDto> nutriInfo = new ArrayList<>();

    private ArrayList<Integer> userDisease = new ArrayList<>();
    private ArrayList<Integer> userDiseaseListNum = new ArrayList<>();

    private String sql_sentence = "SELECT intake_table.foodname, manufacturer, classification," +
            "kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat " +
            "from intake_table join food_table on intake_table.foodname = food_table.foodname where substr(date,1,10) = date('now','localtime');";

    private TextView kcalPercentage;
    private TextView carboPercentage;
    private TextView proteinPercentage;
    private TextView provincePercentage;

    private ImageView carboStatus;
    private ImageView proteinStatus;
    private ImageView provinceStatus;

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

        carboStatus = view.findViewById(R.id.carboStatus);
        proteinStatus = view.findViewById(R.id.proteinStatus);
        provinceStatus = view.findViewById(R.id.provinceStatus);

        //DB Connect get intake food
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        intake_food = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);
        userInfo = dbHelper.ExecuteQueryGetUserInfo();

        //지병에 따른 권장량 가져오기
        userRecAmount = new UserRecommendAmount();
        userDisease = dbHelper.getUserDisease(); //사용자 지병

        //지병 없다면
        if (userDisease.size() == 0) {
            userDiseaseListNum.add(0);
        }
        //지병 있다면
        else {
            for (int i = 0; i < userDisease.size(); i++) {

                userDiseaseListNum.add(userDisease.get(i) + 1);
            }
        }

        nutriInfo = userRecAmount.getUserRecommendAmount(userDiseaseListNum,userInfo.get(0).getAge(), userInfo.get(0).getHeight(), userInfo.get(0).getWeight(), userInfo.get(0).getSex(),userInfo.get(0).getActivity());

        //변수 선언
        Float totalKcal=0.0f;
        Float totalCarbohydrate=0.0f;
        Float totalProtein=0.0f;
        Float totalProvince=0.0f;

        //총합
        for (int i = 0; i < intake_food.size(); i++)
        {
            totalKcal += intake_food.get(i).getKcal();
            totalCarbohydrate += intake_food.get(i).getCarbohydrate();
            totalProtein += intake_food.get(i).getProtein();
            totalProvince += intake_food.get(i).getProvince();
        }


        //프로그레스 바 설정. 2200.0f 는 임시값. (총 먹은 칼로리/권장 칼로리)
            mainCircleProgressbar.setProgress( Math.round( (totalKcal/nutriInfo.get(0).getKcal())*100) ) ;
            progressbarCarbohydrate.setProgress( Math.round( (totalCarbohydrate/nutriInfo.get(0).getCarbohydrate())*100 ) );
            progressbarProtein.setProgress( Math.round( (totalProtein/nutriInfo.get(0).getProtein())*100 ) );
            progressbarProvince.setProgress( Math.round( (totalProvince/nutriInfo.get(0).getProvince())*100 ) );


        //탄단지 총섭취량/권장섭취량 텍스트 설정. 권장섭취량은 임시값
        kcalPercentage.setText(String.format("%.0f",totalKcal) + "");
        carboPercentage.setText( String.format("%.2f",totalCarbohydrate) + " / " + String.format("%.2f",nutriInfo.get(0).getCarbohydrate()) + " g");
        proteinPercentage.setText( String.format("%.2f",totalProtein) + " / " + String.format("%.2f",nutriInfo.get(0).getProtein()) + " g");
        provincePercentage.setText( String.format("%.2f",totalProvince) + " / " + String.format("%.2f",nutriInfo.get(0).getProvince()) + " g");

        //region code(탄단지 초과 섭취 시)
        if (nutriInfo.get(0).getCarbohydrate() - totalCarbohydrate < 0){
            carboPercentage.setTextColor(Color.parseColor("#ff0000"));
            progressbarCarbohydrate.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF5D5D")));
            carboStatus.setImageResource(R.drawable.caution_cutout);
        }
        if (nutriInfo.get(0).getProtein() - totalProtein < 0){
            proteinPercentage.setTextColor(Color.parseColor("#ff0000"));
            progressbarProtein.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF5D5D")));
            proteinStatus.setImageResource(R.drawable.caution_cutout);
        }
        if (nutriInfo.get(0).getProvince() - totalProvince < 0){
            provincePercentage.setTextColor(Color.parseColor("#ff0000"));
            progressbarProvince.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF5D5D")));
            provinceStatus.setImageResource(R.drawable.caution_cutout);
        }
        //endregion

        //region 남은 칼로리 표시. 초과하면 주의 알림. 2200은 권장칼로리 임시값
        if (nutriInfo.get(0).getKcal() - totalKcal < 0) {
            //주의
            kcalPercentage.setTextColor(Color.parseColor("#ff0000"));
        }


        return view;
    }



}