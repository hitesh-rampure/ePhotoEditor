package com.e.com.videoandimageuploaddemo;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
    {
        private TabLayout tabLayout;
        private ViewPager viewPager;
        private LinkedList<SelectedData> selectedDataLinkedList;


        @Override

        protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                viewPager = (ViewPager) findViewById(R.id.viewpager);
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(4);

                tabLayout = (TabLayout) findViewById(R.id.tabs);
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
    }
