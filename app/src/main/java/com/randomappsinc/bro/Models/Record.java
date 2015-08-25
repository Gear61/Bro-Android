package com.randomappsinc.bro.Models;

import com.orm.SugarRecord;

/**
 * Created by alexanderchiou on 8/25/15.
 */
public class Record extends SugarRecord<Record>
{
    long id;
    String targetPhoneNumber;
    String message;
    boolean includedLink;
    long time;

    public Record() {}

    public Record(long id, String targetPhoneNumber, String message, boolean includedLink, long time)
    {
        this.id = id;
        this.targetPhoneNumber = targetPhoneNumber;
        this.message = message;
        this.includedLink = includedLink;
        this.time = time;
    }
}
