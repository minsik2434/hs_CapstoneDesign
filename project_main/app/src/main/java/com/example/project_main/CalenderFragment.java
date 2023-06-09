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

    private ArrayList<Integer> foodImg = new ArrayList<>();
    private ArrayList<String> foodName = new ArrayList<>();
    private ArrayList<Integer> foodKcal = new ArrayList<>();
    private ArrayList<Float> foodCarbohydrate = new ArrayList<>();
    private ArrayList<Float> foodProtein = new ArrayList<>();
    private ArrayList<Float> foodProvince = new ArrayList<>();
    private ArrayList<String> foodNutriInfo = new ArrayList<>();

    //초과하여 먹은 날짜 종합
    private ArrayList<String> overeatDay = new ArrayList<>();

    MyDatabaseHelper dbHelper;
    SQLiteDatabase database;

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

        //권장 칼로리보다 적게 먹었다면 초록, 아니면 빨강
//        for(int i =0;i<date.length;i++)
//        {
//            if(todayKcal[i]<=suitableKcal[i])
//            {
//                materialCalendarView.addDecorators(new BGDecorator(date[i]));
//            }
//            if(todayKcal[i]>suitableKcal[i])
//            {
//                materialCalendarView.addDecorators(new BGDecorator2(date[i]));
//            }
//        }

        //DB Connect
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        database = dbHelper.getWritableDatabase();
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
                getIntakeExecuteQuery(dbDate);

                //음식 탄단지 정보 저장
                for (int i = 0; i < foodName.size(); i++) {
                    foodNutriInfo.add("탄수화물 " + foodCarbohydrate.get(i) + "g" + " 단백질 " + foodProtein.get(i) + "g" + " 지방 " + foodProvince.get(i) + "g");
                }
                //어뎁터 초기화
                listViewAdapter.clearItem();
                //어뎁터에 아이템 추가, 첫 번째 인자에 foodImg.get(i) 넣어야 함. DB에 데이터 없어서 작성하지 않음
                for (int i = 0; i < foodName.size(); i++) {
                    listViewAdapter.addItem(R.mipmap.ic_launcher, foodName.get(i), foodKcal.get(i) + " Kcal", foodNutriInfo.get(i));
                }
                //리스트뷰에 어뎁터 set
                schedule_list.setAdapter(listViewAdapter);
                //ArrayList 초기화
                    foodImg.clear();
                    foodName.clear();
                    foodKcal.clear();
                    foodCarbohydrate.clear();
                    foodProtein.clear();
                    foodProvince.clear();
                    foodNutriInfo.clear();

            }
        });

        //달력 꾸미기
        //권장 칼로리(500kcal) 이상 섭취한 날짜 불러오기
        getOvereatDayExecuteQuery(500);
        //날짜 색 빨강으로 변경
        for(int i =0;i<overeatDay.size();i++)
        {
                materialCalendarView.addDecorators(new BGDecorator2(overeatDay.get(i)));
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

    private void getIntakeExecuteQuery(String todayDate){
        Cursor cursor = database.rawQuery("select food_image, foodname, kcal, carbohydrate, protein, province\n" +
                "from food_table, nutrition, intake_table\n" +
                "where food_table.foodID = nutrition.foodID\n" +
                "and food_table.foodID = intake_table.foodID\n" +
                "and intakeID in (select intakeID from intake_table where substr(date,1,10) = "+"'"+todayDate+"');",null);
        int recordCount = cursor.getCount();
        for (int i = 0; i < recordCount; i++){
            cursor.moveToNext();
            foodImg.add(cursor.getInt(0));
            foodName.add(cursor.getString(1));
            foodKcal.add(cursor.getInt(2));
            foodCarbohydrate.add(cursor.getFloat(3));
            foodProtein.add(cursor.getFloat(4));
            foodProvince.add(cursor.getFloat(5));
        }
        cursor.close();
    }

    private void getOvereatDayExecuteQuery(int suitableKcal){
        Cursor cursor = database.rawQuery("select substr(date,1,10)\n" +
                "from intake_table, food_table, nutrition\n" +
                "where intake_table.foodID = food_table.foodID\n" +
                "and food_table.foodID = nutrition.foodID\n" +
                "group by substr(date,1,10)\n" +
                "having sum(kcal) > "+suitableKcal+";",null);
        int recordCount = cursor.getCount();
        for (int i = 0; i < recordCount; i++){
            cursor.moveToNext();
            overeatDay.add(cursor.getString(0));
        }
        cursor.close();
    }


}