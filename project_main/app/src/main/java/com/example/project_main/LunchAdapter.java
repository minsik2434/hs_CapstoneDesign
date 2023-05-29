package com.example.project_main;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class LunchAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List list;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();
    }

    class ViewHolder{
        public ImageView lunch_img;
        public TextView lunch_name;
        public TextView lunch_kcal;
        public TextView lunch_info;
    }

    public LunchAdapter(Context context, ArrayList list){
        super(context,0,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_custom,parent,false); //listview_item 임시 가져옴(커스텀 리스트뷰)
        }

        viewHolder = new ViewHolder();
        viewHolder.lunch_img = convertView.findViewById(R.id.foodImage);
        viewHolder.lunch_name = convertView.findViewById(R.id.foodName);
        viewHolder.lunch_kcal = convertView.findViewById(R.id.foodKcal);
        viewHolder.lunch_info = convertView.findViewById(R.id.foodInfo);

        final LunchArray lunchArray = (LunchArray) list.get(position);
        viewHolder.lunch_img.setImageResource(lunchArray.getImg());
        viewHolder.lunch_name.setText(lunchArray.getName());
        viewHolder.lunch_kcal.setText(lunchArray.getKcal());
        viewHolder.lunch_info.setText(lunchArray.getInfo());
        Glide
                .with(context)
                .load(lunchArray.getImg())
                .centerCrop()
                .apply(new RequestOptions().override(250, 350))
                .into(viewHolder.lunch_img);
        viewHolder.lunch_name.setTag(lunchArray.getName());


        return convertView;
    }
}
