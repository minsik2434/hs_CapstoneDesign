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


public class StateLunch extends Fragment {

    ArrayList<LunchArray> lunchArray;
    ListView lunchListView;
    private static LunchAdapter customLunchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View lunchView = inflater.inflate(R.layout.fragment_state_lunch, container, false);

        //리스트 아이템 추가 (아이콘, 이름, 칼로리, 탄단지당나콜포트)
        lunchArray = new ArrayList<>();
        lunchArray.add(new LunchArray(R.drawable.lunch_icon,"점심1","300kcal","탄단지1"));
        lunchArray.add(new LunchArray(R.drawable.lunch_icon,"점심2","300kcal","탄단지2"));
        lunchArray.add(new LunchArray(R.drawable.lunch_icon,"점심3","300kcal","탄단지3"));

        lunchListView = lunchView.findViewById(R.id.lunch_list_custom);
        customLunchAdapter = new LunchAdapter(getContext(),lunchArray);
        lunchListView.setAdapter(customLunchAdapter);

        //아이템 클릭 시 이벤트
        lunchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) view.findViewById(R.id.foodName).getTag().toString();
                Toast.makeText(getContext(),"선택됨: " + " " + selectedItem, Toast.LENGTH_LONG).show();
            }
        });

        return lunchView;
    }
}

class LunchArray{
    private int img;
    private String name;
    private String kcal;
    private String info;

    public LunchArray(int img, String name, String kcal, String info){
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

