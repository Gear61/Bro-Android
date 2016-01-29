package com.randomappsinc.bro.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.randomappsinc.bro.Adapters.StoriesAdapter;
import com.randomappsinc.bro.Adapters.StoryChoicesAdapter;
import com.randomappsinc.bro.Models.Record;
import com.randomappsinc.bro.Persistence.PreferencesManager;
import com.randomappsinc.bro.R;
import com.randomappsinc.bro.Utils.BroUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by alexanderchiou on 8/18/15.
 */
public class HistoryFragment extends Fragment
{
    @Bind(R.id.stories_list) ListView storiesList;
    @Bind(R.id.no_stories) TextView noStories;

    private StoriesAdapter storiesAdapter;
    private BroReceiver broReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broReceiver = new BroReceiver();
        getActivity().registerReceiver(broReceiver, new IntentFilter(getString(R.string.bro_event_key)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.history, container, false);
        ButterKnife.bind(this, rootView);

        storiesAdapter = new StoriesAdapter(getActivity());
        storiesList.setAdapter(storiesAdapter);
        refreshContent();

        return rootView;
    }

    public void refreshContent()
    {
        if (storiesAdapter.getCount() == 0)
        {
            storiesList.setVisibility(View.GONE);
            noStories.setVisibility(View.VISIBLE);
        }
        else
        {
            noStories.setVisibility(View.GONE);
            storiesList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try
        {
            getActivity().unregisterReceiver(broReceiver);
        }
        catch (IllegalArgumentException ignored) {}
    }

    private class BroReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Record record = intent.getParcelableExtra(getString(R.string.record_key));
            storiesAdapter.addNewStory(record);
            refreshContent();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // If this fragment is becoming visible
        if (isVisibleToUser)
        {
            // Hide the keyboard if it's open
            View view = getActivity().getCurrentFocus();
            if (view != null)
            {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            // Update timestamps
            storiesAdapter.notifyDataSetChanged();
        }
    }

    @OnItemClick(R.id.stories_list)
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.ordinary_listview, null);
        alertDialogBuilder.setView(convertView);
        final Record record = storiesAdapter.getItem(position);
        ListView storyChoices = (ListView) convertView.findViewById(R.id.listView1);
        final StoryChoicesAdapter adapter = new StoryChoicesAdapter(getActivity(), record);
        storyChoices.setAdapter(adapter);
        final AlertDialog storyChosenDialog = alertDialogBuilder.show();
        storyChoices.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int dialogPosition, long id)
            {
                storyChosenDialog.dismiss();
                String action = adapter.getItem(dialogPosition);
                if (action.startsWith("Re-"))
                {
                    int recordId = PreferencesManager.get().getHighestRecordId() + 1;
                    Record baseRecord = storiesAdapter.getItem(position);
                    Record record = new Record(recordId, baseRecord.getTargetPhoneNumber(),
                            baseRecord.getTargetName(), baseRecord.getMessageSent());
                    String statusMessage = BroUtils.processBro(getActivity(), record, false);
                    Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_LONG).show();
                }
                else if (action.startsWith("Delete"))
                {
                    storiesAdapter.deleteStoryAt(position);
                    refreshContent();
                }
            }
        });
        storyChosenDialog.setCanceledOnTouchOutside(true);
        storyChosenDialog.setCancelable(true);
    }
}

