package com.example.project_main;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NutritionSecond extends Fragment {

    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor; // 에디터

    private boolean overSugar;
    private boolean overSalt;
    private boolean overCholesterol;
    private boolean overTransfat;
    private boolean overSaturfat;
    private String timelineText;

    private ProgressBar progressbarSugar;
    private ProgressBar progressbarSalt;
    private ProgressBar progressbarCholesterol;
    private ProgressBar progressbarTransFat;
    private ProgressBar progressbarSaturFat;

    MyDatabaseHelper dbHelper;
    private ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();
    private ArrayList<UserDto> userInfo = new ArrayList<UserDto>();

    UserRecommendAmount userRecAmount;
    private ArrayList<NutritionDto> nutriInfo = new ArrayList<>();

    private ArrayList<Integer> userDisease = new ArrayList<>();
    private ArrayList<Integer> userDiseaseListNum = new ArrayList<>();

    String sql_sentence = "select food_table.foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat, time\n" +
            "from food_table, intake_table\n" +
            "where food_table.foodname = intake_table.foodname\n" +
            "and intakeID in (select intakeID from intake_table where substr(date,1,10) = date('now'));";

    private TextView sugarPercentage;
    private TextView saltPercentage;
    private TextView cholPercentage;
    private TextView transFatPercentage;
    private TextView saturFatPercentage;

    private ImageView sugarStatus;
    private ImageView saltStatus;
    private ImageView cholesterolStatus;
    private ImageView transfatStatus;
    private ImageView saturfatStatus;

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

        sugarStatus = view.findViewById(R.id.sugarStatus);
        saltStatus = view.findViewById(R.id.saltStatus);
        cholesterolStatus = view.findViewById(R.id.cholesterolStatus);
        transfatStatus = view.findViewById(R.id.transfatStatus);
        saturfatStatus = view.findViewById(R.id.saturfatStatus);

        //endregion

        //DB Connect
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        intake_food = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);
        userInfo = dbHelper.ExecuteQueryGetUserInfo();

        //권장량 가져오기
        userRecAmount = new UserRecommendAmount();
        userDisease = dbHelper.getUserDisease(); //사용자 지병

        //알람
        // 1. Shared Preference 초기화
        pref = getContext().getSharedPreferences("pref2", Activity.MODE_PRIVATE);
        editor = pref.edit();

        // 2. 저장해둔 값 불러오기 ("식별값", 초기값) -> 식별값과 초기값은 직접 원하는 이름과 값으로 작성.

        overSugar = pref.getBoolean("overSugar", false);
        overSalt = pref.getBoolean("overSalt", false);
        overCholesterol = pref.getBoolean("overCholesterol", false);
        overTransfat = pref.getBoolean("overTransfat", false);
        overSaturfat = pref.getBoolean("overSaturfat", false);
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

        nutriInfo = userRecAmount.getUserRecommendAmount(userDiseaseListNum,userInfo.get(0).getAge(), userInfo.get(0).getHeight(), userInfo.get(0).getWeight(), userInfo.get(0).getSex(),userInfo.get(0).getActivity());

        //변수 선언
        Float totalSugar=0.0f;
        Float totalSalt=0.0f;
        Float totalCholesterol=0.0f;
        Float totalTransFat=0.0f;
        Float totalSaturFat=0.0f;

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
            progressbarSugar.setProgress( Math.round( (totalSugar/nutriInfo.get(0).getSugars())*100 ) );
            progressbarSalt.setProgress( Math.round( (totalSalt/nutriInfo.get(0).getSalt())*100 ) );
            progressbarCholesterol.setProgress( Math.round( (totalCholesterol/nutriInfo.get(0).getCholesterol())*100 ) );
            progressbarTransFat.setProgress( Math.round( (totalTransFat/nutriInfo.get(0).getTrans_fat())*100 ) );
            progressbarSaturFat.setProgress( Math.round( (totalSaturFat/nutriInfo.get(0).getSaturated_fat())*100 ) );


        //총섭취량/권장섭취량 텍스트 설정. 권장섭취량은 임시값

        sugarPercentage.setText( String.format("%.2f",totalSugar) + " / " + String.format("%.2f",nutriInfo.get(0).getSugars()) + " g");
        saltPercentage.setText( String.format("%.2f",totalSalt) + " / " + String.format("%.2f",nutriInfo.get(0).getSalt()) + " mg");
        cholPercentage.setText( String.format("%.2f",totalCholesterol) + " / " + String.format("%.2f",nutriInfo.get(0).getCholesterol()) + " mg");
        transFatPercentage.setText( String.format("%.2f",totalTransFat) + " / " + String.format("%.2f",nutriInfo.get(0).getTrans_fat()) + " g");
        saturFatPercentage.setText( String.format("%.2f",totalSaturFat) + " / " + String.format("%.2f",nutriInfo.get(0).getSaturated_fat()) + " g");

        //초과하면 주의 알림. 100은 권장량 임시값
        //region code(if overeaten, change color to red)
        if (nutriInfo.get(0).getSugars() - totalSugar < 0){
            sugarPercentage.setTextColor(Color.parseColor("#ff0000"));
            progressbarSugar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF5D5D")));
            sugarStatus.setImageResource(R.drawable.caution_cutout);

            if(overSugar == false)
            {
                alarmController.setAlarm2(13,0);
                timelineText = "당이 기준치를 초과하였습니다.";
                dbHelper.addUserTimeline(userInfo.get(0).getNickname(), timelineText, getResources().getDrawable(R.drawable.warning_icon2));
                editor.putBoolean("overSugar", true);
                editor.apply(); // 저장
            }
        }else {
            editor.putBoolean("overSugar", false);
            editor.apply(); // 저장

        }
        if (nutriInfo.get(0).getSalt() - totalSalt < 0){
            saltPercentage.setTextColor(Color.parseColor("#ff0000"));
            progressbarSalt.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF5D5D")));
            saltStatus.setImageResource(R.drawable.caution_cutout);

            if(overSalt == false)
            {
                alarmController.setAlarm2(14,0);
                timelineText = "나트륨이 기준치를 초과하였습니다.";
                dbHelper.addUserTimeline(userInfo.get(0).getNickname(), timelineText, getResources().getDrawable(R.drawable.warning_icon2));
                editor.putBoolean("overSalt", true);
                editor.apply(); // 저장
            }
        }else {
            editor.putBoolean("overSalt", false);
            editor.apply(); // 저장

        }
        if (nutriInfo.get(0).getCholesterol() - totalCholesterol < 0){
            cholPercentage.setTextColor(Color.parseColor("#ff0000"));
            progressbarCholesterol.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF5D5D")));
            cholesterolStatus.setImageResource(R.drawable.caution_cutout);

            if(overCholesterol == false)
            {
                alarmController.setAlarm2(15,0);
                timelineText = "콜레스테롤이 기준치를 초과하였습니다.";
                dbHelper.addUserTimeline(userInfo.get(0).getNickname(), timelineText, getResources().getDrawable(R.drawable.warning_icon2));
                editor.putBoolean("overCholesterol", true);
                editor.apply(); // 저장
            }
        }else {
            editor.putBoolean("overCholesterol", false);
            editor.apply(); // 저장

        }
        if (nutriInfo.get(0).getTrans_fat() - totalTransFat < 0){
            transFatPercentage.setTextColor(Color.parseColor("#ff0000"));
            progressbarTransFat.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF5D5D")));
            transfatStatus.setImageResource(R.drawable.caution_cutout);

            if(overTransfat == false)
            {
                alarmController.setAlarm2(16,0);
                timelineText = "트랜스지방이 기준치를 초과하였습니다.";
                dbHelper.addUserTimeline(userInfo.get(0).getNickname(), timelineText, getResources().getDrawable(R.drawable.warning_icon2));
                editor.putBoolean("overTransfat", true);
                editor.apply(); // 저장
            }
        }else {
            editor.putBoolean("overTransfat", false);
            editor.apply(); // 저장

        }
        if (nutriInfo.get(0).getSaturated_fat() - totalSaturFat < 0){
            saturFatPercentage.setTextColor(Color.parseColor("#ff0000"));
            progressbarSaturFat.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF5D5D")));
            saturfatStatus.setImageResource(R.drawable.caution_cutout);

            if(overSaturfat == false)
            {
                alarmController.setAlarm2(17,0);
                timelineText = "포화지방산이 기준치를 초과하였습니다.";
                dbHelper.addUserTimeline(userInfo.get(0).getNickname(), timelineText, getResources().getDrawable(R.drawable.warning_icon2));
                editor.putBoolean("overSaturfat", true);
                editor.apply(); // 저장
            }
        }else {
            editor.putBoolean("overSaturfat", false);
            editor.apply(); // 저장

        }
        //endregion

        return view;
    }


}