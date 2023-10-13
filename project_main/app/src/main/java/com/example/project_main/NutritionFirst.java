package com.example.project_main;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NutritionFirst extends Fragment {

    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor; // 에디터

    private boolean overCarbohydrate;
    private boolean overProtein;
    private boolean overProvince;
    private String timelineText;

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
            "kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat, time " +
            "from intake_table join food_table on intake_table.foodname = food_table.foodname where substr(date,1,10) = date('now','localtime');";

    private TextView kcalRecommend;
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

        kcalRecommend = view.findViewById(R.id.mainRecommendKcal);
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

        //알람
        // 1. Shared Preference 초기화
        pref = getContext().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        // 2. 저장해둔 값 불러오기 ("식별값", 초기값) -> 식별값과 초기값은 직접 원하는 이름과 값으로 작성.
        overCarbohydrate = pref.getBoolean("overCarbohydrate", false);
        overProtein = pref.getBoolean("overProtein", false);
        overProvince = pref.getBoolean("overProvince", false);
        AlarmController alarmController = new AlarmController(getContext());

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

        nutriInfo = userRecAmount.getUserRecommendAmount(userDiseaseListNum, this.userInfo.get(0).getAge(), this.userInfo.get(0).getHeight(), this.userInfo.get(0).getWeight(), this.userInfo.get(0).getSex(), this.userInfo.get(0).getActivity());

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
        
        //권장 칼로리 표시
        kcalRecommend.setText("/ "+ nutriInfo.get(0).getKcal() + " kcal");

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

            System.out.println(overCarbohydrate);
            if(overCarbohydrate == false)
            {
                alarmController.setAlarm2(10,0);
                timelineText = "탄수화물이 기준치를 초과하였습니다.";
                dbHelper.addUserTimeline(userInfo.get(0).getNickname(), timelineText, getResources().getDrawable(R.drawable.warning_icon2));
                editor.putBoolean("overCarbohydrate", true);
                editor.apply(); // 저장
            }
        } else{
            editor.putBoolean("overCarbohydrate", false);
            editor.apply(); // 저장
        }
        if (nutriInfo.get(0).getProtein() - totalProtein < 0){
            proteinPercentage.setTextColor(Color.parseColor("#ff0000"));
            progressbarProtein.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF5D5D")));
            proteinStatus.setImageResource(R.drawable.caution_cutout);
            if(overProtein == false)
            {
                alarmController.setAlarm2(11,0);
                timelineText = "단백질이 기준치를 초과하였습니다.";
                dbHelper.addUserTimeline(userInfo.get(0).getNickname(), timelineText, getResources().getDrawable(R.drawable.warning_icon2));
                editor.putBoolean("overProtein", true);
                editor.apply(); // 저장
            }
        }else {
            editor.putBoolean("overProtein", false);
            editor.apply(); // 저장
        }
        if (nutriInfo.get(0).getProvince() - totalProvince < 0){
            provincePercentage.setTextColor(Color.parseColor("#ff0000"));
            progressbarProvince.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF5D5D")));
            provinceStatus.setImageResource(R.drawable.caution_cutout);
            if(overProvince == false)
            {
                alarmController.setAlarm2(12,0);
                timelineText = "지방이 기준치를 초과하였습니다.";
                dbHelper.addUserTimeline(userInfo.get(0).getNickname(), timelineText, getResources().getDrawable(R.drawable.warning_icon2));
                editor.putBoolean("overProvince", true);
                editor.apply(); // 저장
            }
        }else {
            editor.putBoolean("overProvince", false);
            editor.apply(); // 저장
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