package com.randomappsinc.bro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.bro.Models.Friend;
import com.randomappsinc.bro.R;
import com.randomappsinc.bro.Utils.FriendServer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 8/20/15.
 */
public class FriendsAdapter extends BaseAdapter {
    private Context context;
    private List<Friend> friends;

    public FriendsAdapter(Context context) {
        this.context = context;
        this.friends = FriendServer.getInstance().getMatches("");
    }

    public void updateWithPrefix(String prefix) {
        this.friends = FriendServer.getInstance().getMatches(prefix);
        notifyDataSetChanged();
    }

    public int getCount() {
        return friends.size();
    }

    public Friend getItem(int position) {
        return friends.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public class FriendViewHolder {
        @Bind(R.id.contact_name) TextView contactName;

        public FriendViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public View getView(int position, View view, ViewGroup parent) {
        FriendViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.friends_list_item, parent, false);
            holder = new FriendViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (FriendViewHolder) view.getTag();
        }
        holder.contactName.setText(getItem(position).getName());
        return view;
    }
}
