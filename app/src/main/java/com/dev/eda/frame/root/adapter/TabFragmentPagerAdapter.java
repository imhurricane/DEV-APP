package com.dev.eda.frame.root.adapter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dev.eda.frame.blog.fragment.BlogFragment;
import com.dev.eda.frame.centre.fragment.CentreFragment;
import com.dev.eda.frame.home.fragment.HomeFragment;
import com.dev.eda.frame.mine.fragment.MineFragment;

public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {


    private String[] mData;

    public TabFragmentPagerAdapter(@NonNull FragmentManager fm, String[] data) {
        super(fm);
        mData = data;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return CentreFragment.getInstance();
            case 2:
                return BlogFragment.getInstance();
            case 3:
                return MineFragment.getInstance();
            default:
                return HomeFragment.getInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mData[position];
    }

    @Override
    public int getCount() {
        return mData.length;
    }



}
