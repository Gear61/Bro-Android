package com.randomappsinc.bro.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.randomappsinc.bro.Utils.FeedUtils;

/**
 * Created by alexanderchiou on 8/25/15.
 */
public class Record extends SugarRecord implements Parcelable {
    long id;
    String targetPhoneNumber;
    String targetName;
    String message;
    long time;

    public Record() {}

    public Record(long id, String targetPhoneNumber, String targetName, String message) {
        this.id = id;
        this.targetPhoneNumber = targetPhoneNumber;
        this.targetName = targetName;
        this.message = message;
        this.time = System.currentTimeMillis();
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public long getRecordId() {
        return id;
    }

    public String getEventDeclaration() {
        return "You " + message + "-ed " + targetName + ".";
    }

    public String getTimeStamp() {
        return FeedUtils.humanizeUnixTime(time);
    }

    public String getMessageSent() {
        return message;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTargetPhoneNumber() {
        return targetPhoneNumber;
    }

    protected Record(Parcel in) {
        id = in.readLong();
        targetPhoneNumber = in.readString();
        targetName = in.readString();
        message = in.readString();
        time = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(targetPhoneNumber);
        dest.writeString(targetName);
        dest.writeString(message);
        dest.writeLong(time);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
}
