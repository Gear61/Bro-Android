package com.randomappsinc.bro.Models;

import com.orm.SugarRecord;
import com.randomappsinc.bro.Utils.FeedUtils;

/**
 * Created by alexanderchiou on 8/25/15.
 */
public class Record extends SugarRecord<Record>
{
    long id;
    String targetPhoneNumber;
    String targetName;
    String message;
    long time;

    public Record() {}

    public Record(long id, String targetPhoneNumber, String targetName, String message)
    {
        this.id = id;
        this.targetPhoneNumber = targetPhoneNumber;
        this.targetName = targetName;
        this.message = message;
        this.time = System.currentTimeMillis();
    }

    public void setTargetName(String targetName)
    {
        this.targetName = targetName;
    }

    public String getEventDeclaration()
    {
        return "You " + message + "-ed " + targetName + ".";
    }

    public String getTimeStamp()
    {
        return FeedUtils.humanizeUnixTime(time);
    }
}
