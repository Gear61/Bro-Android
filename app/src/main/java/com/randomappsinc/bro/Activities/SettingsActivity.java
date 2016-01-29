package com.randomappsinc.bro.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.bro.Persistence.PreferencesManager;
import com.randomappsinc.bro.R;
import com.randomappsinc.bro.Utils.BroUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexanderchiou on 8/29/15.
 */
public class SettingsActivity extends StandardActivity {
    public static final String NOW_CONFIRMING_MESSAGE =
            "The app will now show a confirmation dialog before sending messages.";
    public static final String NOT_CONFIRMING_MESSAGE =
            "The app will now stop showing a confirmation dialog before sending messages.";
    private Context context;

    @Bind(R.id.should_confirm) CheckBox shouldConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        ButterKnife.bind(this);
        shouldConfirm.setChecked(PreferencesManager.get(context).getShouldConfirm());
        shouldConfirm.setOnCheckedChangeListener(shouldConfirmListener);
    }

    @OnClick(R.id.choose_message)
    public void onChooseMessageClick(View view) {
        List<String> optionsList = BroUtils.getMessageOptions(context);
        CharSequence[] options = optionsList.toArray(new CharSequence[optionsList.size()]);

        new MaterialDialog.Builder(context)
                .title(R.string.message_choices)
                .items(options)
                .itemsCallbackSingleChoice(BroUtils.getCurrentMessageIndex(context),
                        new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        PreferencesManager.get(context).setMessage(text.toString());
                        Toast.makeText(context, "You will now text \"" + text.toString() + "\" in your messages.",
                                Toast.LENGTH_LONG).show();
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    CompoundButton.OnCheckedChangeListener shouldConfirmListener = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            PreferencesManager.get(context).setShouldConfirm(isChecked);
            String message = isChecked ? NOW_CONFIRMING_MESSAGE : NOT_CONFIRMING_MESSAGE;
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    };
}
