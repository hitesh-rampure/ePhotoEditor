package com.e.com.videoandimageuploaddemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

public class ViewPagerAdapter extends FragmentPagerAdapter
    {

        private String title[] = {"Pictures", "Videos", "Documents"};

        public ViewPagerAdapter(FragmentManager fm)
            {
                super(fm);
            }

        @Override
        public Fragment getItem(int position)
            {
                TabFragment tabFragment = TabFragment.getInstance(position);
                return tabFragment;
            }

        @Override
        public int getCount()
            {
                return title.length;
            }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
            {
                return title[position];
            }
    }
