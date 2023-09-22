package com.example.project_main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LineChartView extends View {

    private Paint linePaint;
    private Paint dotPaint;
    private Paint textPaint;
    private int[] weightData;
    private int maxWeightData;

    private int paddingStart = 60;
    private int paddingTop = 60;
    private int paddingEnd = 60;
    private int paddingBottom = 0;

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
        linePaint.setColor(Color.GREEN);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);

        dotPaint = new Paint();
        dotPaint.setColor(Color.GREEN);
        dotPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setWeightData(int[] weightData) {
        this.weightData = weightData;
        this.maxWeightData = findMax(weightData);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (weightData != null) {
            float width = getWidth() - paddingStart - paddingEnd;
            float height = getHeight() - paddingTop - paddingBottom;
            float barWidth = width / (weightData.length * 2);
            float weightDataHeightRatio = height / maxWeightData * 0.8f;

            float gapBetweenBars = barWidth * 2.0f;

            Path weightPath = new Path();
            for (int i = 0; i < weightData.length; i++) {
                float x = i * gapBetweenBars + barWidth / 2 + paddingStart;
                float dotY = getHeight() - paddingBottom - weightData[i] * weightDataHeightRatio;
                if (i == 0) {
                    weightPath.moveTo(x, dotY);
                } else {
                    weightPath.lineTo(x, dotY);
                }

                canvas.drawCircle(x, dotY, 6, dotPaint);

                String valueText = String.valueOf(weightData[i]);
                canvas.drawText(valueText, x, dotY - 10, textPaint);
            }

            canvas.drawPath(weightPath, linePaint);
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