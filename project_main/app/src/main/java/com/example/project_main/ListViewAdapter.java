package com.example.project_main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

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

        final int pos = position;
        final Context context = parent.getContext();
        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);
        }

        //화면에 보여질 데이터 참조
        TextView foodName = convertView.findViewById(R.id.foodName);
        TextView foodkcal = convertView.findViewById(R.id.foodKcal);
        TextView foodInfo = convertView.findViewById(R.id.foodInfo);

        //화면에 보여질 데이터 설정
        foodName.setText(listViewItem.getFoodName());
        foodkcal.setText(listViewItem.getFoodKcal());
        foodInfo.setText(listViewItem.getFoodInfo());


        // 설정한 view를 반환해줘야 함
        return convertView;
    }

    public void addItem(String name, String kcal, String info) {
        ListViewItem item = new ListViewItem(name,kcal,info);
        listViewItemList.add(item);
    }

    public void clearItem(){
        listViewItemList.clear();
    }



}
