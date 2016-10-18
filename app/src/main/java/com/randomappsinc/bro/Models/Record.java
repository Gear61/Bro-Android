package com.randomappsinc.bro.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.randomappsinc.bro.Persistence.RecordDO;
import com.randomappsinc.bro.Utils.FeedUtils;

import io.realm.annotations.PrimaryKey;

/**
 * Created by alexanderchiou on 10/17/16.
 */

public class Record implements Parcelable {
    @PrimaryKey
    private long id;

    private String targetPhoneNumber;
    private String targetName;
    private String message;
    private long time;

    public Record() {}

    public Record(long id, String targetPhoneNumber, String targetName, String message) {
        this.id = id;
        this.targetPhoneNumber = targetPhoneNumber;
        this.targetName = targetName;
        this.message = message;
        this.time = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventDeclaration() {
        return "You " + message + "-ed " + targetName + ".";
    }

    public String getTargetPhoneNumber() {
        return targetPhoneNumber;
    }

    public void setTargetPhoneNumber(String targetPhoneNumber) {
        this.targetPhoneNumber = targetPhoneNumber;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return FeedUtils.humanizeUnixTime(time);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public RecordDO toRecordDO() {
        RecordDO recordDO = new RecordDO();
        recordDO.setId(id);
        recordDO.setTargetPhoneNumber(targetPhoneNumber);
        recordDO.setTargetName(targetName);
        recordDO.setMessage(message);
        recordDO.setTime(time);
        return recordDO;
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
