package com.example.project_main;

import android.app.FragmentManager;
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
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StatisticsDay extends Fragment {

    private Button ingredientCheckButton_Week;
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

        Calendar calendar = Calendar.getInstance();


        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.getDefault());

        String[] dateArr = new String[7];

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        barChartView = view.findViewById(R.id.barChartView);
        lineChartView = view.findViewById(R.id.lineChartView);

        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek + 2);

        for (int i = 0; i < 7; i++) {
            dateArr[i] = sdf.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        data = new int[7];
        contentsData = new int[7];

        for(int i=0;i<7;i++){
            data[i] = dbHelper.getTotalCaloriesConsumedOnDate(dateArr[i]);
        }
        for(int i=0;i<7;i++){
            contentsData[i] = dbHelper.getTotalCarbohydratesConsumedOnDate(dateArr[i]);
        }

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
                        dateArr = getThisWeekDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesConsumedOnDate(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalCarbohydratesConsumedOnDate(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.protein_menu:
                        ingredientCheckButton_Week.setText("단백질");
                        dateArr = getThisWeekDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesConsumedOnDate(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalProteinConsumedOnDate(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.fat_menu:
                        ingredientCheckButton_Week.setText("지방");
                        dateArr = getThisWeekDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesConsumedOnDate(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalFatConsumedOnDate(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.saccharides_menu:
                        ingredientCheckButton_Week.setText("당류");
                        dateArr = getThisWeekDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesConsumedOnDate(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalSugarsConsumedOnDate(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.sodium_menu:
                        ingredientCheckButton_Week.setText("나트륨");
                        dateArr = getThisWeekDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesConsumedOnDate(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalSodiumConsumedOnDate(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.cholesterol_menu:
                        ingredientCheckButton_Week.setText("콜레스테롤");
                        dateArr = getThisWeekDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesConsumedOnDate(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalCholesterolConsumedOnDate(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.transfat_menu:
                        ingredientCheckButton_Week.setText("트랜스지방");
                        dateArr = getThisWeekDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesConsumedOnDate(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalTransFatConsumedOnDate(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.fattyacid_menu:
                        ingredientCheckButton_Week.setText("포화지방산");
                        dateArr = getThisWeekDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesConsumedOnDate(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalSaturatedFatConsumedOnDate(dateArr[i]);
                        }
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

    private String[] getThisWeekDateArray() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        String[] dateArr = new String[7];

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // 현재 주의 월요일로 이동
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek + Calendar.MONDAY);

        for (int i = 0; i < 7; i++) {
            dateArr[i] = sdf.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dateArr;
    }


}