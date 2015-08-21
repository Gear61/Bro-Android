package com.randomappsinc.bro.Utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.randomappsinc.bro.Models.Friend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexanderchiou on 8/18/15.
 */
public class ContactUtils
{
    // Given a phone number as a string, removes all non-digit characters and appends country code
    // This function is called before passing phone numbers to the back-end
    public static String sanitizePhoneNumber(String phoneNumber)
    {
        String phoneNumberSanitized = phoneNumber.replaceAll("[^0-9]", "");
        // Assume everyone is American, because we're durdles
        if (phoneNumberSanitized.length() == 10)
        {
            phoneNumberSanitized = "1" + phoneNumberSanitized;
        }
        return phoneNumberSanitized;
    }

    // Gets data to be used in ContactsAC adapters in emergency cases
    public static List<Friend> getPhoneFriends(ContentResolver resolver)
    {
        List<Friend> phoneFriends = new ArrayList<>();
        Map<String, String> phoneFriendsMap = getPhoneFriendsMap(resolver);
        for (String phoneNumber : phoneFriendsMap.keySet())
        {
            phoneFriends.add(new Friend(phoneFriendsMap.get(phoneNumber), phoneNumber));
        }
        Collections.sort(phoneFriends);
        return phoneFriends;
    }

    // Gets data to be used in ContactsAC adapters
    public static HashMap<String, String> getPhoneFriendsMap(ContentResolver resolver)
    {
        HashMap<String, String> phoneFriendsMappings = new HashMap<>();
        Cursor cur = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cur.moveToNext())
        {
            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
            {
                Cursor pCur = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                // Mechanism to only have 1 number per contact
                // Mobile is 1, home is 2, work is 3, other is 4
                int numberPriority = 100;
                String finalPhoneNumber = "";
                while (pCur.moveToNext())
                {
                    int phoneType = pCur.getInt(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    String phoneNumber = sanitizePhoneNumber(pCur.getString(pCur.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    switch (phoneType)
                    {
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                            if (numberPriority > 1) {
                                finalPhoneNumber = phoneNumber;
                                numberPriority = 1;
                            }
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                            if (numberPriority > 2) {
                                finalPhoneNumber = phoneNumber;
                                numberPriority = 2;
                            }
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                            if (numberPriority > 3) {
                                finalPhoneNumber = phoneNumber;
                                numberPriority = 3;
                            }
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                            if (numberPriority > 4) {
                                finalPhoneNumber = phoneNumber;
                                numberPriority = 4;
                            }
                            break;
                        default:
                            break;
                    }
                }
                if (finalPhoneNumber.length() == 11) {
                    phoneFriendsMappings.put(finalPhoneNumber, name);
                }
                pCur.close();
            }
        }
        cur.close();
        return phoneFriendsMappings;
    }
}
