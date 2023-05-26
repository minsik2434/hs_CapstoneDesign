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

public class BreakfastAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List list;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();
    }

    class ViewHolder{
        public ImageView breakfast_img;
        public TextView breakfast_name;
        public TextView breakfast_kcal;
        public TextView breakfast_info;
    }

    public BreakfastAdapter(Context context, ArrayList list){
        super(context,0,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_item,parent,false); //listview_item 임시 가져옴(커스텀 리스트뷰)
        }

        viewHolder = new ViewHolder();
        viewHolder.breakfast_img = convertView.findViewById(R.id.img);
        viewHolder.breakfast_name = convertView.findViewById(R.id.name);
        viewHolder.breakfast_kcal = convertView.findViewById(R.id.kcal);
        viewHolder.breakfast_info = convertView.findViewById(R.id.info);

        final BreakfastArray breakfastArray = (BreakfastArray) list.get(position);
        viewHolder.breakfast_img.setImageResource(breakfastArray.getImg());
        viewHolder.breakfast_name.setText(breakfastArray.getName());
        viewHolder.breakfast_kcal.setText(breakfastArray.getKcal());
        viewHolder.breakfast_info.setText(breakfastArray.getInfo());
        Glide
                .with(context)
                .load(breakfastArray.getImg())
                .centerCrop()
                .apply(new RequestOptions().override(250, 350))
                .into(viewHolder.breakfast_img);
        viewHolder.breakfast_name.setTag(breakfastArray.getName());


        return convertView;
    }
}
