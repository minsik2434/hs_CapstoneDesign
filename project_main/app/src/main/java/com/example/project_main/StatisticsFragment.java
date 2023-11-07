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

        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        calendar.add(Calendar.DAY_OF_WEEK, -dayOfWeek + 2);
        Date thisWeekStart = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date thisWeekEnd = calendar.getTime();

        String thisWeekStartString = dateFormat.format(thisWeekStart);
        String thisWeekEndString = dateFormat.format(thisWeekEnd);

        String thisWeekStr = thisWeekStartString+"~"+thisWeekEndString;

        calendar.setTime(thisWeekStart);
        calendar.add(Calendar.DAY_OF_WEEK, -7);
        Date lastWeekStart = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date lastWeekEnd = calendar.getTime();

        String lastWeekStartString = dateFormat.format(lastWeekStart);
        String lastWeekEndString = dateFormat.format(lastWeekEnd);

        String lastWeekStr = lastWeekStartString+"~"+lastWeekEndString;

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