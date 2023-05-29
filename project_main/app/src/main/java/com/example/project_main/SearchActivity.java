package com.example.project_main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;

public class SearchActivity extends Activity {
    ListView listView;
    ListItemAdapter adapter;
    ImageButton xbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        xbtn = findViewById(R.id.xbtn);
        listView = findViewById(R.id.foodlist);
        adapter = new ListItemAdapter();

        adapter.addItem(new ListViewItem(R.mipmap.ic_launcher,"사과","12kcal","당1g 염분1g"));
        adapter.addItem(new ListViewItem(R.mipmap.ic_launcher,"사과","12kcal","당1g 염분1g"));
        adapter.addItem(new ListViewItem(R.mipmap.ic_launcher,"김치","12kcal","당1g 염분1g"));
        adapter.addItem(new ListViewItem(R.mipmap.ic_launcher,"부대찌개","12kcal","당1g 염분1g"));
        listView.setAdapter(adapter);

        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
