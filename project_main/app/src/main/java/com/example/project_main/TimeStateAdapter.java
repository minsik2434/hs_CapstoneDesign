package com.example.project_main;

import android.content.Context;
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

public class TimeStateAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener{

    private Context context;
    private List list;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();
    }

    class ViewHolder{
        public ImageView food_img;
        public TextView food_name;
        public TextView food_kcal;
        public TextView food_info;
    }

    public TimeStateAdapter(Context context, ArrayList list){
        super(context,0,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final TimeStateAdapter.ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_custom,parent,false);
        }

        viewHolder = new ViewHolder();
        viewHolder.food_img = convertView.findViewById(R.id.foodImage);
        viewHolder.food_name = convertView.findViewById(R.id.foodName);
        viewHolder.food_kcal = convertView.findViewById(R.id.foodKcal);
        viewHolder.food_info = convertView.findViewById(R.id.foodInfo);

        final MainFoodArray breakfastArray = (MainFoodArray) list.get(position);
        viewHolder.food_img.setImageResource(breakfastArray.getImg());
        viewHolder.food_name.setText(breakfastArray.getName());
        viewHolder.food_kcal.setText(breakfastArray.getKcal());
        viewHolder.food_info.setText(breakfastArray.getInfo());
        Glide
                .with(context)
                .load(breakfastArray.getImg())
                .centerCrop()
                .apply(new RequestOptions().override(250, 350))
                .into(viewHolder.food_img);
        viewHolder.food_name.setTag(breakfastArray.getName());

        return convertView;
    }

}
