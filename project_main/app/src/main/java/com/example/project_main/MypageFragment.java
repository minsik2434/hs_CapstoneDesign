package com.example.project_main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MypageFragment extends Fragment {

    MyDatabaseHelper dbHelper;
    ArrayList<UserDto> userInfo = new ArrayList<>();

    ImageButton mypageSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        TextView userProfileNameAgeSex = view.findViewById(R.id.userProfileNameAgeSex);
        TextView userProfileHeight = view.findViewById(R.id.userProfileHeight);
        TextView userProfileWeight = view.findViewById(R.id.userProfileWeight);
        TextView userProfileActivity = view.findViewById(R.id.userProfileActivity);

        //DB Connect
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        userInfo = dbHelper.ExecuteQueryGetUserInfo();

        userProfileNameAgeSex.setText(userInfo.get(0).getNickname()+" / "+userInfo.get(0).getAge()+"세("+userInfo.get(0).getSex()+")");
        userProfileHeight.setText(userInfo.get(0).getHeight()+"");
        userProfileWeight.setText(userInfo.get(0).getWeight()+"");
        userProfileActivity.setText(userInfo.get(0).getActivity());

        //설정 버튼(버그 있음)
//        mypageSetting = view.findViewById(R.id.settingsBtn);
//        mypageSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),init_setup1.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }

}