package com.example.project_main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MypageFragment extends Fragment {

    MyDatabaseHelper dbHelper;
    ArrayList<UserDto> userInfo = new ArrayList<>();

    private RecyclerCustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        TextView userProfileName = view.findViewById(R.id.userProfileName);
        TextView userProfileAgeSex = view.findViewById(R.id.userProfileAgeSex);
        TextView userProfileHeight = view.findViewById(R.id.userProfileHeight);
        TextView userProfileWeight = view.findViewById(R.id.userProfileWeight);
        TextView userProfileActivity = view.findViewById(R.id.userProfileActivity);

        ImageButton imgBtn = view.findViewById(R.id.mypageSettingsBtn);

        //리사이클러뷰
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerCustomAdapter();
        recyclerView.setAdapter(adapter);

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this.getContext());
        String sql_sentence = "SELECT * from user_timeline;";
        ArrayList<TimelineSelectDto> timelineSelectDto = new ArrayList<TimelineSelectDto>();
        timelineSelectDto = dbHelper.executeQuerySearchAlarmToday(sql_sentence);

        System.out.println("timelineSelectDto.size() :" + timelineSelectDto.size());
        for (int i = 0; i < timelineSelectDto.size(); i++) {
            Alert_Data data = new Alert_Data();
            String alarmTime = timelineSelectDto.get(i).getDate();
            data.setTimeline_text(alarmTime.substring(5, 16));
            data.setTimeline_resId(getIcon(timelineSelectDto.get(i).getAlarm_ing()));
            data.setTimeline_cardview(timelineSelectDto.get(i).getContext());
            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }


        // adapter의 값 갱신
        adapter.notifyDataSetChanged();

        //DB Connect
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        userInfo = dbHelper.ExecuteQueryGetUserInfo();

        if (userInfo.size() != 0) {
            userProfileName.setText(userInfo.get(0).getNickname());
            userProfileAgeSex.setText(userInfo.get(0).getAge() + "세 (" + userInfo.get(0).getSex() + ")");
            userProfileHeight.setText(userInfo.get(0).getHeight() + "");
            userProfileWeight.setText(userInfo.get(0).getWeight() + "");
            userProfileActivity.setText(userInfo.get(0).getActivity());
        }

        //설정
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), UserInfoReset.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private Bitmap getIcon(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

}