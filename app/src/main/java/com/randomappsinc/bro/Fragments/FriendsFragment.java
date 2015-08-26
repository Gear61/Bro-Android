package com.randomappsinc.bro.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.randomappsinc.bro.Adapters.FriendsAdapter;
import com.randomappsinc.bro.R;
import com.randomappsinc.bro.Utils.BroUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

/**
 * Created by alexanderchiou on 8/18/15.
 */
public class FriendsFragment extends Fragment {
    @Bind(R.id.link_spam_checkbox) CheckBox sendInviteCheckbox;
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

    @OnItemClick(R.id.friends_list)
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id)
    {
        String statusMessage = BroUtils.processBro(getActivity(), friendsAdapter.getItem(position),
                sendInviteCheckbox.isChecked());
        Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_LONG).show();
    }
}
