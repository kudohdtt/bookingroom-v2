package com.learnadroid.myfirstapp.home;


import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs = 3;
    ImageView searchIcon;
    ImageView orderIcon;
    ImageView profileIcon;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    private void SetactiveIcon(int pos) {

    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SearchHotel();
            case 1:
                return new SearchHotel();
            case 2:
                return new Profile();

            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
