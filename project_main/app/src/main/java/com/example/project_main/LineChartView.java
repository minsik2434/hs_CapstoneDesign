package com.example.project_main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LineChartView extends View {

    private Paint linePaint;
    private Paint dotPaint;
    private Paint textPaint; // Add this line to declare the textPaint variable
    private int[] weightData;
    private int maxWeightData;

    private int paddingStart = 60;
    private int paddingTop = 60;
    private int paddingEnd = 60;
    private int paddingBottom = 80;

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

        textPaint = new Paint(); // Initialize the textPaint variable
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

            float barWidth = width / weightData.length;
            float weightDataHeightRatio = height / maxWeightData * 0.8f;

            float gapBetweenBars = barWidth * 0.1f;

            Path weightPath = new Path();
            for (int i = 0; i < weightData.length; i++) {
                float dotX = paddingStart + i * barWidth + barWidth / 2;
                float dotY = getHeight() - paddingBottom - weightData[i] * weightDataHeightRatio;
                if (i == 0) {
                    weightPath.moveTo(dotX, dotY);
                } else {
                    weightPath.lineTo(dotX, dotY);
                }

                // Draw circle at each weight data point
                canvas.drawCircle(dotX, dotY, 6, dotPaint);

                // Draw value text for the weight data
                String weightText = String.valueOf(weightData[i]) + " kg";
                Rect weightBounds = new Rect();
                textPaint.getTextBounds(weightText, 0, weightText.length(), weightBounds);
                float weightX = dotX;
                float weightY = dotY - weightBounds.height() * 0.7f; // 텍스트를 약간 위로 이동하여 데이터 포인트와 가깝게
                canvas.drawText(weightText, weightX, weightY, textPaint);
            }

            // Draw line connecting weight data points
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