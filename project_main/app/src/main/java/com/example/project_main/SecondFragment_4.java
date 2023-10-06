package com.example.project_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecondFragment_4 extends Fragment {

    private BarChartView barChartView;
    private LineChartView lineChartView;
    private int currentIndex = 0;
    private ViewPager2 mSecondPager;
    private int pageCount = 4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_4, container, false);

//        mSecondPager = requireActivity().findViewById(R.id.second_viewpager);

        Button prevButton4 = view.findViewById(R.id.second_previous_button4);
        prevButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mSecondPager.getCurrentItem();
                if (currentItem < pageCount - 1) {
                    currentItem++;
                    mSecondPager.setCurrentItem(currentItem);
                }
            }
        });

        Button nextButton4 = view.findViewById(R.id.second_next_button4);
        nextButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mSecondPager.getCurrentItem();
                if (currentItem > 0) {
                    currentItem--;
                    mSecondPager.setCurrentItem(currentItem);
                }
            }
        });

        TextView dateTextView = view.findViewById(R.id.second_date_text4);
        SimpleDateFormat sdf = new SimpleDateFormat("MM월dd일", Locale.getDefault()); // 날짜 형식 변경
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 현재 주의 월요일로 설정
        String mondayDate = sdf.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK, 6); // 6일을 더하여 주의 일요일로 설정
        String sundayDate = sdf.format(calendar.getTime());

        dateTextView.setText(mondayDate + "~" + sundayDate);

        String dateStr = mondayDate + "~" + sundayDate;
        String[] dateRange = dateStr.split("~");

        String startDateStr = dateRange[0];
        String endDateStr = dateRange[1];

        startDateStr = startDateStr.replaceAll("[^0-9]", "");
        endDateStr = endDateStr.replaceAll("[^0-9]", "");

        int startDate = Integer.parseInt(startDateStr);
        int endDate = Integer.parseInt(endDateStr);

        String[] dateArr = new String[7];

        for (int i = 0; i < 7; i++) {
            int month = startDate / 100;
            int day = startDate % 100;
            dateArr[i] = String.format("%d/%d", month, day);
            startDate++;
        }

        barChartView = view.findViewById(R.id.barChart);

        lineChartView = view.findViewById(R.id.lineChart);

        int[] weight = {75, 68, 72, 80, 63, 68, 71};
        int[] data = {2500, 4800, 3100, 2200, 2400, 2550, 7000};
        int[] carbohydrate = {1100, 850, 920, 750, 620, 800, 880};
        int[] protein = {450, 720, 610, 480, 540, 410, 820};
        int[] fat = {950, 1100, 720, 880, 1050, 1300, 980};
        lineChartView.setWeightData(weight);
        barChartView.setData(data, carbohydrate, protein, fat, dateArr);

        return view;
    }
}