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
            int numBars = data.length;
            float barWidth = width / (numBars * 4); // 막대그래프의 너비 계산
            float dataHeightRatio = height / maxData * 1.5f;

            for (int i = 0; i < numBars; i++) {
                float barSpacing = 70; // 막대 그래프들 사이의 원하는 간격 설정
                float x = i * (barWidth * 2 + barSpacing) + paddingStart + barSpacing / 2; // x 위치 계산 (가운데 정렬)

                float carbohydrateTop = getHeight() - paddingBottom - carbohydrateData[i] * dataHeightRatio;
                float proteinTop = carbohydrateTop - proteinData[i] * dataHeightRatio;
                float fatTop = proteinTop - fatData[i] * dataHeightRatio;

                canvas.drawRect(x, carbohydrateTop, x + barWidth, getHeight() - paddingBottom, carbohydratePaint);
                canvas.drawRect(x, proteinTop, x + barWidth, carbohydrateTop, proteinPaint);

                Path fatTopPath = new Path();
                fatTopPath.moveTo(x, fatTop);
                fatTopPath.lineTo(x + barWidth, fatTop);
                fatTopPath.lineTo(x + barWidth, proteinTop);
                fatTopPath.lineTo(x, proteinTop);
                fatTopPath.close();

                canvas.drawPath(fatTopPath, fatPaint);

                String valueText = String.valueOf(data[i]);
                float valueX = x + barWidth / 2;
                float valueY = fatTop - 10;
                canvas.drawText(valueText, valueX, valueY, textPaint);

                String dateText = dates[i];
                float dateX = x + barWidth / 2;
                float dateY = getHeight() - paddingBottom + 40;

                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(dateText, dateX, dateY, textPaint);
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