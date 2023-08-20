package com.example.project_main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.compose.ui.platform.ComposeView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;


public class StatisticsFragment extends Fragment {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3;
    private CircleIndicator3 mIndicator;
    private BarChartView barChartView;
    private LineChartView lineChartView;
    private MyDatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());

        barChartView = view.findViewById(R.id.barChart);
        lineChartView = view.findViewById(R.id.lineChart);


        /**
         * 가로 슬라이드 뷰 Fragment
         */
        //ViewPager2
        mPager = view.findViewById(R.id.viewpager);
        //Adapter
        pagerAdapter = new BannerAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        //Indicator
        mIndicator = view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,0);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        /**
         * 이 부분 조정하여 처음 시작하는 이미지 설정.
         * 2000장 생성하였으니 현재위치 1002로 설정하여
         * 좌 우로 슬라이딩 할 수 있게 함. 거의 무한대로
         */

        mPager.setCurrentItem(540); //시작 지점
        mPager.setOffscreenPageLimit(3); //최대 이미지 수

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position % num_page);
            }
        });

//        int[] data = dbHelper.caloriesFor7Days();
//        int[] carbohydrate = dbHelper.carbohydrateCaloriesFor7Days();
//        int[] protein = dbHelper.proteinCaloriesFor7Days();
//        int[] fat = dbHelper.fatCaloriesFor7Days();

        // 단백질 : 파란색 지방 : 마젠타  탄수화물 : 빨간색
        int[] data = {2300, 5000, 3200, 2000, 2150, 2365, 7400};
        int[] weight = {70, 50, 53, 80, 103, 47, 60};
        int[] carbohydrate = {1000, 800, 900, 700, 600, 750, 850};
        int[] protein = {400, 700, 600, 450, 550, 400, 800};
        int[] fat = {900, 1200, 700, 850, 1000, 1215, 950};

        // 선그래프
        lineChartView.setWeightData(weight);

        // 데이터를 BarChartView에 설정하여 그래프를 그립니다.
        barChartView.setData(data, carbohydrate, protein, fat);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}