package com.e.com.videoandimageuploaddemo;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnAdapterChangeListener;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.e.com.videoandimageuploaddemo.TabFragment.updateViewPager;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements updateViewPager, OnPageChangeListener
    {
        private ViewPager viewPager;

        @Override
        protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                viewPager = findViewById(R.id.viewpager);
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(4);

                viewPager.addOnPageChangeListener(this);

                TabLayout tabLayout = findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);
            }

        @Override
        public boolean onCreateOptionsMenu(Menu menu)
            {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu, menu);
                SearchManager searchManager =
                        (SearchManager) getSystemService(Context.SEARCH_SERVICE);

                MenuItem searchItem = menu.findItem(R.id.menu_search);

                android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();

                searchView.setSearchableInfo(
                        searchManager.getSearchableInfo(getComponentName()));

                return super.onCreateOptionsMenu(menu);
            }

        @Override
        public void updateViewPager(int pageNumbar)
            {
                viewPager.setCurrentItem(pageNumbar);
            }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

        @Override
        public void onPageSelected(int position)
            {


            }

        @Override
        public void onPageScrollStateChanged(int state)
            {

            }
    }
