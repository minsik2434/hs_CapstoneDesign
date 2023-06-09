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

    private ArrayList<String> foodName = new ArrayList<>();
    private ArrayList<Integer> foodKcal = new ArrayList<>();
    private ArrayList<Float> foodCarbohydrate = new ArrayList<>();
    private ArrayList<Float> foodProtein = new ArrayList<>();
    private ArrayList<Float> foodProvince = new ArrayList<>();
    private ArrayList<Float> foodSugar = new ArrayList<>();
    private ArrayList<Float> foodSalt = new ArrayList<>();
    private ArrayList<Float> foodCholesterol = new ArrayList<>();
    private ArrayList<Float> foodTransFat = new ArrayList<>();
    private ArrayList<Float> foodSaturFat = new ArrayList<>();

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
    SQLiteDatabase database;

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
        database = dbHelper.getWritableDatabase();

        //음식 배열에 정보 추가
        for (int i = 0; i < timeList.length; i++) {
            //정보 추가
            getFoodIntakeExecuteQuery(timeList[i]);
            //탄단지 정보 종합
            for (int j = 0; j < foodName.size(); j++) {
                foodInfo.add("탄수화물 " + foodCarbohydrate.get(j) + "g" + " 단백질 " + foodProtein.get(j) + "g" + " 지방 " + foodProvince.get(j) + "g");
                foodInfo2.add("■ 당류 " + foodSugar.get(j) + " g" + "\n■ 나트륨 " + foodSalt.get(j) + " mg"
                        +"\n■ 콜레스테롤 " + foodCholesterol.get(j) + " mg"+ "\n■ 트랜스지방 " + foodTransFat.get(j) + " g"
                        + "\n■ 포화지방산 " + foodSaturFat.get(j) + " g");
            }
            //리스트 아이템 추가 (아이콘, 이름, 칼로리). 아이콘 수정필요
            switch (i) {
                //아침
                case 0:
                for (int k = 0; k < foodName.size(); k++) {
                    customBreakfastAdapter.addItem(R.drawable.breakfast_icon, foodName.get(k), foodKcal.get(k) + " Kcal", foodInfo.get(k));
                    breakfastArray.add(new TimeArray(R.drawable.breakfast_icon,foodName.get(k),foodKcal.get(k),foodInfo.get(k), foodInfo2.get(k)));
                }
                break;
                //점심
                case 1:
                for (int k = 0; k < foodName.size(); k++) {
                    customLunchAdapter.addItem(R.drawable.lunch_icon, foodName.get(k), foodKcal.get(k) + " Kcal", foodInfo.get(k));
                    lunchArray.add(new TimeArray(R.drawable.lunch_icon,foodName.get(k),foodKcal.get(k),foodInfo.get(k), foodInfo2.get(k)));
                }
                break;
                //저녁
                case 2:
                for (int k = 0; k < foodName.size(); k++) {
                    customDinnerAdapter.addItem(R.drawable.dinner_icon, foodName.get(k), foodKcal.get(k) + " Kcal", foodInfo.get(k));
                    dinnerArray.add(new TimeArray(R.drawable.dinner_icon,foodName.get(k),foodKcal.get(k),foodInfo.get(k), foodInfo2.get(k)));
                }
                break;
            }
            //초기화
            foodName.clear();
            foodKcal.clear();
            foodCarbohydrate.clear();
            foodProtein.clear();
            foodProvince.clear();
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

    private void getFoodIntakeExecuteQuery(String time){

        Cursor cursor = database.rawQuery("select foodname, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, trans_fat, saturated_fat\n" +
                "               from food_table, nutrition, intake_table\n" +
                "               where food_table.foodID = nutrition.foodID\n" +
                "               and food_table.foodID = intake_table.foodID\n" +
                "               and intakeID in (select intakeID from intake_table where substr(date,1,10) = DATE('now'))\n" +
                "\t\t\t   and time='"+time+"';",null);
        int recordCount = cursor.getCount();
        for (int i = 0; i < recordCount; i++){
            cursor.moveToNext();
            foodName.add(cursor.getString(0));
            foodKcal.add(cursor.getInt(1));
            foodCarbohydrate.add(cursor.getFloat(2));
            foodProtein.add(cursor.getFloat(3));
            foodProvince.add(cursor.getFloat(4));

            foodSugar.add(cursor.getFloat(5));
            foodSalt.add(cursor.getFloat(6));
            foodCholesterol.add(cursor.getFloat(7));
            foodTransFat.add(cursor.getFloat(8));
            foodSaturFat.add(cursor.getFloat(9));
        }
        cursor.close();

    }
}

class TimeArray {
    int img;
    String name;
    int kcal;
    String info;
    String info2;

    public TimeArray(int img, String name, int kcal, String info, String info2){
        this.img = img;
        this.name = name;
        this.kcal = kcal;
        this.info = info;
        this.info2 = info2;
    }

    public int getImg(){
        return img;
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