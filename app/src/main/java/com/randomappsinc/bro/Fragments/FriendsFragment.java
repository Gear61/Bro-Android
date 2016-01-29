package com.randomappsinc.bro.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentCompat;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.bro.Activities.MainActivity;
import com.randomappsinc.bro.Adapters.FriendsAdapter;
import com.randomappsinc.bro.Models.Friend;
import com.randomappsinc.bro.Models.Record;
import com.randomappsinc.bro.Persistence.PreferencesManager;
import com.randomappsinc.bro.R;
import com.randomappsinc.bro.Utils.BroUtils;
import com.randomappsinc.bro.Utils.FormUtils;
import com.randomappsinc.bro.Utils.FriendServer;
import com.rey.material.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

/**
 * Created by alexanderchiou on 8/18/15.
 */
public class FriendsFragment extends Fragment {
    public static final int READ_CONTACTS_REQUEST = 1;

    @Bind(R.id.loading_contacts) View loadingContacts;
    @Bind(R.id.content) View content;
    @Bind(R.id.link_spam_checkbox) CheckBox sendInviteCheckbox;
    @Bind(R.id.friends_list) ListView friendsList;
    @Bind(R.id.friend_input) EditText friendInput;

    private FriendsAdapter friendsAdapter;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends, container, false);
        ButterKnife.bind(this, rootView);
        sendInviteCheckbox.setCheckedImmediately(true);
        context = getActivity();
        setUpFriendsList();
        return rootView;
    }

    public void setUpFriendsList() {
        new FriendsListInitializer().execute();
    }

    private class FriendsListInitializer extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            FriendServer.getInstance().initialize();
            renderFriendsList();
            return null;
        }
    }

    private void renderFriendsList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingContacts.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
                friendsAdapter = new FriendsAdapter(getActivity());
                friendsList.setAdapter(friendsAdapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_CONTACTS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpFriendsList();
                }
                else {

                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestPermission(String permission, int requestCode) {
        FragmentCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    @OnTextChanged(value = R.id.friend_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable searchInput) {
        friendsAdapter.updateWithPrefix(searchInput.toString());
    }

    @OnItemClick(R.id.friends_list)
    public void onItemClick(int position) {
        FormUtils.hideKeyboard(getActivity());

        final Friend friend = friendsAdapter.getItem(position);
        String message = PreferencesManager.get().getMessage();
        String confirmationMessage = "Do you want to text \"" + message + "\" to " + friend.getName() + "?";
        if (PreferencesManager.get().getShouldConfirm()) {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.confirm_message)
                    .content(confirmationMessage)
                    .positiveText(android.R.string.yes)
                    .negativeText(android.R.string.no)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            sendBro(friend);
                        }
                    })
                    .show();
        }
        else {
            sendBro(friend);
        }
    }

    private void sendBro(Friend friend) {
        String message = PreferencesManager.get().getMessage();
        int recordId = PreferencesManager.get().getHighestRecordId() + 1;
        Record record = new Record(recordId, friend.getPhoneNumber(), friend.getName(), message);
        String statusMessage = BroUtils.processBro(context, record, sendInviteCheckbox.isChecked());
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.showSnackbar(statusMessage);
    }

    @OnClick(R.id.clear_input)
    public void onClearInputClick() {
        friendInput.setText("");
    }
}
