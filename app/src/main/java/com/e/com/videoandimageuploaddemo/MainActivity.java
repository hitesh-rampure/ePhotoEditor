package com.e.com.videoandimageuploaddemo;

import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.e.com.videoandimageuploaddemo.TabFragment.updateViewPager;

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


        @Override
        protected void onDestroy()
            {
                super.onDestroy();

                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();

                Log.e("OnDestroy", "Service On Destroy");
                Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
                sendBroadcast(broadcastIntent);

                //stoptimertask();


            }
    }
