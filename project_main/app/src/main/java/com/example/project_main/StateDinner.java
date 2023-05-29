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


public class StateDinner extends Fragment {

    ArrayList<DinnerArray> dinnerArray;
    ListView dinnerListView;
    private static DinnerAdapter customDinnerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dinnerView = inflater.inflate(R.layout.fragment_state_dinner, container, false);

        //리스트 아이템 추가 (아이콘, 이름, 칼로리, 탄단지당나콜포트)
        dinnerArray = new ArrayList<>();
        dinnerArray.add(new DinnerArray(R.drawable.dinner_icon,"저녁1","300kcal","탄단지1"));
        dinnerArray.add(new DinnerArray(R.drawable.dinner_icon,"저녁2","300kcal","탄단지2"));
        dinnerArray.add(new DinnerArray(R.drawable.dinner_icon,"저녁3","300kcal","탄단지3"));

        dinnerListView = dinnerView.findViewById(R.id.dinner_list_custom);
        customDinnerAdapter = new DinnerAdapter(getContext(),dinnerArray);
        dinnerListView.setAdapter(customDinnerAdapter);

        //아이템 클릭 시 이벤트
        dinnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) view.findViewById(R.id.foodName).getTag().toString();
                Toast.makeText(getContext(),"선택됨: " + " " + selectedItem, Toast.LENGTH_LONG).show();
            }
        });

        return dinnerView;
    }

}

class DinnerArray{
    private int img;
    private String name;
    private String kcal;
    private String info;

    public DinnerArray(int img, String name, String kcal, String info){
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