package com.example.project_main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    public void addItem(ListViewItem item) {
        listViewItemList.add(item);
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_custom, parent, false);

        ImageView image = view.findViewById(R.id.image);
        TextView foodName = view.findViewById(R.id.foodName);
        TextView kcal = view.findViewById(R.id.kcal);

        // ListView의 Item을 구성하는 뷰 세팅
        ListViewItem item = listViewItemList.get(position);
        image.setImageResource(item.getImage());		// item 객체 내용을 가져와 세팅
        foodName.setText(item.getFoodName());		// 해당위치 +1 설정, 배열순으로 0부터 시작
        kcal.setText(item.getKcal());					// item 객체 내용을 가져와 세팅

        // 설정한 view를 반환해줘야 함
        return view;
    }
}
