package com.example.project_main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;

public class MypageFragment extends Fragment {

    MyDatabaseHelper dbHelper;
    ArrayList<UserDto> userInfo = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        TextView userProfileName = view.findViewById(R.id.userProfileName);
        TextView userProfileAgeSex = view.findViewById(R.id.userProfileAgeSex);
        TextView userProfileHeight = view.findViewById(R.id.userProfileHeight);
        TextView userProfileWeight = view.findViewById(R.id.userProfileWeight);
        TextView userProfileActivity = view.findViewById(R.id.userProfileActivity);

        ImageButton imgBtn = view.findViewById(R.id.mypageSettingsBtn);

        //DB Connect
        dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        userInfo = dbHelper.ExecuteQueryGetUserInfo();

        if(userInfo.size()!= 0) {
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
}