package com.randomappsinc.bro.Models;

import com.orm.SugarRecord;

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
}
