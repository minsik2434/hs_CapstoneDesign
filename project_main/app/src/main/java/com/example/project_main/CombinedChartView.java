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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CombinedChartView extends View {

    private static final int NUM_OF_DAYS = 7;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd", Locale.getDefault());

    private Paint weightPaint;
    private Paint textPaint;
    private Paint linePaint;
    private Paint dotPaint;

    private int[] data;
    private int maxData;
    private int[] contentsData;
    private int maxContentsData;

    private String[] dates;

    private int paddingStart = 20;
    private int paddingTop = 60;
    private int paddingEnd = 60;
    private int paddingBottom = 80;

    private int alphaValue = 100;

    public CombinedChartView(Context context) {
        super(context);
        init();
    }

    public CombinedChartView(Context context, @Nullable AttributeSet attrs) {
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
    }

    public void setData(int[] data, String[] dates, int[] contentsData) {
        this.data = data;
        this.dates = dates;
        this.contentsData = contentsData;

        this.maxData = findMax(data);
        this.maxContentsData = findMax(contentsData);

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

        if (data != null && dates != null && contentsData != null) {
            float width = getWidth() - paddingStart - paddingEnd;
            float height = getHeight() - paddingTop - paddingBottom;
            int numBars = data.length;
            float barWidth = width / (numBars * 4);
            float dataHeightRatio = height / maxData * 0.5f;
            float contentsDataHeightRatio = height / maxContentsData * 0.9f;

            float xAxisCenter = (paddingStart + width + paddingEnd) / 2;
            float yAxisCenter = (paddingTop + height + paddingBottom) / 2.3f;

            textPaint.setTextAlign(Paint.Align.CENTER);

            textPaint.setTextSize(40);
            canvas.drawText("칼로리", xAxisCenter, yAxisCenter, textPaint);

            textPaint.setTextSize(30);

            for (int i = 0; i < numBars; i++) {
                float barSpacing = 70;
                float x = i * (barWidth * 2 + barSpacing) + paddingStart + barSpacing / 2;

                float cornerRadius = 20;
                RectF rect = new RectF(x, getHeight() - paddingBottom - data[i] * dataHeightRatio, x + barWidth, getHeight() - paddingBottom);
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, weightPaint);

                float lineY = getHeight() - paddingBottom - contentsData[i] * contentsDataHeightRatio;

                if (i > 0) {
                    float prevX = (i - 1) * (barWidth * 2 + barSpacing) + paddingStart + barSpacing / 2 + barWidth;
                    float prevLineY = getHeight() - paddingBottom - contentsData[i - 1] * contentsDataHeightRatio;
                    float currentLineY = getHeight() - paddingBottom - contentsData[i] * contentsDataHeightRatio;

                    canvas.drawLine(prevX, prevLineY, x, currentLineY, linePaint);

                    String lineValueText = String.valueOf(contentsData[i]);
                    float lineValueX = x + barWidth / 2;
                    float lineValueY = currentLineY - 10;
                    canvas.drawText(lineValueText, lineValueX, lineValueY, textPaint);
                } else {
                    canvas.drawCircle(x + barWidth / 2, lineY, 6, dotPaint);
                    canvas.drawText(String.valueOf(contentsData[i]), x + barWidth / 2, lineY - 10, textPaint);
                }

                float dotX = x + barWidth / 2;
                canvas.drawCircle(dotX, lineY, 6, dotPaint);

                String valueText = String.valueOf(data[i]);
                float valueX = x + barWidth / 2;
                float valueY = getHeight() - paddingBottom - data[i] * dataHeightRatio - 10;
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