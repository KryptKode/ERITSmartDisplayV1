package com.kryptkode.cyberman.eritsmartdisplay;

import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;


import com.kryptkode.cyberman.eritsmartdisplay.utils.WifiHotspot;

import java.lang.ref.WeakReference;

public class SplashScreen extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Boolean> {
    private static  final int SPLASH_DISPLAY_LENGTH = 1500;
    private static final int WIFI_LOADER = 100;

    public static final String mSSID = "Erit Smart Display";
    public static final String PASSWORD = "1234567890"; //TODO: Add to build.gradle
    public static final int REQUEST_SETTINGS_PERMISSONS = 200;

    private WifiHotspot hotspot;

    // Declare the Handler as a member variable
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Pass a new instance of StartMainActivityRunnable with reference to 'this'.
        /* Duration of wait */
        hotspot = new WifiHotspot(this);
        createHotspot();

    }

    private void delayOneSecond() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void startMainActivity() {
        Intent mainIntent = new Intent(this, EritSmartDisplayActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void createHotspot() {
        h();
        if (Build.VERSION.SDK_INT < 23 || Settings.System.canWrite(getApplicationContext())) {
            LoaderManager wifiHospotLoader = getSupportLoaderManager();
            Loader<String> loader = wifiHospotLoader.getLoader(WIFI_LOADER);
            if (loader == null) {
                wifiHospotLoader.initLoader(WIFI_LOADER, null, this);
            } else {
                wifiHospotLoader.restartLoader(WIFI_LOADER, null, this);
            }
        }
    }


    public void g() {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.System.canWrite(getApplicationContext())) {
            startActivityForResult(new Intent("android.settings.action.MANAGE_WRITE_SETTINGS",
                    Uri.parse("package:" + getPackageName())), REQUEST_SETTINGS_PERMISSONS);
        }
    }


    public  void h() {
        if (Build.VERSION.SDK_INT < 23 || Settings.System.canWrite(getApplicationContext())) {
            //something here
            return;
        }

        if (Build.VERSION.SDK_INT >= 23 && !Settings.System.canWrite(getApplicationContext())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.ThemeOverlay_Material_Dialog);
            builder.setTitle(R.string.allow_title);
            builder.setMessage(R.string.allow_message);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {
                    g();
                }
            });
          /* builder.setNegativeButton(R.string.noo, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });*/
            builder.setCancelable(false);
            builder.show();
        }
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Boolean>(this) {
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public Boolean loadInBackground() {

                return hotspot.setUpWifiHotspot(true);
            }

            @Override
            public void deliverResult(Boolean data) {
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        delayOneSecond();
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_SETTINGS_PERMISSONS:
                if (Build.VERSION.SDK_INT >= 23 && Settings.System.canWrite(getApplicationContext())) {
                    LoaderManager wifiHospotLoader = getSupportLoaderManager();
                    Loader<String> loader = wifiHospotLoader.getLoader(WIFI_LOADER);
                    if (loader == null) {
                        wifiHospotLoader.initLoader(WIFI_LOADER, null, this);
                    } else {
                        wifiHospotLoader.restartLoader(WIFI_LOADER, null, this);
                    }
                }
                break;
        }
    }








}

