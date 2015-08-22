package com.randomappsinc.bro.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import butterknife.OnTextChanged;

/**
 * Created by alexanderchiou on 8/18/15.
 */
public class FriendsFragment extends Fragment {
    public static final String APP_LINK = "https://play.google.com/store/apps/details?id=com.randomappsinc.bro";

    @Bind(R.id.link_spam_checkbox) CheckBox linkSpamCheckbox;
    @Bind(R.id.friend_input) EditText friendInput;
    @Bind(R.id.friends_list) ListView friendsList;

    private FriendsAdapter friendsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends_main, container, false);
        ButterKnife.bind(this, rootView);

        friendsAdapter = new FriendsAdapter(getActivity());
        friendsList.setAdapter(friendsAdapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnTextChanged(value = R.id.friend_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged (Editable s)
    {
        friendsAdapter.updateWithPrefix(s.toString());
    }
}
