package com.example.project_main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SecondBannerAdapter extends FragmentStateAdapter {
    public int mCount;

    public SecondBannerAdapter(@NonNull FragmentActivity fragmentActivity, int count) {
        super(fragmentActivity);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

//        if (index == 0) return new SecondFragment_1();
//        else if (index == 1) return new SecondFragment_2();
//        else if (index == 2) return new SecondFragment_3();
//        else return new SecondFragment_4();

        if (index == 0) return new SecondFragment_4();
        else if (index == 1) return new SecondFragment_3();
        else if (index == 2) return new SecondFragment_2();
        else return new SecondFragment_1();
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public int getRealPosition(int position) {
        return position % mCount;
    }
}