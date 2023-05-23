package com.example.project_main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

public class MainFragment extends Fragment {

    private int change = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        view.findViewById(R.id.nutriChangeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentView(change);
            }
        });

        // 탭호스트 연결
        TabHost tabhost = view.findViewById(R.id.tabhost);

        // 탭호스트 설정
        tabhost.setup();

        // 탭호스트에 탭1 추가
        TabHost.TabSpec spec = tabhost.newTabSpec("breakfast");
        spec.setContent(R.id.tab1);

        // 탭1 이름 변경 및 추가
        spec.setIndicator("아침");
        tabhost.addTab(spec);

        // 탭호스트에 탭2 추가
        spec = tabhost.newTabSpec("lunch");
        spec.setContent(R.id.tab2);

        // 탭2 이름 변경 및 추가
        spec.setIndicator("점심");
        tabhost.addTab(spec);

        // 탭호스트에 탭3 추가
        spec = tabhost.newTabSpec("dinner");
        spec.setContent(R.id.tab3);

        // 탭3 이름 변경 및 추가
        spec.setIndicator("저녁");
        tabhost.addTab(spec);

        return view;
    }

    //프래그먼트 호출
    private void FragmentView(int fragment){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (fragment){
            case 0:
                // 첫번 째 프래그먼트 호출
                NutritionFirst nutri1 = new NutritionFirst();
                transaction.replace(R.id.nutri_content, nutri1);
                transaction.commit();
                change = 1;
                break;

            case 1:
                // 두번 째 프래그먼트 호출
                NutritionSecond nutri2 = new NutritionSecond();
                transaction.replace(R.id.nutri_content, nutri2);
                transaction.commit();
                change = 0;
                break;
        }

    }


}