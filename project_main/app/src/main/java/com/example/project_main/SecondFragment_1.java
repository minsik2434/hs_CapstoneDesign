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

public class SecondFragment_1 extends Fragment {

    private BarChartView barChartView;
    private LineChartView lineChartView;
    private int currentIndex = 0;
    private ViewPager2 mSecondPager;
    private int pageCount = 4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_1, container, false);

//        mSecondPager = requireActivity().findViewById(R.id.second_viewpager);

        Button prevButton1 = view.findViewById(R.id.second_previous_button1);
        prevButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mSecondPager.getCurrentItem();
                if (currentItem < pageCount - 1) {
                    currentItem++;
                    mSecondPager.setCurrentItem(currentItem);
                }
            }
        });

        Button nextButton1 = view.findViewById(R.id.second_next_button1);
        nextButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mSecondPager.getCurrentItem();
                if (currentItem > 0) {
                    currentItem--;
                    mSecondPager.setCurrentItem(currentItem);
                }
            }
        });

        TextView dateTextView = view.findViewById(R.id.second_date_text1);
        SimpleDateFormat sdf = new SimpleDateFormat("MM월dd일", Locale.getDefault()); // 날짜 형식 변경
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // 이전 주의 월요일로 설정 (한 주 전 날짜)
        calendar.add(Calendar.DAY_OF_WEEK, -20);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String lastMondayDate = sdf.format(calendar.getTime());

        // 이전 주의 일요일로 설정 (한 주 전 날짜)
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        String lastSundayDate = sdf.format(calendar.getTime());

        dateTextView.setText(lastMondayDate + "~" + lastSundayDate);

        String dateStr = lastMondayDate + "~" + lastSundayDate;
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

        int[] weight = {70, 65, 60, 62, 64, 65, 70};
        int[] data = {2300, 5000, 3200, 2000, 2150, 2365, 7400};
        int[] carbohydrate = {1000, 800, 900, 700, 600, 750, 850};
        int[] protein = {400, 700, 600, 450, 550, 400, 800};
        int[] fat = {900, 1200, 700, 850, 1000, 1215, 950};

        lineChartView.setWeightData(weight);

        barChartView.setData(data, carbohydrate, protein, fat,dateArr);

        return view;
    }
}