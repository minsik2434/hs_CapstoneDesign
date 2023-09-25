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
    private int first_num_page = 3;
    private CircleIndicator3 mIndicator;
    private MyDatabaseHelper dbHelper;

    private ViewPager2 mSecondPager;
    private SecondBannerAdapter secondPagerAdapter;
    private int second_num_page = 4;
    private CircleIndicator3 mSecondIndicator;
    private int currentPageIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());

        // 첫 번째 ViewPager2 설정
        mPager = view.findViewById(R.id.viewpager);
        pagerAdapter = new BannerAdapter(this, first_num_page);
        mPager.setAdapter(pagerAdapter);
        mIndicator = view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(first_num_page, 0);
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(540);
        mPager.setOffscreenPageLimit(3);


        // 두 번째
        mSecondPager = view.findViewById(R.id.second_viewpager);
        secondPagerAdapter = new SecondBannerAdapter(requireActivity(), second_num_page);
        mSecondPager.setAdapter(secondPagerAdapter);
        mSecondPager.setUserInputEnabled(false); // 두 번째 ViewPager2의 스와이프 비활성화
        mSecondIndicator = view.findViewById(R.id.second_indicator);
        mSecondIndicator.setViewPager(mSecondPager);
        mSecondIndicator.createIndicators(second_num_page, 0);
        mSecondPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}