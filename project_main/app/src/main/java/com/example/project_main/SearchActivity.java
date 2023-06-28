package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchActivity extends Activity {

    ListView listView;
    ListViewAdapter adapter;
    ImageButton xbtn;
    EditText searchEdit;

    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        xbtn = findViewById(R.id.xbtn);
        listView = findViewById(R.id.foodlist);
        searchEdit = findViewById(R.id.searchEdit);

        dbHelper = new MyDatabaseHelper(this);
        
        adapter = new ListViewAdapter();
        adapter.addItem(R.mipmap.ic_launcher, "음식이름", "칼로리" + " Kcal", "탄100g단100g지100g");

        listView.setAdapter(adapter);



        //리스트뷰 클릭 이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //finish();
            }
        });

        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}