package com.example.project_main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
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

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {

    EditText searchBar;
    ListView listView;
    ListViewAdapter adapter;
    ImageButton xbtn;
    MyDatabaseHelper dbHelper;
    private ArrayList<RecodeSelectDto> search_foodList = new ArrayList<RecodeSelectDto>();
    String foodname;
    int foodKcal;
    float foodCarbohydrate;
    float foodProtein;
    float foodProvince;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        searchBar = findViewById(R.id.searchBar);
        xbtn = findViewById(R.id.xbtn);
        listView = findViewById(R.id.foodlist);
        adapter = new ListViewAdapter();
        dbHelper = new MyDatabaseHelper(this);
<<<<<<< HEAD

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // 텍스트 내용을 가져온다.
                adapter.clearItem();
                String searchKeyword = textView.getText().toString();
                String sql_sentence = "select foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat from food_table where foodname like '%"+searchKeyword+"%' ORDER BY (CASE WHEN manufacturer = '전국(대표)' THEN 1 ELSE 2 end)";
                search_foodList = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);

                for(int j=0; j < search_foodList.toArray().length; j++)
                {
                    foodname = search_foodList.get(j).getFoodName();
                    foodKcal = (int) search_foodList.get(j).getKcal();
                    foodCarbohydrate = search_foodList.get(j).getCarbohydrate();
                    foodProtein = search_foodList.get(j).getProtein();
                    foodProvince = search_foodList.get(j).getProvince();

                    adapter.addItem(foodname,  foodKcal+"Kcal", "탄수화물 " + foodCarbohydrate + "g" + " 단백질 " + foodProtein + "g" + " 지방 " + foodProvince + "g");
                }

                listView.setAdapter(adapter);
                return false;
            }
        });
=======

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // 텍스트 내용을 가져온다.
                adapter.clearItem();
                String searchKeyword = textView.getText().toString();
                String sql_sentence = "select foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat from food_table where foodname like '%"+searchKeyword+"%' ORDER BY (CASE WHEN manufacturer = '전국(대표)' THEN 1 ELSE 2 end)";
                search_foodList = dbHelper.executeQuerySearchIntakeFoodToday(sql_sentence);

                for(int j=0; j < search_foodList.toArray().length; j++)
                {
                    foodname = search_foodList.get(j).getFoodName();
                    foodKcal = (int) search_foodList.get(j).getKcal();
                    foodCarbohydrate = search_foodList.get(j).getCarbohydrate();
                    foodProtein = search_foodList.get(j).getProtein();
                    foodProvince = search_foodList.get(j).getProvince();

                    adapter.addItem(foodname,  "Kcal " + foodKcal, "탄수화물 " + foodCarbohydrate + "g, 단백질 " + foodProtein + "g, 지방 " + foodProvince + "g");
                }

                listView.setAdapter(adapter);
                return false;
            }
        });

>>>>>>> 0b1921a7a148ac754b1b3e25c24bbc6feb55b6ef

        //리스트뷰 클릭 이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
<<<<<<< HEAD

                ListViewItem vo = (ListViewItem) listView.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("foodname", vo.getFoodName());
                intent.putExtra("kcal", vo.getFoodKcal());
                intent.putExtra("foodinfo", vo.getFoodInfo());

                setResult(Activity.RESULT_OK, intent);
=======
                ListViewItem listViewItem = (ListViewItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),RecordFragment.class);
                intent.putExtra("fname", listViewItem.getFoodName());
                intent.putExtra("kcal",listViewItem.getFoodKcal());
                intent.putExtra("foodinfo",listViewItem.getFoodInfo());
                setResult(RESULT_OK,intent);
>>>>>>> 0b1921a7a148ac754b1b3e25c24bbc6feb55b6ef
                finish();
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