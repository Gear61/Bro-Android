package com.randomappsinc.bro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.randomappsinc.bro.Adapters.HomepageTabsAdapter;
import com.randomappsinc.bro.Layouts.SlidingTabLayout;
import com.randomappsinc.bro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    @Bind(R.id.viewpager) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        HomepageTabsAdapter profileTabsAdapter = new HomepageTabsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(profileTabsAdapter);
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent loadSettingsPage = new Intent(this, SettingsActivity.class);
            startActivity(loadSettingsPage);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
