package com.example.project_main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StatisticsTabFragment extends Fragment {

    private final int Fragment_1 = 1;
    private final int Fragment_2 = 2;

    Button calendarBtn;
    Button staticsBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_statistics_tab, container, false);

        calendarBtn = view.findViewById(R.id.fragment_calender_btn);
        staticsBtn = view.findViewById(R.id.fragment_statistics_btn);

        view.findViewById(R.id.fragment_calender_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarBtn.setBackgroundResource(R.drawable.statics_btn_style1);
                calendarBtn.setTextColor(Color.parseColor("#ffffff"));
                staticsBtn.setBackgroundResource(R.drawable.statics_btn_style2);
                staticsBtn.setTextColor(Color.parseColor("#6844A8"));
                FragmentView(Fragment_1);

            }
        });

        view.findViewById(R.id.fragment_statistics_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarBtn.setBackgroundResource(R.drawable.statics_btn_style2);
                calendarBtn.setTextColor(Color.parseColor("#6844A8"));
                staticsBtn.setBackgroundResource(R.drawable.statics_btn_style1);
                staticsBtn.setTextColor(Color.parseColor("#ffffff"));
                FragmentView(Fragment_2);

            }
        });


        FragmentView(Fragment_1);

        return view;
    }

    private void FragmentView(int fragment){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (fragment){
            case 1:
                // 첫번 째 프래그먼트 호출
                CalenderFragment fragment1 = new CalenderFragment();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 2:
                // 두번 째 프래그먼트 호출
                StatisticsFragment fragment2 = new StatisticsFragment();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                break;
        }

    }

}