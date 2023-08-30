package com.example.project_main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        carbohydratePaint.setColor(Color.RED);

        proteinPaint = new Paint();
        proteinPaint.setColor(Color.BLUE);

        fatPaint = new Paint();
        fatPaint.setColor(Color.MAGENTA);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setData(int[] data, int[] carbohydrateData, int[] proteinData, int[] fatData) {
        this.data = data;
        this.carbohydrateData = carbohydrateData;
        this.proteinData = proteinData;
        this.fatData = fatData;

        this.maxData = findMax(data);

        this.dates = calculateDates();
        invalidate();
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
            float barWidth = width / (data.length * 2); // 막대그래프를 더 얇게 만듦
            float dataHeightRatio = height / maxData * 2.0f; // 막대그래프 세로 길이

            float gapBetweenBars = barWidth * 2.0f; // 막대 사이의 간격을 더 넓게 조정

            for (int i = 0; i < data.length; i++) {
                float x = i * gapBetweenBars + barWidth / 2 + paddingStart; // X 축 위치 계산
                // 칼로리 데이터를 분할하여 탄수화물, 단백질, 지방 데이터로 표시
                float carbohydrateTop = getHeight() - paddingBottom - carbohydrateData[i] * dataHeightRatio;
                float proteinTop = carbohydrateTop - proteinData[i] * dataHeightRatio;
                float fatTop = proteinTop - fatData[i] * dataHeightRatio;

                // Draw the bars for each data type
                canvas.drawRect(x - barWidth / 2, carbohydrateTop, x + barWidth / 2, getHeight() - paddingBottom, carbohydratePaint);
                canvas.drawRect(x - barWidth / 2, proteinTop, x + barWidth / 2, carbohydrateTop, proteinPaint);
                canvas.drawRect(x - barWidth / 2, fatTop, x + barWidth / 2, proteinTop, fatPaint);

                // Draw value text for the data
                String valueText = String.valueOf(data[i]);
                float valueX = x;

                float valueY = fatTop - 10; // 막대의 상단에 표시
                canvas.drawText(valueText, valueX, valueY, textPaint);
            }

            // Draw date text
            for (int i = 0; i < dates.length; i++) {
                String dateText = dates[i];
                float x = i * gapBetweenBars + barWidth / 2 + paddingStart; // X 축 위치 계산
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

        // Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measuredWidth = Math.min(desiredWidth, widthSize);
        } else {
            measuredWidth = desiredWidth;
        }

        // Measure Height
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