package com.randomappsinc.bro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.randomappsinc.bro.Adapters.HomepageTabsAdapter;
import com.randomappsinc.bro.Layouts.SlidingTabLayout;
import com.randomappsinc.bro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends StandardActivity {
    @Bind(R.id.viewpager) ViewPager mViewPager;
    @Bind(R.id.sliding_tabs) SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        HomepageTabsAdapter profileTabsAdapter = new HomepageTabsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(profileTabsAdapter);
        slidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.choose_message).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_wechat)
                        .colorRes(R.color.white)
                        .actionBarSize());
        menu.findItem(R.id.confirm_messages).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_hand_stop_o)
                        .colorRes(R.color.white)
                        .actionBarSize());
        menu.findItem(R.id.settings).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_gear)
                        .colorRes(R.color.white)
                        .actionBarSize());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.choose_message:
                return true;
            case R.id.confirm_messages:
                return true;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
