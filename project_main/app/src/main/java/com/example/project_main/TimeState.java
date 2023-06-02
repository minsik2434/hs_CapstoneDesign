package com.example.project_main;

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

    ArrayList<MainFoodArray> breakfastArray;
    ArrayList<MainFoodArray> lunchArray;
    ArrayList<MainFoodArray> dinnerArray;

    //커스텀 리스트뷰 선택
    ListView mainFoodListView;

    private static TimeStateAdapter customBreakfastAdapter;
    private static TimeStateAdapter customLunchAdapter;
    private static TimeStateAdapter customDinnerAdapter;

    String timeStateStr="morning"; //어댑터 선택을 위한 변수, 초기 설정은 아침

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View timeView = inflater.inflate(R.layout.fragment_time_state, container, false);

        //아침 리스트 아이템 추가 (아이콘, 이름, 칼로리, 탄단지당나콜포트)
        breakfastArray = new ArrayList<>();
        breakfastArray.add(new MainFoodArray(R.drawable.breakfast_icon,"아침1","300kcal","탄단지1"));
        breakfastArray.add(new MainFoodArray(R.drawable.breakfast_icon,"아침2","300kcal","탄단지2"));
        breakfastArray.add(new MainFoodArray(R.drawable.breakfast_icon,"아침3","300kcal","탄단지3"));

        //점심 리스트 아이템 추가 (아이콘, 이름, 칼로리, 탄단지당나콜포트)
        lunchArray = new ArrayList<>();
        lunchArray.add(new MainFoodArray(R.drawable.lunch_icon,"점심1","300kcal","탄단지1"));
        lunchArray.add(new MainFoodArray(R.drawable.lunch_icon,"점심2","300kcal","탄단지2"));
        lunchArray.add(new MainFoodArray(R.drawable.lunch_icon,"점심3","300kcal","탄단지3"));

        //저녁 리스트 아이템 추가 (아이콘, 이름, 칼로리, 탄단지당나콜포트)
        dinnerArray = new ArrayList<>();
        dinnerArray.add(new MainFoodArray(R.drawable.dinner_icon,"저녁1","300kcal","탄단지1"));
        dinnerArray.add(new MainFoodArray(R.drawable.dinner_icon,"저녁2","300kcal","탄단지2"));
        dinnerArray.add(new MainFoodArray(R.drawable.dinner_icon,"저녁3","300kcal","탄단지3"));

        //커스텀리스트뷰 설정 및 어댑터 선언
        mainFoodListView = timeView.findViewById(R.id.time_list_custom);

        customBreakfastAdapter = new TimeStateAdapter(getContext(),breakfastArray);
        customLunchAdapter = new TimeStateAdapter(getContext(),lunchArray);
        customDinnerAdapter = new TimeStateAdapter(getContext(),dinnerArray);

        //아이템 클릭 시 이벤트(아침)
        mainFoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = (String) view.findViewById(R.id.foodName).getTag().toString();
                Toast.makeText(getContext(),"선택됨: " + " " + selectedItem, Toast.LENGTH_LONG).show();
            }
        });

        setStateAdapter(timeStateStr);
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

class MainFoodArray{
    private int img;
    private String name;
    private String kcal;
    private String info;

    public MainFoodArray(int img, String name, String kcal, String info){
        this.img = img;
        this.name = name;
        this.kcal = kcal;
        this.info = info;
    }

    public int getImg(){
        return img;
    }

    public String getName(){
        return name;
    }

    public String getKcal(){
        return kcal;
    }

    public String getInfo(){
        return info;
    }

}