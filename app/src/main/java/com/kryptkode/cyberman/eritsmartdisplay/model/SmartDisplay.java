package com.kryptkode.cyberman.eritsmartdisplay.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cyberman on 7/3/2017.
 */

public class SmartDisplay implements Parcelable {
    private Map<String, String> messages;
    private String agoValue000;
    private String dpkValue000;
    private String pmsValue000;
    private String agoValue00;
    private String dpkValue00;
    private String pmsValue00;
    private String brtValue;
    private String invValue;
    private String spdValue;


    public static final String BRT_KEY = "brt";
    public static final String INV_KEY = "inv";
    public static final String SPD_KEY = "spd";
    public static final String PMS_KEY = "pms";
    public static final String DPK_KEY = "dpk";
    public static final String AGO_KEY = "ago";

    public static final String PMS_KEY0 = "pms0";
    public static final String DPK_KEY0 = "dpk0";
    public static final String AGO_KEY0 = "ago0";
    public SmartDisplay() {
    }


    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public String getAgoValue000() {
        return agoValue000;
    }

    public void setAgoValue000(String agoValue000) {
        this.agoValue000 = agoValue000;
    }

    public String getDpkValue000() {
        return dpkValue000;
    }

    public void setDpkValue000(String dpkValue000) {
        this.dpkValue000 = dpkValue000;
    }

    public String getPmsValue000() {
        return pmsValue000;
    }

    public void setPmsValue000(String pmsValue000) {
        this.pmsValue000 = pmsValue000;
    }

    public String getAgoValue00() {
        return agoValue00;
    }

    public void setAgoValue00(String agoValue00) {
        this.agoValue00 = agoValue00;
    }

    public String getDpkValue00() {
        return dpkValue00;
    }

    public void setDpkValue00(String dpkValue00) {
        this.dpkValue00 = dpkValue00;
    }

    public String getPmsValue00() {
        return pmsValue00;
    }

    public void setPmsValue00(String pmsValue00) {
        this.pmsValue00 = pmsValue00;
    }

    public String getBrtValue() {
        return brtValue;
    }

    public void setBrtValue(String brtValue) {
        this.brtValue = brtValue;
    }


    public String getInvValue() {
        return invValue;
    }

    public void setInvValue(String invValue) {
        this.invValue = invValue;
    }

    public String getSpdValue() {
        return spdValue;
    }

    public void setSpdValue(String spdValue) {
        this.spdValue = spdValue;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.messages.size());
        for (Map.Entry<String, String> entry : this.messages.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeString(this.agoValue000);
        dest.writeString(this.dpkValue000);
        dest.writeString(this.pmsValue000);
        dest.writeString(this.agoValue00);
        dest.writeString(this.dpkValue00);
        dest.writeString(this.pmsValue00);
        dest.writeString(this.brtValue);
        dest.writeString(this.invValue);
        dest.writeString(this.spdValue);
    }

    protected SmartDisplay(Parcel in) {
        int messagesSize = in.readInt();
        this.messages = new HashMap<String, String>(messagesSize);
        for (int i = 0; i < messagesSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.messages.put(key, value);
        }
        this.agoValue000 = in.readString();
        this.dpkValue000 = in.readString();
        this.pmsValue000 = in.readString();
        this.agoValue00 = in.readString();
        this.dpkValue00 = in.readString();
        this.pmsValue00 = in.readString();
        this.brtValue = in.readString();
        this.invValue = in.readString();
        this.spdValue = in.readString();
    }

    public static final Creator<SmartDisplay> CREATOR = new Creator<SmartDisplay>() {
        @Override
        public SmartDisplay createFromParcel(Parcel source) {
            return new SmartDisplay(source);
        }

        @Override
        public SmartDisplay[] newArray(int size) {
            return new SmartDisplay[size];
        }
    };
}
