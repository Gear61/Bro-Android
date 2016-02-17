package com.randomappsinc.bro.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.randomappsinc.bro.Fragments.FriendsFragment;
import com.randomappsinc.bro.Fragments.HistoryFragment;
import com.randomappsinc.bro.R;
import com.randomappsinc.bro.Utils.MyApplication;

/**
 * Created by Alex on 1/29/2015.
 */
public class HomepageTabsAdapter extends FragmentPagerAdapter {
    private String tabTitles[];
    private HistoryFragment historyFragment;

    public HomepageTabsAdapter(FragmentManager fm) {
        super(fm);
        tabTitles = MyApplication.getAppContext().getResources().getStringArray(R.array.tabs);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FriendsFragment();
            case 1:
                historyFragment = new HistoryFragment();
                return historyFragment;
            default:
                return null;
        }
    }

    public HistoryFragment getHistoryFragment() {
        return historyFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
