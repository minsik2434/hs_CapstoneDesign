package com.example.project_main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StatisticsWeek extends Fragment {

    private Button ingredientCheckButton_Week;
    private CombinedChartView combinedChartView;
    private MyDatabaseHelper dbHelper;

    private String[] dateArr;
    private int[] data;
    private int[] contentsData;

    BarChartView barChartView;
    LineChartView lineChartView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_week, container, false);

        ingredientCheckButton_Week = view.findViewById(R.id.ingredientCheckButton_Week);
        dbHelper = new MyDatabaseHelper(requireContext());

        dateArr = getWeekDateArray(); // 현재 주부터 6주 전까지의 날짜를 가져옵니다

        data = new int[7];
        contentsData = new int[7];

        for (int i = 0; i < 7; i++) {
            data[i] = dbHelper.getTotalCaloriesForDateRange(dateArr[i]);
            contentsData[i] = dbHelper.getTotalCarbohydratesForDateRange(dateArr[i]);
        }

        barChartView = view.findViewById(R.id.barChartView);
        lineChartView = view.findViewById(R.id.lineChartView);

        barChartView.setTextSize(22);

        barChartView.setData(data, dateArr);
        lineChartView.setData(contentsData);

        ingredientCheckButton_Week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.ingredient_check_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.carbohydrate_menu:
                        ingredientCheckButton_Week.setText("탄수화물");
                        dateArr = getWeekDateArray();
                        data = new int[7];
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForDateRange(dateArr[i]);
                            contentsData[i] = dbHelper.getTotalCarbohydratesForDateRange(dateArr[i]);
                        }
                        lineChartView.setLineColor(Color.GREEN);
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.protein_menu:
                        ingredientCheckButton_Week.setText("단백질");
                        dateArr = getWeekDateArray();
                        data = new int[7];
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForDateRange(dateArr[i]);
                            contentsData[i] = dbHelper.getTotalProteinForDateRange(dateArr[i]);
                        }
                        lineChartView.setLineColor(Color.BLUE);
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.fat_menu:
                        ingredientCheckButton_Week.setText("지방");
                        dateArr = getWeekDateArray();
                        data = new int[7];
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForDateRange(dateArr[i]);
                            contentsData[i] = dbHelper.getTotalFatForDateRange(dateArr[i]);
                        }
                        int brownColor = Color.rgb(139, 69, 19); // 갈색 색상 코드
                        lineChartView.setLineColor(brownColor);
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.saccharides_menu:
                        ingredientCheckButton_Week.setText("당류");
                        dateArr = getWeekDateArray();
                        data = new int[7];
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForDateRange(dateArr[i]);
                            contentsData[i] = dbHelper.getTotalSugarsForDateRange(dateArr[i]);
                        }
                        lineChartView.setLineColor(Color.RED);
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.sodium_menu:
                        ingredientCheckButton_Week.setText("나트륨");
                        dateArr = getWeekDateArray();
                        data = new int[7];
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForDateRange(dateArr[i]);
                            contentsData[i] = dbHelper.getTotalSodiumForDateRange(dateArr[i]);
                        }
                        lineChartView.setLineColor(Color.LTGRAY);
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.cholesterol_menu:
                        ingredientCheckButton_Week.setText("콜레스테롤");
                        dateArr = getWeekDateArray();
                        data = new int[7];
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForDateRange(dateArr[i]);
                            contentsData[i] = dbHelper.getTotalCholesterolForDateRange(dateArr[i]);
                        }
                        lineChartView.setLineColor(Color.MAGENTA);
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.transfat_menu:
                        ingredientCheckButton_Week.setText("트랜스지방");
                        dateArr = getWeekDateArray();
                        data = new int[7];
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForDateRange(dateArr[i]);
                            contentsData[i] = dbHelper.getTotalTransFatForDateRange(dateArr[i]);
                        }
                        lineChartView.setLineColor(Color.BLACK);
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.fattyacid_menu:
                        ingredientCheckButton_Week.setText("포화지방산");
                        dateArr = getWeekDateArray();
                        data = new int[7];
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForDateRange(dateArr[i]);
                            contentsData[i] = dbHelper.getTotalSaturatedFatForDateRange(dateArr[i]);
                        }
                        int orangeColor = Color.rgb(255, 165, 0); // 주황색 색상 코드
                        lineChartView.setLineColor(orangeColor);
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    public static String[] getWeekDateArray() {
        String[] dateArr = new String[7];

        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");

        for (int i = 0; i < 7; i++) {

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            calendar.add(Calendar.DAY_OF_WEEK, -dayOfWeek + 2);
            Date weekStart = calendar.getTime();

            calendar.add(Calendar.DAY_OF_WEEK, 6);
            Date weekEnd = calendar.getTime();

            String weekStartString = dateFormat.format(weekStart);
            String weekEndString = dateFormat.format(weekEnd);

            dateArr[6 - i] = weekStartString + "~" + weekEndString;

            calendar.setTime(weekStart);
            calendar.add(Calendar.DAY_OF_WEEK, -7);
        }

        return dateArr;
    }
}