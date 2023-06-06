package com.example.project_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalenderFragment extends Fragment {
    MaterialCalendarView materialCalendarView;
    TextView choice_date;
    private ListViewAdapter listViewAdapter;
    private ListView schedule_list;

    String[] date;
    int[] todayKcal;
    int[] suitableKcal;
    long now = System.currentTimeMillis();
    Date currentDate;
    SimpleDateFormat format;
    ScrollView scrollview_calender;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        date = new String[]{"2023-02-01", "2023-02-02", "2023-02-03"};
        todayKcal = new int[]{1000,1500,2000};
        suitableKcal = new int[]{1500,1600,1800};

        materialCalendarView = view.findViewById(R.id.calendarView);
        choice_date = view.findViewById(R.id.choice_date);
        //리스트뷰, 리스트뷰 어뎁터 초기화
        listViewAdapter = new ListViewAdapter();
        schedule_list = (ListView) view.findViewById(R.id.schedule_list);
        scrollview_calender = (ScrollView) view.findViewById(R.id.scrollview_calender);



        currentDate = new Date(now);
        format = new SimpleDateFormat("음력 MM월 dd일");
        choice_date.setText(format.format(currentDate));
        materialCalendarView.state().edit().setFirstDayOfWeek(Calendar.SUNDAY);
        materialCalendarView.state().edit().setMinimumDate(CalendarDay.from(2017, 0, 1));
        materialCalendarView.state().edit().setMaximumDate(CalendarDay.from(2030, 11, 31));
        materialCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS);

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new FutureDayDecorator()
        );


        for(int i =0;i<date.length;i++)
        {
            if(todayKcal[i]<=suitableKcal[i])
            {
                materialCalendarView.addDecorators(new BGDecorator(date[i]));
            }
            if(todayKcal[i]>suitableKcal[i])
            {
                materialCalendarView.addDecorators(new BGDecorator2(date[i]));
            }
        }


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                SimpleDateFormat format = new SimpleDateFormat("음력 MM월 dd일");
                String CalDate = format.format(date.getDate());
                choice_date.setText(CalDate);

                //어뎁터 초기화
                listViewAdapter.clearItem();
                //어뎁터에 아이템 추가
                listViewAdapter.addItem(R.drawable.ic_launcher_background,"라면",200);
                //리스트뷰에 어뎁터 set
                schedule_list.setAdapter(listViewAdapter);

            }
        });

        schedule_list.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollview_calender.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}