package com.example.project_main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LineChartView extends View {

    private Paint linePaint;
    private Paint textPaint;
    private Paint dotPaint;

    private int[] contentsData;
    private int maxContentsData;

    private int paddingStart = 70;
    private int paddingTop = 60;
    private int paddingEnd = 60;
    private int paddingBottom = 80;

    private int alphaValue = 100;

    private int lineColor = Color.GREEN;

    public LineChartView(Context context) {
        super(context);
        init();
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(lineColor); // 선의 색상을 기본 색상으로 설정
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);

        dotPaint = new Paint();
        dotPaint.setColor(Color.BLACK); // 점의 색상을 검은색으로 설정
        dotPaint.setStyle(Paint.Style.FILL);
    }

    public void setData(int[] contentsData) {
        this.contentsData = contentsData;
        this.maxContentsData = findMax(contentsData);

        invalidate();
    }

    public void setLineColor(int color) {
        lineColor = color;
        linePaint.setColor(lineColor);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (contentsData != null) {
            float width = getWidth() - paddingStart - paddingEnd;
            float height = getHeight() - paddingTop - paddingBottom;
            int numPoints = contentsData.length;

            float pointSpacing = width / (numPoints - 1) * 0.89f;

            float xOffset = 50f;

            for (int i = 0; i < numPoints; i++) {
                float x = i * pointSpacing + paddingStart + xOffset;
                float y = getHeight() - paddingBottom - contentsData[i] * (height / maxContentsData);

                canvas.drawCircle(x, y, 6, dotPaint);

                if (i > 0) {
                    float prevX = (i - 1) * pointSpacing + paddingStart + xOffset;
                    float prevY = getHeight() - paddingBottom - contentsData[i - 1] * (height / maxContentsData);
                    canvas.drawLine(prevX, prevY, x, y, linePaint);
                } else {
                    canvas.drawCircle(x, y, 6, dotPaint);
                }

                String valueText = String.valueOf(contentsData[i]);
                float valueX = x;
                float valueY = y - 10;
                canvas.drawText(valueText, valueX, valueY, textPaint);
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
}