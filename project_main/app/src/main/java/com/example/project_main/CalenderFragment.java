package com.example.project_main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalenderFragment extends Fragment {

    MaterialCalendarView materialCalendarView;
    TextView choice_date;
    private ListViewAdapter listViewAdapter;
    private ListView schedule_list;
    ScrollView scrollview_calender;

    String[] date;
    int[] todayKcal;
    int[] suitableKcal;
    long now = System.currentTimeMillis();
    Date currentDate;
    SimpleDateFormat format;

    private ArrayList<String> foodNutriInfo = new ArrayList<>();

    //초과하여 먹은 날짜 종합
    private ArrayList<String> overeatDay = new ArrayList<>();

    MyDatabaseHelper dbHelper;
    private ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();
    private ArrayList<CalendarDto> overeat = new ArrayList<CalendarDto>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        //달력 표시용 날짜, 오늘섭취칼로리, 권장섭취칼로리
//        date = new String[]{"2023-02-01", "2023-02-02", "2023-02-03"};
//        todayKcal = new int[]{1000,1500,2000};
//        suitableKcal = new int[]{1500,1600,1800};

        materialCalendarView = view.findViewById(R.id.calendarView);
        choice_date = view.findViewById(R.id.choice_date);
        //리스트뷰, 리스트뷰 어뎁터 초기화
        listViewAdapter = new ListViewAdapter();
        schedule_list = (ListView) view.findViewById(R.id.schedule_list);

        scrollview_calender = (ScrollView) view.findViewById(R.id.scrollview_calender);

        currentDate = new Date(now);
        format = new SimpleDateFormat("음력 MM월 dd일");
        choice_date.setText(format.format(currentDate));
        materialCalendarView.state().edit().setFirstDayOfWeek(Calendar.SUNDAY);
        materialCalendarView.state().edit().setMinimumDate(CalendarDay.from(2017, 0, 1));
        materialCalendarView.state().edit().setMaximumDate(CalendarDay.from(2030, 11, 31));
        materialCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS);

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new FutureDayDecorator()
        );

        //DB Date format
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

        //일 선택 시
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                SimpleDateFormat format = new SimpleDateFormat("음력 MM월 dd일");
                String CalDate = format.format(date.getDate());
                String dbDate = dbFormat.format(date.getDate());
                choice_date.setText(CalDate);

                //DB get data
                String sql_sentence = "select food_table.foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat, time\n" +
                        "from food_table, intake_table\n" +
                        "where food_table.foodname = intake_table.foodname\n" +
                        "and intakeID in (select intakeID from intake_table where substr(date,1,10) = '"+ dbDate +"');";

                dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
                intake_food = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);

                //음식 탄단지 정보 저장
                for (int i = 0; i < intake_food.size(); i++) {
                    foodNutriInfo.add("탄수화물 " + intake_food.get(i).getCarbohydrate() + "g" + " 단백질 " + intake_food.get(i).getProtein() + "g" + " 지방 " + intake_food.get(i).getProvince() + "g");
                }
                //어뎁터 초기화
                listViewAdapter.clearItem();
                //어뎁터에 아이템 추가
                for (int i = 0; i < intake_food.size(); i++) {
                    if (intake_food.get(i).getIntakeTime().equals("아침")) {
                        listViewAdapter.addItem(R.drawable.breakfast_icon, intake_food.get(i).getFoodName(), Math.round(intake_food.get(i).getKcal()) + " Kcal", foodNutriInfo.get(i));
                    }
                    else if (intake_food.get(i).getIntakeTime().equals("점심")){
                        listViewAdapter.addItem(R.drawable.lunch_icon, intake_food.get(i).getFoodName(), Math.round(intake_food.get(i).getKcal()) + " Kcal", foodNutriInfo.get(i));
                    }
                    else if (intake_food.get(i).getIntakeTime().equals("저녁")){
                        listViewAdapter.addItem(R.drawable.dinner_icon,intake_food.get(i).getFoodName(), Math.round(intake_food.get(i).getKcal()) + " Kcal", foodNutriInfo.get(i));
                    }
                    else;

                }
                //리스트뷰에 어뎁터 set
                schedule_list.setAdapter(listViewAdapter);
                //ArrayList 초기화
                foodNutriInfo.clear();


            }
        });

        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        overeat = dbHelper.ExecuteQueryGetOvereatDay(dbHelper.getRecommendedKcal());

        //날짜 색 빨강으로 변경
        for(int i =0;i<overeat.size();i++)
        {
                materialCalendarView.addDecorators(new BGDecorator2(overeat.get(i).getOvereatDate()));
        }

        schedule_list.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollview_calender.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}