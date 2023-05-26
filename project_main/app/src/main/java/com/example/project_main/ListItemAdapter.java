package com.example.project_main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {
    ArrayList<ListItem> items = new ArrayList<ListItem>();
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
        ListItem listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item,parent,false);
        }

        TextView nameText = convertView.findViewById(R.id.name);
        TextView kcalText = convertView.findViewById(R.id.kcal);
        TextView infoText = convertView.findViewById(R.id.info);

        nameText.setText(listItem.getName());
        kcalText.setText(listItem.getKcal());
        infoText.setText(listItem.getInfo());

        return convertView;
    }
    public void addItem(ListItem item){
        items.add(item);
    }
}
