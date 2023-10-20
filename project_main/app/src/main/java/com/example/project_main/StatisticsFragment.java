package com.example.project_main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.compose.ui.platform.ComposeView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator3;


public class StatisticsFragment extends Fragment {

    private MyDatabaseHelper dbHelper;
    private BottomNavigationView statisticsBottomNavigationView;
    private FragmentManager fragmentManager;

    private static final int NUM_TOP_FOODS = 5;

    TextView compareText;

    Integer[] foodTopId = {
            R.id.foodTop1, R.id.foodTop2, R.id.foodTop3, R.id.foodTop4, R.id.foodTop5
    };
    TextView[] foodTopTextView = new TextView[foodTopId.length];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());

        for (int i = 0; i < foodTopTextView.length; i++) {
            foodTopTextView[i] = view.findViewById(foodTopId[i]);
        }

        String[] foodname = new String[NUM_TOP_FOODS];

        for (int i = 1; i <= NUM_TOP_FOODS; i++) {
            foodname[i - 1] = dbHelper.getNthMostEatenFoodForWeek(i);
        }

        for (int i = 0; i < foodname.length; i++) {
            foodTopTextView[i].setText(foodname[i]);
        }

        fragmentManager = getActivity().getSupportFragmentManager();

        statisticsBottomNavigationView = view.findViewById(R.id.statisticsBottomNavigationView);
        // 초기 프래그먼트를 홈으로 설정
        loadFragment(new StatisticsDay()); // 초기 프래그먼트를 StatisticsWeek로 설정

        statisticsBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.day:
                        fragment = new StatisticsDay();
                        break;
                    case R.id.month:
                        fragment = new StatisticsMonth();
                        break;
                    case R.id.week:
                        fragment = new StatisticsWeek();
                        break;
                    default:
                        fragment = new StatisticsWeek();
                        break;
                }
                return loadFragment(fragment);
            }
        });

        compareText = view.findViewById(R.id.compareText);

        // Calculate the date range for the current week (Monday to Sunday)
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        // Set the calendar to the current date
        calendar.setTime(new Date());

        // Find the current day of the week
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Calculate the difference between the current day and Monday
        int daysUntilMonday = (Calendar.MONDAY - dayOfWeek + 7) % 7;

        // Calculate the difference between the current day and Sunday
        int daysUntilSunday = (Calendar.SUNDAY - dayOfWeek + 7) % 7;

        // Calculate the dates for the current week
        calendar.add(Calendar.DAY_OF_YEAR, daysUntilMonday);
        Date startDateThisWeek = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, daysUntilSunday - daysUntilMonday);
        Date endDateThisWeek = calendar.getTime();

        // Calculate the dates for the previous week
        calendar.setTime(startDateThisWeek); // Reset the calendar to the start of the current week
        calendar.add(Calendar.DAY_OF_YEAR, -7); // Move the calendar 7 days back
        Date startDateLastWeek = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 6); // Move the calendar 6 days forward to get the end of the previous week
        Date endDateLastWeek = calendar.getTime();

        // Format the dates as "MM/DD" strings
        String startDateThisWeekStr = dateFormat.format(startDateThisWeek);
        String endDateThisWeekStr = dateFormat.format(endDateThisWeek);
        String startDateLastWeekStr = dateFormat.format(startDateLastWeek);
        String endDateLastWeekStr = dateFormat.format(endDateLastWeek);

        String thisWeekStr = startDateThisWeekStr+"~"+endDateThisWeekStr;
        String lastWeekStr = startDateLastWeekStr+"~"+endDateLastWeekStr;

        int thisWeekInt = dbHelper.getTotalCaloriesForDateRange(thisWeekStr);
        int lastWeekInt = dbHelper.getTotalCaloriesForDateRange(lastWeekStr);

        if(thisWeekInt>lastWeekInt){
            String text = "저번 주보다 "+(thisWeekInt-lastWeekInt)+"kcal 더 먹었습니다";
            compareText.setText(text);
        }
        else if(lastWeekInt>thisWeekInt){
            String text = "저번 주보다 "+(lastWeekInt-thisWeekInt)+"kcal 덜 먹었습니다.";
            compareText.setText(text);
        }

//        // Set the text in compareText
//        String text = "이번 주: " + thisWeekInt + "\n"
//                + "저번 주: " + lastWeekInt;
//        compareText.setText(text);

        return view;

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.statistics_fragment_container, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}