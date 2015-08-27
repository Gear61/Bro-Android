package com.randomappsinc.bro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.bro.Models.Record;
import com.randomappsinc.bro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderchiou on 8/26/15.
 */
public class StoryChoicesAdapter extends BaseAdapter
{
    private Context context;
    private List<String> storyChoices;

    public StoryChoicesAdapter(Context context, Record record)
    {
        this.context = context;
        this.storyChoices = new ArrayList<>();
        this.storyChoices.add("Re-" + record.getMessageSent() + " " + record.getTargetName());
        this.storyChoices.add("Delete story");
    }

    public int getCount() {
        return storyChoices.size();
    }

    public String getItem(int position) {
        return storyChoices.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        public TextView action;
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        final ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.story_choice_list_item, parent, false);
            holder = new ViewHolder();
            holder.action = (TextView) v.findViewById(R.id.action);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        holder.action.setText(storyChoices.get(position));

        return v;
    }
}