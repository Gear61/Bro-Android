package com.randomappsinc.bro.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.randomappsinc.bro.Fragments.FriendsFragment;
import com.randomappsinc.bro.Fragments.HistoryFragment;

/**
 * Created by Alex on 1/29/2015.
 */
public class HomepageTabsAdapter extends FragmentPagerAdapter
{
    private String tabTitles[] = new String[]{"Friends", "History"};

    public HomepageTabsAdapter(FragmentManager fm) {
        super(fm);
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
                return new HistoryFragment();

            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
