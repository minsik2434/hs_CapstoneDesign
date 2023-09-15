package com.example.project_main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BarChartView extends View {

    private static final int NUM_OF_DAYS = 7;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd", Locale.getDefault());

    private Paint carbohydratePaint;
    private Paint proteinPaint;
    private Paint fatPaint;
    private Paint textPaint;

    private int[] data;
    private int[] carbohydrateData;
    private int[] proteinData;
    private int[] fatData;
    private int maxData;

    private String[] dates;

    private int paddingStart = 90;
    private int paddingTop = 60;
    private int paddingEnd = 60;
    private int paddingBottom = 80;

    // 투명도 변수 추가
    private int alphaValue = 100; // 0 (완전 투명) to 255 (완전 불투명)

    public BarChartView(Context context) {
        super(context);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        carbohydratePaint = new Paint();
        carbohydratePaint.setColor(Color.argb(alphaValue, 255, 0, 0)); // 빨간색

        proteinPaint = new Paint();
        proteinPaint.setColor(Color.argb(alphaValue, 0, 0, 255)); // 파란색

        fatPaint = new Paint();
        fatPaint.setColor(Color.argb(alphaValue, 255, 0, 255)); // 마젠타색
        fatPaint.setPathEffect(new CornerPathEffect(10)); // 지방 바에 둥근 모서리 효과 적용

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setData(int[] data, int[] carbohydrateData, int[] proteinData, int[] fatData, String[] dates) {
        this.data = data;
        this.carbohydrateData = carbohydrateData;
        this.proteinData = proteinData;
        this.fatData = fatData;
        this.dates = dates; // 날짜 배열 설정

        this.maxData = findMax(data);

        invalidate(); // 다시 그리도록 요청
    }

    private String[] calculateDates() {
        String[] result = new String[NUM_OF_DAYS];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        for (int i = NUM_OF_DAYS - 1; i >= 0; i--) {
            Date date = calendar.getTime();
            result[i] = DATE_FORMAT.format(date);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data != null && dates != null && carbohydrateData != null && proteinData != null && fatData != null) {
            float width = getWidth() - paddingStart - paddingEnd;
            float height = getHeight() - paddingTop - paddingBottom;
            float barWidth = width / (data.length * 2.5f);
            float dataHeightRatio = height / maxData * 1.5f;
            float gapBetweenBars = barWidth * 2.5f;

            for (int i = 0; i < data.length; i++) {
                float x = i * gapBetweenBars + barWidth / 2 + paddingStart;

                float carbohydrateTop = getHeight() - paddingBottom - carbohydrateData[i] * dataHeightRatio;
                float proteinTop = carbohydrateTop - proteinData[i] * dataHeightRatio;
                float fatTop = proteinTop - fatData[i] * dataHeightRatio;

                // 칼로리 데이터 그리기
                canvas.drawRect(x - barWidth / 2, carbohydrateTop, x + barWidth / 2, getHeight() - paddingBottom, carbohydratePaint);
                canvas.drawRect(x - barWidth / 2, proteinTop, x + barWidth / 2, carbohydrateTop, proteinPaint);
                // 지방 바의 상단 두 모서리에 둥근 모양 효과 적용
                Path fatTopPath = new Path();
                fatTopPath.moveTo(x - barWidth / 2, fatTop);
                fatTopPath.lineTo(x + barWidth / 2, fatTop);
                fatTopPath.lineTo(x + barWidth / 2, proteinTop);
                fatTopPath.lineTo(x - barWidth / 2, proteinTop);
                fatTopPath.close();

                canvas.drawPath(fatTopPath, fatPaint);

                // 데이터 값 표시
                String valueText = String.valueOf(data[i]);
                float valueX = x;
                float valueY = fatTop - 10;

                canvas.drawText(valueText, valueX, valueY, textPaint);
            }

            // 날짜 텍스트 그리기
            for (int i = 0; i < dates.length; i++) {
                String dateText = dates[i];
                float x = i * gapBetweenBars + barWidth / 2 + paddingStart;
                float dateY = getHeight() - paddingBottom + 40;
                canvas.drawText(dateText, x, dateY, textPaint);

            }
        }
    }

    private int findMax(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = getSuggestedMinimumWidth() + paddingStart + paddingEnd;
        int desiredHeight = getSuggestedMinimumHeight() + paddingTop + paddingBottom;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int measuredWidth;
        int measuredHeight;

        if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measuredWidth = Math.min(desiredWidth, widthSize);
        } else {
            measuredWidth = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measuredHeight = Math.min(desiredHeight, heightSize);
        } else {
            measuredHeight = desiredHeight;
        }

        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}