package com.example.project_main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

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
}