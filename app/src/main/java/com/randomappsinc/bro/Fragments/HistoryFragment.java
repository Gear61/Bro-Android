package com.randomappsinc.bro.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.bro.Adapters.StoriesAdapter;
import com.randomappsinc.bro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 8/18/15.
 */
public class HistoryFragment extends Fragment
{
    @Bind(R.id.stories_list) ListView storiesList;
    @Bind(R.id.no_stories) TextView noStories;

    private StoriesAdapter storiesAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.history, container, false);
        ButterKnife.bind(this, rootView);

        storiesAdapter = new StoriesAdapter(getActivity());
        storiesList.setAdapter(storiesAdapter);
        if (storiesAdapter.getCount() == 0)
        {
            noStories.setVisibility(View.VISIBLE);
        }
        else
        {
            storiesList.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

