package com.randomappsinc.bro.Activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.randomappsinc.bro.Persistence.PreferencesManager;
import com.randomappsinc.bro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        shouldConfirm.setChecked(PreferencesManager.get().getShouldConfirm());
        shouldConfirm.setOnCheckedChangeListener(shouldConfirmListener);
    }

    CompoundButton.OnCheckedChangeListener shouldConfirmListener = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            PreferencesManager.get().setShouldConfirm(isChecked);
            String message = isChecked ? NOW_CONFIRMING_MESSAGE : NOT_CONFIRMING_MESSAGE;
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    };
}
