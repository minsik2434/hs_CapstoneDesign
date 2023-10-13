package com.example.project_main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class BarChartView extends View {

    private static final int NUM_OF_DAYS = 7;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd", Locale.getDefault());

    private Paint weightPaint;
    private Paint textPaint;
    private Paint linePaint;
    private Paint dotPaint;
    private Paint barPaint;

    private int[] data;
    private int maxData;
    private String[] dates;

    private int paddingStart = 70;
    private int paddingTop = 60;
    private int paddingEnd = 60;
    private int paddingBottom = 80;

    private int alphaValue = 100;

    public BarChartView(Context context) {
        super(context);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        weightPaint = new Paint();
        weightPaint.setColor(Color.argb(alphaValue, 0, 0, 255));

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);

        linePaint = new Paint();
        linePaint.setColor(Color.GREEN);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);

        dotPaint = new Paint();
        dotPaint.setColor(Color.GREEN);
        dotPaint.setStyle(Paint.Style.FILL);

        barPaint = new Paint();
        barPaint.setColor(Color.argb(alphaValue, 0, 0, 255));
    }

    public void setData(int[] data, String[] dates) {
        this.data = data;
        this.dates = dates;

        this.maxData = findMax(data);

        invalidate();
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data != null && dates != null) {
            float width = getWidth() - paddingStart - paddingEnd;
            float height = getHeight() - paddingTop - paddingBottom;
            int numBars = data.length;
            float barWidth = width / (numBars * 4);
            float dataHeightRatio = height / maxData * 0.8f;

            float xAxisCenter = (paddingStart + width + paddingEnd) / 2;
            float yAxisCenter = (paddingTop + height + paddingBottom) / 2.4f;

            float barSpacing = 70; // 바 간격 추가

            // Draw the bar chart (막대 그래프 그리기)
            for (int i = 0; i < numBars; i++) {
                float x = i * (barWidth * 2 + barSpacing) + paddingStart + barSpacing / 2;
                float cornerRadius = 20;
                RectF rect = new RectF(x, getHeight() - paddingBottom - data[i] * dataHeightRatio, x + barWidth, getHeight() - paddingBottom);
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, barPaint);

                float valueTextX = x + barWidth / 2;
                float valueTextY = getHeight() - paddingBottom - data[i] * dataHeightRatio - 10;
                canvas.drawText(String.valueOf(data[i]), valueTextX, valueTextY, textPaint);

                String dateText = dates[i];
                float dateTextX = x + barWidth / 2;
                float dateTextY = getHeight() - paddingBottom + 40;

                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(dateText, dateTextX, dateTextY, textPaint);
            }
        }
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