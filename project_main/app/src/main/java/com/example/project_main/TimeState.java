package com.example.project_main;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class TimeState extends Fragment {

    private ArrayList<TimeArray> breakfastArray = new ArrayList<>();
    private ArrayList<TimeArray> lunchArray = new ArrayList<>();
    private ArrayList<TimeArray> dinnerArray = new ArrayList<>();

    private ArrayList<String> foodInfo = new ArrayList<>();
    private ArrayList<String> foodInfo2 = new ArrayList<>();

    private String[] timeList = {"아침","점심","저녁"};

    //커스텀 리스트뷰 선택
    ListView mainFoodListView;

    private static ListViewAdapter customBreakfastAdapter;
    private static ListViewAdapter customLunchAdapter;
    private static ListViewAdapter customDinnerAdapter;

    String timeStateStr="morning"; //어댑터 선택을 위한 변수, 첫 화면에 아침 리스트 보여줌

    MyDatabaseHelper dbHelper;

    private ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View timeView = inflater.inflate(R.layout.fragment_time_state, container, false);

        //어댑터 설정
        customBreakfastAdapter = new ListViewAdapter();
        customLunchAdapter = new ListViewAdapter();
        customDinnerAdapter = new ListViewAdapter();

        //DB Connect
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());

        //음식 배열에 정보 추가
        for (int i = 0; i < timeList.length; i++) {
            //정보 추가
            String sql_sentence = "SELECT intake_table.foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat  from intake_table join food_table on intake_table.foodname = food_table.foodname where substr(date,1,10) = date('now','localtime');";
            intake_food = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);
            //탄단지 정보 종합
            for (int j = 0; j < intake_food.size(); j++) {
                foodInfo.add("탄수화물 " + intake_food.get(j).getCarbohydrate() + "g" + " 단백질 " + intake_food.get(j).getProtein() + "g" + " 지방 " + intake_food.get(j).getProvince() + "g");
                foodInfo2.add("■ 당류 " + intake_food.get(j).getSugars() + " g" + "\n■ 나트륨 " + intake_food.get(j).getSalt() + " mg"
                        +"\n■ 콜레스테롤 " + intake_food.get(j).getCholesterol() + " mg"+ "\n■ 트랜스지방 " + intake_food.get(j).getTrans_fat() + " g"
                        + "\n■ 포화지방산 " + intake_food.get(j).getSaturated_fat() + " g");
            }
            //리스트 아이템 추가 (아이콘, 이름, 칼로리). 아이콘 수정필요
            switch (i) {
                //아침
                case 0:
                for (int k = 0; k < intake_food.size(); k++) {
                    customBreakfastAdapter.addItem(intake_food.get(k).getFoodName(), intake_food.get(k).getKcal() + " Kcal", foodInfo.get(k));
                    breakfastArray.add(new TimeArray(intake_food.get(k).getFoodName(),(int) intake_food.get(k).getKcal(), foodInfo.get(k), foodInfo2.get(k)));
                }
                break;
                //점심
                case 1:
                for (int k = 0; k < intake_food.size(); k++) {
                    customLunchAdapter.addItem(intake_food.get(k).getFoodName(), intake_food.get(k).getKcal() + " Kcal", foodInfo.get(k));
                    lunchArray.add(new TimeArray(intake_food.get(k).getFoodName(),(int) intake_food.get(k).getKcal(), foodInfo.get(k), foodInfo2.get(k)));
                }
                break;
                //저녁
                case 2:
                for (int k = 0; k < intake_food.size(); k++) {
                    customDinnerAdapter.addItem(intake_food.get(k).getFoodName(), intake_food.get(k).getKcal() + " Kcal", foodInfo.get(k));
                    dinnerArray.add(new TimeArray(intake_food.get(k).getFoodName(),(int) intake_food.get(k).getKcal(), foodInfo.get(k), foodInfo2.get(k)));
                }
                break;
            }
            //초기화
            foodInfo.clear();
            foodInfo2.clear();
        }


        //커스텀리스트뷰 설정 및 어댑터 선언
        mainFoodListView = timeView.findViewById(R.id.time_list_custom);
        setStateAdapter(timeStateStr);

        //아이템 클릭 시 이벤트
        mainFoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //대화상자
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());

                switch(timeStateStr) {
                    case "morning":
                        dlg.setTitle(breakfastArray.get(position).getName()); //제목
                        dlg.setMessage(breakfastArray.get(position).getKcal()+"Kcal"+"\n"+breakfastArray.get(position).getInfo()+"\n\n"+breakfastArray.get(position).getInfo2()); // 메시지
                        dlg.setIcon(R.drawable.breakfast_icon);
                        break;
                    case "lunch":
                        dlg.setTitle(lunchArray.get(position).getName()); //제목
                        dlg.setMessage(lunchArray.get(position).getKcal()+"Kcal"+"\n"+lunchArray.get(position).getInfo()+"\n\n"+lunchArray.get(position).getInfo2()); // 메시지
                        dlg.setIcon(R.drawable.lunch_icon);
                        break;
                    case "dinner":
                        dlg.setTitle(dinnerArray.get(position).getName()); //제목
                        dlg.setMessage(dinnerArray.get(position).getKcal()+"Kcal"+"\n"+dinnerArray.get(position).getInfo()+"\n\n"+dinnerArray.get(position).getInfo2()); // 메시지
                        dlg.setIcon(R.drawable.dinner_icon);
                        break;

                }

                dlg.show();
            }
        });

        return timeView;
    }

    //어댑터 선택(화면에 보여질 리스트뷰)
    private void setStateAdapter(String timeStateStr){
        switch(timeStateStr) {
            case "morning":
                mainFoodListView.setAdapter(customBreakfastAdapter);
                break;

            case "lunch":
                mainFoodListView.setAdapter(customLunchAdapter);
                break;

            default:
                mainFoodListView.setAdapter(customDinnerAdapter);
                break;
        }
    }
}

class TimeArray {

    String name;
    int kcal;
    String info;
    String info2;

    public TimeArray(String name, int kcal, String info, String info2){

        this.name = name;
        this.kcal = kcal;
        this.info = info;
        this.info2 = info2;
    }

    public String getName(){
        return name;
    }

    public int getKcal(){
        return kcal;
    }

    public String getInfo(){
        return info;
    }
    public String getInfo2() {
        return info2;
    }

}