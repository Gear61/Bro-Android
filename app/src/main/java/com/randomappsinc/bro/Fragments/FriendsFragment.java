package com.randomappsinc.bro.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.randomappsinc.bro.Adapters.FriendsAdapter;
import com.randomappsinc.bro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 8/18/15.
 */
public class FriendsFragment extends Fragment {
    @Bind(R.id.link_spam_checkbox) CheckBox linkSpamCheckbox;
    @Bind(R.id.friend_input) EditText friendInput;
    @Bind(R.id.friends_list) ListView friendsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends_main, container, false);
        ButterKnife.bind(this, rootView);

        friendsList.setAdapter(new FriendsAdapter(getActivity()));
        return rootView;
    }
}
