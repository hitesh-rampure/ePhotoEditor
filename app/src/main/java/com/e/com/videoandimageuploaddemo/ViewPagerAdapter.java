package com.e.com.videoandimageuploaddemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.e.com.videoandimageuploaddemo.TabFragment.updateViewPager;

public class ViewPagerAdapter extends FragmentPagerAdapter implements updateViewPager
    {

        private String title[] = {"Pictures", "Videos", "Documents"};
        private updateViewPager updateViewPager;

        public ViewPagerAdapter(FragmentManager fm, updateViewPager updatePager)
            {
                super(fm);
                updateViewPager = updatePager;
            }

        @Override
        public Fragment getItem(int position)
            {
                TabFragment tabFragment = TabFragment.getInstance(position);
                tabFragment.updateViewPager(this);

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

        @Override
        public void updateViewPager(int pageNumber)
            {
                updateViewPager.updateViewPager(pageNumber);
            }
    }
