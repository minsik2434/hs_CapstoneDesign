package com.example.project_main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomTextDrawer extends View {

    private Paint linePaint;
    private Paint textPaint;

    public CustomTextDrawer(Context context) {
        super(context);
        init();
    }

    public CustomTextDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.LEFT); // 텍스트를 왼쪽 정렬로 변경
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 화면의 중앙 좌표를 계산합니다.
        float centerY = getHeight() / 2;

        // 각 선과 텍스트를 그립니다.
        drawLineWithText(canvas, 60, centerY, 160, centerY, "몸무게", Color.GREEN); // 초록색 선
        drawLineWithText(canvas, 260, centerY, 360, centerY, "탄수화물", Color.RED); // 초록색 선
        drawLineWithText(canvas, 510, centerY, 610, centerY, "단백질", Color.BLUE); // 파란색 선
        drawLineWithText(canvas, 710, centerY, 810, centerY, "지방", Color.MAGENTA); // 마젠타색 선
    }

    private void drawLineWithText(Canvas canvas, float startX, float startY, float endX, float endY, String labelText, int lineColor) {
        linePaint.setColor(lineColor);

        canvas.drawLine(startX, startY, endX, endY, linePaint);

        // 텍스트의 위치를 선 오른쪽에 맞추고 여백을 추가합니다.
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);

        float textWidth = textPaint.measureText(labelText); // 텍스트의 너비 계산
        float textX = endX + 10; // 선 오른쪽에 위치
        float textY = (startY + endY) / 2 + textPaint.getTextSize() / 2;

        canvas.drawText(labelText, textX, textY, textPaint);
    }
}