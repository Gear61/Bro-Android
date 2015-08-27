package com.randomappsinc.bro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.bro.Models.Record;
import com.randomappsinc.bro.Persistence.RecordDataSource;
import com.randomappsinc.bro.R;

import java.util.List;

/**
 * Created by alexanderchiou on 8/25/15.
 */
public class StoriesAdapter extends BaseAdapter
{
    private Context context;
    private List<Record> stories;

    public StoriesAdapter(Context context)
    {
        this.context = context;
        this.stories = RecordDataSource.getAllRecords();
    }

    public void addNewStory(Record record)
    {
        stories.add(0, record);
        notifyDataSetChanged();
    }

    public void deleteStory(long recordId)
    {
        RecordDataSource.deleteRecord(recordId);
        stories.remove(recordId);
        notifyDataSetChanged();
    }

    public int getCount()
    {
        return stories.size();
    }

    public Record getItem(int position) {
        return stories.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        public TextView eventDeclaration;
        public TextView timeOfEvent;
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.stories_list_item, null);
            holder = new ViewHolder();
            holder.eventDeclaration = (TextView) v.findViewById(R.id.event_declaration);
            holder.timeOfEvent = (TextView) v.findViewById(R.id.time_of_event);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        Record record = stories.get(position);
        holder.eventDeclaration.setText(record.getEventDeclaration());
        holder.timeOfEvent.setText(record.getTimeStamp());

        return v;
    }
}