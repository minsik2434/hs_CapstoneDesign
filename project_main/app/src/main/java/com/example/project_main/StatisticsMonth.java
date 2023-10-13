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

public class StatisticsMonth extends Fragment {

    private Button ingredientCheckButton_Month;
    private MyDatabaseHelper dbHelper;

    private String[] dateArr;
    private int[] data;
    private int[] contentsData;

    BarChartView barChartView;
    LineChartView lineChartView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_month, container, false);

        ingredientCheckButton_Month = view.findViewById(R.id.ingredientCheckButton_Month);

        dbHelper = new MyDatabaseHelper(requireContext());

        barChartView = view.findViewById(R.id.barChartView);
        lineChartView = view.findViewById(R.id.lineChartView);

        dateArr = getThisMonthDateArray();

        int[] data = new int[7];

        for(int i=0;i<7;i++){
            data[i] = dbHelper.getTotalCaloriesForMonth(dateArr[i]);
        }

        int[] contentsData = new int[7];
        for(int i=0;i<7;i++){
            contentsData[i] = dbHelper.getTotalCarbohydratesForMonth(dateArr[i]);
        }



        barChartView.setData(data, dateArr);
        lineChartView.setData(contentsData);

        ingredientCheckButton_Month.setOnClickListener(new View.OnClickListener() {
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
                        ingredientCheckButton_Month.setText("탄수화물");
                        dateArr = getThisMonthDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForMonth(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalCarbohydratesForMonth(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.protein_menu:
                        ingredientCheckButton_Month.setText("단백질");
                        dateArr = getThisMonthDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForMonth(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalProteinsForMonth(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.fat_menu:
                        ingredientCheckButton_Month.setText("지방");
                        dateArr = getThisMonthDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForMonth(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalFatForMonth(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.saccharides_menu:
                        ingredientCheckButton_Month.setText("당류");
                        dateArr = getThisMonthDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForMonth(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalSugarsForMonth(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.sodium_menu:
                        ingredientCheckButton_Month.setText("나트륨");
                        dateArr = getThisMonthDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForMonth(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalSaltForMonth(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.cholesterol_menu:
                        ingredientCheckButton_Month.setText("콜레스테롤");
                        dateArr = getThisMonthDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForMonth(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalCholesterolForMonth(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.transfat_menu:
                        ingredientCheckButton_Month.setText("트랜스지방");
                        dateArr = getThisMonthDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForMonth(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalTransFatForMonth(dateArr[i]);
                        }
                        barChartView.setData(data, dateArr);
                        lineChartView.setData(contentsData);
                        return true;
                    case R.id.fattyacid_menu:
                        ingredientCheckButton_Month.setText("포화지방산");
                        dateArr = getThisMonthDateArray();
                        data = new int[7];
                        for (int i = 0; i < 7; i++) {
                            data[i] = dbHelper.getTotalCaloriesForMonth(dateArr[i]);
                        }
                        contentsData = new int[7];
                        for (int i = 0; i < 7; i++) {
                            contentsData[i] = dbHelper.getTotalSaturatedFatForMonth(dateArr[i]);
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


    private String[] getThisMonthDateArray() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());

        String[] dateArr = new String[7];

        for (int i = 6; i >= 0; i--) {
            dateArr[i] = sdf.format(calendar.getTime());
            calendar.add(Calendar.MONTH, -1); // 이전 월로 이동
        }

        return dateArr;
    }

}