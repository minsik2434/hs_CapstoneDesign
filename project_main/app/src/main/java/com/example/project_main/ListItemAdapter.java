package com.example.project_main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {
    ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
    Context context;
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ListViewItem listViewItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom,parent,false);
        }

        ImageView imgView = convertView.findViewById(R.id.foodImage);
        TextView nameText = convertView.findViewById(R.id.foodName);
        TextView kcalText = convertView.findViewById(R.id.foodKcal);
        TextView infoText = convertView.findViewById(R.id.foodInfo);

        imgView.setImageResource(listViewItem.getFoodImage());
        nameText.setText(listViewItem.getFoodName());
        kcalText.setText(listViewItem.getFoodKcal());
        infoText.setText(listViewItem.getFoodInfo());


        return convertView;
    }
    public void addItem(ListViewItem item){
        items.add(item);
    }
}
