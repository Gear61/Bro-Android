package com.randomappsinc.bro.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.bro.Activities.MainActivity;
import com.randomappsinc.bro.Adapters.StoriesAdapter;
import com.randomappsinc.bro.Models.Record;
import com.randomappsinc.bro.Persistence.DatabaseManager;
import com.randomappsinc.bro.R;
import com.randomappsinc.bro.Utils.BroUtils;
import com.randomappsinc.bro.Utils.FormUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by alexanderchiou on 8/18/15.
 */
public class HistoryFragment extends Fragment {
    @Bind(R.id.stories_list) ListView storiesList;
    @Bind(R.id.no_stories) TextView noStories;

    private StoriesAdapter storiesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.history, container, false);
        ButterKnife.bind(this, rootView);

        storiesAdapter = new StoriesAdapter(getActivity());
        storiesList.setAdapter(storiesAdapter);
        refreshContent();

        return rootView;
    }

    public void refreshContent() {
        if (storiesAdapter.getCount() == 0) {
            storiesList.setVisibility(View.GONE);
            noStories.setVisibility(View.VISIBLE);
        }
        else {
            noStories.setVisibility(View.GONE);
            storiesList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void addNewStory(Record record) {
        storiesAdapter.addNewStory(record);
        refreshContent();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            FormUtils.hideKeyboard(getActivity());
            // Update timestamps
            storiesAdapter.notifyDataSetChanged();
        }
    }

    @OnItemClick(R.id.stories_list)
    public void onItemClick(final int position) {
        Record record = storiesAdapter.getItem(position);
        List<String> storyChoices = new ArrayList<>();
        storyChoices.add("Re-" + record.getMessage() + " " + record.getTargetName());
        storyChoices.add(getString(R.string.delete_story));

        CharSequence[] options = storyChoices.toArray(new CharSequence[storyChoices.size()]);

        final HistoryFragment historyFragment = this;
        new MaterialDialog.Builder(getActivity())
                .title(R.string.story_options)
                .items(options)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                long recordId = DatabaseManager.get().getNextId();
                                Record baseRecord = storiesAdapter.getItem(position);
                                Record record = new Record(recordId, baseRecord.getTargetPhoneNumber(),
                                        baseRecord.getTargetName(), baseRecord.getMessage());
                                String statusMessage = BroUtils.processBro(record, false, historyFragment);
                                MainActivity mainActivity = (MainActivity) getActivity();
                                mainActivity.showSnackbar(statusMessage);
                                break;
                            case 1:
                                storiesAdapter.deleteStoryAt(position);
                                refreshContent();
                                break;
                        }
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .negativeText(android.R.string.no)
                .show();
    }
}

