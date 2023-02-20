package com.example.project_main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StaticsFragment extends Fragment {

    MaterialCalendarView materialCalendarView;
    TextView choice_date;
    ListView schedule_list;
    private ListViewAdapter adapter;

    String[] date;
    int[] todayKcal;
    int[] suitableKcal;
    long now = System.currentTimeMillis();
    Date currentDate;
    SimpleDateFormat format;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statics, container, false);

        date = new String[]{"2023-02-01", "2023-02-02", "2023-02-03"};
        todayKcal = new int[]{1000,1500,2000};
        suitableKcal = new int[]{1500,1600,1800};

        materialCalendarView = view.findViewById(R.id.calendarView);
        choice_date = view.findViewById(R.id.choice_date);
        schedule_list = view.findViewById(R.id.schedule_list);
        adapter = new ListViewAdapter();

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

//                adapter.addItem(new ListViewItem(R.drawable.cancel_icon,"라면",200));
//                schedule_list.setAdapter(adapter);

            }
        });
        return view;
    }
}