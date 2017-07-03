package com.kryptkode.cyberman.eritsmartdisplay.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.kryptkode.cyberman.eritsmartdisplay.model.SmartDisplay;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Cyberman on 7/1/2017.
 */

public abstract class NetworkUtil extends Context {

    public static final String TAG = NetworkUtil.class.getSimpleName();
    public static final String BASE_IP = "http://192.168.43.";
    public static final String WRITE_TO_DISPLAY = "writeDisplay";
    public static final String READ_DISPLAY = "readDisplay";



    public static String buildSendingUrl(SmartDisplay display, String serialNumber){
        String url = null;

        Uri.Builder builder = Uri.parse(BASE_IP + serialNumber).buildUpon()
                .appendPath(WRITE_TO_DISPLAY);


        builder.appendQueryParameter(SmartDisplay.PMS_KEY, display.getPmsValue000() + display.getPmsValue00());
        builder.appendQueryParameter(SmartDisplay.AGO_KEY, display.getAgoValue000() + display.getAgoValue00());
        builder.appendQueryParameter(SmartDisplay.DPK_KEY, display.getDpkValue000() + display.getDpkValue00());
        builder.appendQueryParameter(SmartDisplay.INV_KEY, display.getInvValue());
        builder.appendQueryParameter(SmartDisplay.SPD_KEY, display.getSpdValue());
        builder.appendQueryParameter(SmartDisplay.BRT_KEY, display.getBrtValue());

        try{
            url = new URL(builder.toString()).toString();
            Log.e(TAG, "buildSendingUrl: " + url);
        } catch (MalformedURLException | NullPointerException e){
            e.printStackTrace();
        }
        return url;
    }


    public static String buildSyncingUrl(String serialNumber){
        String url = null;
        Uri.Builder builder = Uri.parse(BASE_IP + serialNumber).buildUpon()
                .appendPath(READ_DISPLAY);

        try{
            url = new URL(builder.toString()).toString();
        } catch (MalformedURLException | NullPointerException e){
            e.printStackTrace();
        }
        return url;
    }
}
