package com.example.project_main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

    private Paint linePaint;
    private Paint dotPaint;
    private Paint textPaint;
    private int[] data;
    private int[] weightData;
    private String[] dates;

    private int paddingStart = 60;
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
        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);

        dotPaint = new Paint();
        dotPaint.setColor(Color.RED);
        dotPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setData(int[] data, int[] weightData) {
        this.data = data;
        this.weightData = weightData;
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

        if (data != null && dates != null && weightData != null) {
            float width = getWidth() - paddingStart - paddingEnd;
            float height = getHeight() - paddingTop - paddingBottom;

            float barWidth = width / data.length;
            int maxData = findMax(data);
            float dataHeightRatio = height / maxData;

            int maxWeightData = findMax(weightData);
            float weightDataHeightRatio = height / maxWeightData;

            for (int i = 0; i < data.length; i++) {
                float left = paddingStart + i * barWidth;
                float right = left + barWidth;
                float bottom = getHeight() - paddingBottom;
                float top = bottom - data[i] * dataHeightRatio;

                // Draw the bar (for the data)
                Paint barPaint = new Paint();
                barPaint.setColor(Color.BLUE);
                canvas.drawRect(left, top, right, bottom, barPaint);

                // Draw value text for the data (at the middle of the bar)
                String valueText = String.valueOf(data[i]);
                Rect valueBounds = new Rect();
                textPaint.getTextBounds(valueText, 0, valueText.length(), valueBounds);
                float valueX = (left + right) / 2;
                float valueY = top - valueBounds.height() / 2 - 10;
                canvas.drawText(valueText, valueX, valueY, textPaint);

                // Draw circle at each weight data point
                float dotX = (left + right) / 2;
                float dotY = getHeight() - paddingBottom - weightData[i] * weightDataHeightRatio;
                canvas.drawCircle(dotX, dotY, 6, dotPaint);

                // Draw line connecting weight data points
                if (i > 0) {
                    float prevX = paddingStart + (i - 1) * barWidth + barWidth / 2;
                    float prevY = getHeight() - paddingBottom - weightData[i - 1] * weightDataHeightRatio;
                    float currX = dotX;
                    float currY = dotY;
                    canvas.drawLine(prevX, prevY, currX, currY, linePaint);
                }

                // Draw value text for the weight data (above the line)
                String weightText = String.valueOf(weightData[i]);
                Rect weightBounds = new Rect();
                textPaint.getTextBounds(weightText, 0, weightText.length(), weightBounds);
                float weightX = dotX;
                float weightY = dotY - weightBounds.height() - 5;
                canvas.drawText(weightText, weightX, weightY, textPaint);

                // Draw date text
                String dateText = dates[i];
                Rect dateBounds = new Rect();
                textPaint.getTextBounds(dateText, 0, dateText.length(), dateBounds);
                float dateX = dotX;
                float dateY = getHeight() - paddingBottom + dateBounds.height() + 10;
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