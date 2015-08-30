package com.randomappsinc.bro.Persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alexanderchiou on 8/25/15.
 */
public class PreferencesManager
{
    private static final String INVITED_PHONE_NUMBERS = "invitedPhoneNumbers";
    private static final String MESSAGE_KEY = "message";
    private static final String HIGHEST_RECORD_ID_KEY = "highestRecordId";
    private static final String SHOULD_CONFIRM_KEY = "shouldConfirm";
    private static PreferencesManager instance;
    private SharedPreferences prefs;

    public static PreferencesManager get(Context context)
    {
        if (instance == null)
        {
            instance = getSync(context);
        }
        return instance;
    }

    private static synchronized PreferencesManager getSync(Context context)
    {
        if (instance == null)
        {
            instance = new PreferencesManager(context);
        }
        return instance;
    }

    private PreferencesManager(Context context)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Set<String> getInvitedPhoneNumbers()
    {
        return new HashSet<>(prefs.getStringSet(INVITED_PHONE_NUMBERS, new HashSet<String>()));
    }

    public PreferencesManager addInvitedPhoneNumber(String phoneNumber)
    {
        Set<String> spammedPhoneNumbers = getInvitedPhoneNumbers();
        spammedPhoneNumbers.add(phoneNumber);
        prefs.edit().putStringSet(INVITED_PHONE_NUMBERS, spammedPhoneNumbers).apply();
        return this;
    }

    public String getMessage()
    {
        return prefs.getString(MESSAGE_KEY, "Bro");
    }

    public void setMessage(String message)
    {
        prefs.edit().putString(MESSAGE_KEY, message).apply();
    }

    public int getHighestRecordId()
    {
        return prefs.getInt(HIGHEST_RECORD_ID_KEY, 0);
    }

    public void incrementHighestRecordId()
    {
        prefs.edit().putInt(HIGHEST_RECORD_ID_KEY, getHighestRecordId() + 1).apply();
    }

    public boolean getShouldConfirm()
    {
        return prefs.getBoolean(SHOULD_CONFIRM_KEY, false);
    }

    public void setShouldConfirm(boolean shouldConfirm)
    {
        prefs.edit().putBoolean(SHOULD_CONFIRM_KEY, shouldConfirm).apply();
    }
}
