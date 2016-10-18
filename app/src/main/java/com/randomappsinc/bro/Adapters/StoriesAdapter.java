package com.randomappsinc.bro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.bro.Models.Record;
import com.randomappsinc.bro.Persistence.DatabaseManager;
import com.randomappsinc.bro.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 8/25/15.
 */
public class StoriesAdapter extends BaseAdapter {
    private Context context;
    private List<Record> stories;

    public StoriesAdapter(Context context) {
        this.context = context;
        this.stories = DatabaseManager.get().getHistory();
    }

    public void addNewStory(Record record) {
        stories.add(0, record);
        notifyDataSetChanged();
    }

    public void deleteStoryAt(int position) {
        DatabaseManager.get().deleteRecord(stories.get(position).getId());
        stories.remove(position);
        notifyDataSetChanged();
    }

    public int getCount() {
        return stories.size();
    }

    public Record getItem(int position) {
        return stories.get(position);
    }

    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    public class StoryViewHolder {
        @Bind(R.id.event_declaration) TextView eventDeclaration;
        @Bind(R.id.time_of_event) TextView timeOfEvent;

        public StoryViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public View getView(int position, View view, ViewGroup parent) {
        StoryViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.stories_list_item, parent, false);
            holder = new StoryViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (StoryViewHolder) view.getTag();
        }

        Record record = stories.get(position);
        holder.eventDeclaration.setText(record.getEventDeclaration());
        holder.timeOfEvent.setText(record.getTimeStamp());

        return view;
    }
}