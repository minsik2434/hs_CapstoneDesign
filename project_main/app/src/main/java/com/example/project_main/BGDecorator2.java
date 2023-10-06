package com.example.project_main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.text.SimpleDateFormat;

public class BGDecorator2 implements DayViewDecorator {

    private final String date;

    public BGDecorator2(String date) {
        this.date = date;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String calDate = format.format(day.getDate());
        return calDate.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new EventDecorator());
    }

    private class EventDecorator implements LineBackgroundSpan {
        @Override
        public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence charSequence, int start, int end, int lineNum) {
            // 경계선 색상 설정
            paint.setColor(Color.RED);
            // 경계선 두께 설정
            int strokeWidth = 2; // 원하는 두께로 조절하세요
            // 날짜 위쪽 경계선 그리기
            canvas.drawRect(left, top, right, top + strokeWidth, paint);
            // 날짜 아래쪽 경계선 그리기
            canvas.drawRect(left, bottom - strokeWidth, right, bottom, paint);
            // 날짜 왼쪽 경계선 그리기
            canvas.drawRect(left, top, left + strokeWidth, bottom, paint);
            // 날짜 오른쪽 경계선 그리기
            canvas.drawRect(right - strokeWidth, top, right, bottom, paint);
        }
    }
}

