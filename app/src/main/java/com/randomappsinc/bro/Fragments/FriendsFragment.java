package com.randomappsinc.bro.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.randomappsinc.bro.Adapters.FriendsAdapter;
import com.randomappsinc.bro.Models.Friend;
import com.randomappsinc.bro.Models.Record;
import com.randomappsinc.bro.Persistence.PreferencesManager;
import com.randomappsinc.bro.R;
import com.randomappsinc.bro.Utils.BroUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

/**
 * Created by alexanderchiou on 8/18/15.
 */
public class FriendsFragment extends Fragment
{
    @Bind(R.id.instructions) TextView instructions;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.friends, container, false);
        ButterKnife.bind(this, rootView);
        context = getActivity();

        friendsAdapter = new FriendsAdapter(getActivity());
        friendsList.setAdapter(friendsAdapter);
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        instructions.setText("Click any friend to text them \"" +
                PreferencesManager.get(getActivity()).getMessage() + "\". Share the link to unlock words.");
    }

    @Override
    public void onDestroyView()
    {
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
        // Hide the keyboard if it's open
        View focusedView = getActivity().getCurrentFocus();
        if (focusedView != null)
        {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        final String message = PreferencesManager.get(context).getMessage();
        final Friend friend = friendsAdapter.getItem(position);
        new AlertDialog.Builder(context)
                .setTitle(R.string.confirm_message)
                .setMessage("Are you sure you want to text \"" + message + "\" to " + friend.getName() + "?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        int recordId = PreferencesManager.get(getActivity()).getHighestRecordId() + 1;
                        Record record = new Record(recordId, friend.getPhoneNumber(), friend.getName(), message);
                        String statusMessage = BroUtils.processBro(getActivity(), record, sendInviteCheckbox.isChecked());
                        Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @OnClick(R.id.clear_input)
    public void onClearInputClick(View view)
    {
        friendInput.setText("");
    }
}
