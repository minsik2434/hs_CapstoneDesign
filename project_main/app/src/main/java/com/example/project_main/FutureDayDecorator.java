package com.example.project_main;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class FutureDayDecorator implements DayViewDecorator {

    private CalendarDay date;

    public FutureDayDecorator() {
        date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return (day.getYear() >= date.getYear() && day.getMonth() >= date.getMonth() && day.getDay() > date.getDay() || day.getYear() >= date.getYear() && day.getMonth()>date.getMonth());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.parseColor("#d2d2d2")));
        view.setDaysDisabled(true);
    }
}

