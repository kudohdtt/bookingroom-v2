package com.learnadroid.myfirstapp.home;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @Override
    public int getCount() {
        return numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }
}
