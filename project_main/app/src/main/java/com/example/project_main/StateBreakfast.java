package com.example.project_main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class StateBreakfast extends Fragment {

    ArrayList<BreakfastArray> breakfastArray;
    ListView breakfastListView;
    private static BreakfastAdapter customBreakfastAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View breakfastView = inflater.inflate(R.layout.fragment_state_breakfast, container, false);

        //리스트 아이템 추가 (아이콘, 이름, 칼로리, 탄단지당나콜포트)
        breakfastArray = new ArrayList<>();
        breakfastArray.add(new BreakfastArray(R.drawable.lunch_icon,"콘푸로스트1","300kcal","탄단지1"));
        breakfastArray.add(new BreakfastArray(R.drawable.lunch_icon,"콘푸로스트2","300kcal","탄단지2"));
        breakfastArray.add(new BreakfastArray(R.drawable.lunch_icon,"콘푸로스트3","300kcal","탄단지3"));

        breakfastListView = breakfastView.findViewById(R.id.breakfast_list_custom);
        customBreakfastAdapter = new BreakfastAdapter(getContext(),breakfastArray);
        breakfastListView.setAdapter(customBreakfastAdapter);
        
        //아이템 클릭 시 이벤트
        breakfastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) view.findViewById(R.id.name).getTag().toString();
                Toast.makeText(getContext(),"선택됨: " + " " + selectedItem, Toast.LENGTH_LONG).show();
            }
        });

        return breakfastView;
    }
}

class BreakfastArray{
    private int img;
    private String name;
    private String kcal;
    private String info;

    public BreakfastArray(int img, String name, String kcal, String info){
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

