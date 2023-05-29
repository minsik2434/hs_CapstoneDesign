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

public class DinnerAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener{

    private Context context;
    private List list;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();
    }

    class ViewHolder{
        public ImageView dinner_img;
        public TextView dinner_name;
        public TextView dinner_kcal;
        public TextView dinner_info;
    }

    public DinnerAdapter(Context context, ArrayList list){
        super(context,0,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final DinnerAdapter.ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_custom,parent,false); //listview_item 임시 가져옴(커스텀 리스트뷰)
        }

        viewHolder = new DinnerAdapter.ViewHolder();
        viewHolder.dinner_img = convertView.findViewById(R.id.foodImage);
        viewHolder.dinner_name = convertView.findViewById(R.id.foodName);
        viewHolder.dinner_kcal = convertView.findViewById(R.id.foodKcal);
        viewHolder.dinner_info = convertView.findViewById(R.id.foodInfo);

        final DinnerArray dinnerArray = (DinnerArray) list.get(position);
        viewHolder.dinner_img.setImageResource(dinnerArray.getImg());
        viewHolder.dinner_name.setText(dinnerArray.getName());
        viewHolder.dinner_kcal.setText(dinnerArray.getKcal());
        viewHolder.dinner_info.setText(dinnerArray.getInfo());
        Glide
                .with(context)
                .load(dinnerArray.getImg())
                .centerCrop()
                .apply(new RequestOptions().override(250, 350))
                .into(viewHolder.dinner_img);
        viewHolder.dinner_name.setTag(dinnerArray.getName());


        return convertView;
    }

}
