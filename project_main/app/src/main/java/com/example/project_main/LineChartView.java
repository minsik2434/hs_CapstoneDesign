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

            float barWidth = width / (weightData.length * 2); // 막대그래프와 같은 X축을 사용하기 위해 수정
            float weightDataHeightRatio = height / maxWeightData * 0.8f;

            float gapBetweenBars = barWidth * 2.0f; // 막대와 선 사이의 간격을 더 넓게 조정

            Path weightPath = new Path();
            for (int i = 0; i < weightData.length; i++) {
                float x = i * gapBetweenBars + barWidth / 2 + paddingStart; // X 축 위치 계산
                float dotY = getHeight() - paddingBottom - weightData[i] * weightDataHeightRatio;
                if (i == 0) {
                    weightPath.moveTo(x, dotY);
                } else {
                    weightPath.lineTo(x, dotY);
                }

                // Draw circle at each weight data point
                canvas.drawCircle(x, dotY, 6, dotPaint);

                // Display the value next to the data point
                String valueText = String.valueOf(weightData[i]);
                canvas.drawText(valueText, x, dotY - 10, textPaint);
            }

            // Draw line connecting weight data points
            canvas.drawPath(weightPath, linePaint);

            // Draw a slightly thicker green line just above the "몸무게" text
            float greenLineStartX = paddingStart + 20;
            float weightLabelY = paddingTop + 50; // Y position of the "몸무게" text
            float greenLineStartY = weightLabelY - 10; // Slightly above the "몸무게" text
            float greenLineEndX = paddingStart + 80;
            float greenLineEndY = weightLabelY - 10; // Slightly above the "몸무게" text
            linePaint.setStrokeWidth(5); // Set thicker line width
            canvas.drawLine(greenLineStartX, greenLineStartY, greenLineEndX, greenLineEndY, linePaint);

            // Draw "몸무게" text
            String weightLabel = "몸무게";
            float weightLabelX = greenLineEndX + 50; // Adjust the horizontal position
            canvas.drawText(weightLabel, weightLabelX, weightLabelY, textPaint);

            // Draw a red line just above the "몸무게" text
            float redLineStartX = paddingStart + 190; // X position of the red line start
            float redLineStartY = weightLabelY - 10; // Slightly above the "몸무게" text
            float redLineEndX = paddingStart + 250; // X position of the red line end
            float redLineEndY = weightLabelY - 10; // Slightly above the "몸무게" text
            linePaint.setColor(Color.RED); // Set line color to red
            canvas.drawLine(redLineStartX, redLineStartY, redLineEndX, redLineEndY, linePaint);

            // Draw "탄수화물" text
            String carbLabel = "탄수화물";
            float carbLabelX = redLineEndX + 70; // Adjust the horizontal position
            canvas.drawText(carbLabel, carbLabelX, weightLabelY, textPaint);


            // Draw a blue line just above the "단백질" text
            float blueLineStartX = paddingStart + 400; // X position of the blue line start (Adjusted)
            float blueLineStartY = weightLabelY - 10; // Slightly above the "단백질" text
            float blueLineEndX = paddingStart + 460; // X position of the blue line end (Adjusted)
            float blueLineEndY = weightLabelY - 10; // Slightly above the "단백질" text
            linePaint.setColor(Color.BLUE); // Set line color to blue
            canvas.drawLine(blueLineStartX, blueLineStartY, blueLineEndX, blueLineEndY, linePaint);

            // Draw "단백질" text
            String proteinLabel = "단백질";
            float proteinLabelX = blueLineEndX + 50; // Adjust the horizontal position
            canvas.drawText(proteinLabel, proteinLabelX, weightLabelY, textPaint);

            // Draw a magenta line just above the "지방" text
            float magentaLineStartX = paddingStart + 580; // X position of the magenta line start (Adjusted)
            float magentaLineStartY = weightLabelY - 10; // Slightly above the "지방" text
            float magentaLineEndX = paddingStart + 640; // X position of the magenta line end (Adjusted)
            float magentaLineEndY = weightLabelY - 10; // Slightly above the "지방" text
            linePaint.setColor(Color.MAGENTA); // Set line color to magenta
            canvas.drawLine(magentaLineStartX, magentaLineStartY, magentaLineEndX, magentaLineEndY, linePaint);

            // Draw "지방" text
            String fatLabel = "지방";
            float fatLabelX = magentaLineEndX + 50; // Adjust the horizontal position
            canvas.drawText(fatLabel, fatLabelX, weightLabelY, textPaint);


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