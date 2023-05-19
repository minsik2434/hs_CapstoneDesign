package com.example.project_main;

import android.graphics.Color;
import android.text.style.BackgroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.text.SimpleDateFormat;

public class BGDecorator implements DayViewDecorator {

    private final String date;

    public BGDecorator(String date) {
        this.date = date;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String CalDate = format.format(day.getDate());
//        Log.i(CalDate,"CalDate값");
//        Log.i(date,"date값");
        return CalDate.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new BackgroundColorSpan(Color.GREEN));
    }
}
