package com.example.project_main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BannerAdapter extends FragmentStateAdapter {
    public int mCount;

    public BannerAdapter(StatisticsFragment fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new Fragment_1();
        else if(index==1) return new Fragment_2();
        else return new Fragment_3();
    }

    @Override
    public int getItemCount() {
        return 1080;
    }

    public int getRealPosition(int position) { return position % mCount; }

}