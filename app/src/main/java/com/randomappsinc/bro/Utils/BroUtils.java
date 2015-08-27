package com.randomappsinc.bro.Utils;

import android.content.Context;
import android.content.Intent;

import com.randomappsinc.bro.Models.Friend;
import com.randomappsinc.bro.Models.Record;
import com.randomappsinc.bro.Persistence.PreferencesManager;
import com.randomappsinc.bro.Persistence.RecordDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by alexanderchiou on 8/25/15.
 */
public class BroUtils
{
    public static final String APP_LINK_MESSAGE = "\n\nJoin the brovolution: " +
            "https://play.google.com/store/apps/details?id=com.randomappsinc.bro";

    public static List<String> returnMessageOptions(Context context)
    {
        List<String> messageOptions = new ArrayList<>();
        messageOptions.add("Bro");
        int numFriendsInvited = PreferencesManager.get(context).getInvitedPhoneNumbers().size();
        if (numFriendsInvited >= 1)
        {
            messageOptions.add("Brah");
        }
        if (numFriendsInvited >= 2)
        {
            messageOptions.add("Bruh");
        }
        if (numFriendsInvited >= 3)
        {
            messageOptions.add("Broski");
        }
        if (numFriendsInvited >= 4)
        {
            messageOptions.add("Broseph");
        }
        if (numFriendsInvited >= 5)
        {
            messageOptions.add("Brochacho");
        }
        return messageOptions;
    }

    public static String processBro(Context context, Friend friend, boolean sendInvite)
    {
        String message = PreferencesManager.get(context).getMessage();
        String textMessage = message;
        String statusMessage = "You " + message + "-ed " + friend.getName() + ".";
        Set<String> invitedPhoneNumbers = PreferencesManager.get(context).getInvitedPhoneNumbers();
        if (sendInvite)
        {
            if (invitedPhoneNumbers.contains(friend.getPhoneNumber()))
            {
                statusMessage += "You have already shared Bro with this friend, so we didn't add a link to your text.";
            }
            else
            {
                textMessage += APP_LINK_MESSAGE;
                String unlockedMessage = getUnlockedMessage(invitedPhoneNumbers.size());
                if (!unlockedMessage.isEmpty())
                {
                    statusMessage += " Also, by asking your friend to join the brovolution, " +
                            "you have unlocked the word \"" + unlockedMessage + "\".";
                }
                PreferencesManager.get(context).addInvitedPhoneNumber(friend.getPhoneNumber());
            }
        }
        int recordId = PreferencesManager.get(context).getHighestRecordId() + 1;
        Record record = new Record(recordId, friend.getPhoneNumber(), friend.getName(), message);
        // Update the DB/Shared Preferences
        RecordDataSource.insertRecord(record);
        PreferencesManager.get(context).incrementHighestRecordId();

        // Send the text
        // SmsManager.getDefault().sendTextMessage(friend.getPhoneNumber(), null, textMessage, null, null);

        return statusMessage;
    }

    public static String getUnlockedMessage(int numFriendsInvited)
    {
        if (numFriendsInvited == 0)
        {
           return "Brah";
        }
        if (numFriendsInvited == 1)
        {
           return "Bruh";
        }
        if (numFriendsInvited == 2)
        {
            return "Broski";
        }
        if (numFriendsInvited == 3)
        {
            return "Broseph";
        }
        if (numFriendsInvited == 4)
        {
            return "Brochacho";
        }
        return "";
    }
}
