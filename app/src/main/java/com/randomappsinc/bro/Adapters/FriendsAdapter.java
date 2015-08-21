package com.randomappsinc.bro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.bro.Models.Friend;
import com.randomappsinc.bro.R;
import com.randomappsinc.bro.Utils.ContactUtils;

import java.util.List;

/**
 * Created by alexanderchiou on 8/20/15.
 */
public class FriendsAdapter extends BaseAdapter
{
    private Context context;
    private List<Friend> friends;

    public FriendsAdapter(Context context)
    {
        this.context = context;
        this.friends = ContactUtils.getPhoneFriends(context.getContentResolver());
    }

    public int getCount()
    {
        return friends.size();
    }

    public Friend getItem(int position) {
        return friends.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        public TextView contactName;
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.friends_list_item, null);
            holder = new ViewHolder();
            holder.contactName = (TextView) v.findViewById(R.id.contact_name);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        Friend friend = friends.get(position);
        holder.contactName.setText(friend.getName());

        return v;
    }
}
