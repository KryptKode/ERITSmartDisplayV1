package com.kryptkode.cyberman.eritsmartdisplay.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.Method;

import static android.content.Context.WIFI_SERVICE;
import static com.kryptkode.cyberman.eritsmartdisplay.SplashScreen.PASSWORD;
import static com.kryptkode.cyberman.eritsmartdisplay.SplashScreen.mSSID;

/**
 * Created by Cyberman on 7/4/2017.
 */

public class WifiHotspot {
    private Context context;

    public WifiHotspot(Context context) {
        this.context = context;
    }

    public void setUpWifiHotspot(boolean enabled){
        String SSID = mSSID;
        String PASS = PASSWORD;

        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(WIFI_SERVICE);

        if (enabled){
            wifiManager.setWifiEnabled(false); //disables all existing Wi-Fi Connections
        }else{
            if (!wifiManager.isWifiEnabled()){
                wifiManager.setWifiEnabled(true);
            }
        }

        Method[] methods = wifiManager.getClass().getDeclaredMethods();

        for (Method method : methods){
            if (method.getName().equals("setWifiApEnabled")) {
                WifiConfiguration configuration = new WifiConfiguration();
                if (!SSID.isEmpty() || !PASS.isEmpty()) {
                    configuration.SSID = SSID;
                    configuration.preSharedKey = PASS;
                    configuration.hiddenSSID = false;
                    configuration.status = WifiConfiguration.Status.ENABLED;
                    configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    configuration.allowedKeyManagement.set(4); //to use WPA2-Auth
                    configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                    configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                    configuration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                    configuration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                }

                try {
                    method.invoke(wifiManager, configuration, enabled);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }

    }

}
