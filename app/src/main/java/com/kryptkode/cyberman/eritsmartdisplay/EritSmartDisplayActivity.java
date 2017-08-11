package com.kryptkode.cyberman.eritsmartdisplay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.kryptkode.cyberman.eritsmartdisplay.fragments.EritSmartDisplayFragment;
import com.kryptkode.cyberman.eritsmartdisplay.utils.NetworkUtil;
import com.kryptkode.cyberman.eritsmartdisplay.utils.WifiHotspot;

import static com.kryptkode.cyberman.eritsmartdisplay.SplashScreen.REQUEST_SETTINGS_PERMISSONS;


public class EritSmartDisplayActivity extends AppCompatActivity {
    public static final String BRT_KEY = "brt";
    public static final String SPD_KEY = "spd";
    public static final String INV_KEY = "inv";
    public static final String NUM_OF_MSGS_KEY = "number_of_messages";
    public static final String SERIAL_KEY = "serial";
    private static final int WIFI_LOADER = 300;
    private FloatingActionButton fab;
    private WifiHotspot wifiHotspot;
    private Menu menu;


    EritSmartDisplayFragment smartDisplayFragment;
    private boolean phoneDevice = true; // to check if the device is a phone in order to set the layout for portrait mode
    private boolean preferencesChanged = true; //to check if the preference changed in order to update the UI with the current setting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erit_smart_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set the default values in the shared preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        //register the listener for the SharedPreferences changes
        PreferenceManager.getDefaultSharedPreferences(this).
                registerOnSharedPreferenceChangeListener(preferencesChangeListener);

        wifiHotspot =  new WifiHotspot(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(fabListener);

        smartDisplayFragment = (EritSmartDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.display_fragment);

        //find the screen size
        int screenSize = getResources().getConfiguration().
                screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        //if the device is a tablet, set the phoneDevice variable to false
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE)
            phoneDevice = false;
    }

    View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            smartDisplayFragment.saveData();
            Snackbar.make(view, R.string.your_data_is_saving, Snackbar.LENGTH_LONG).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_erit_smart_display, menu);
        if (!phoneDevice) {
            hideSettingsMenu(R.id.action_settings);
        }
            return true;



    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpSpinner(Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(this).getString(NUM_OF_MSGS_KEY, null)));

        if(preferencesChanged){
            smartDisplayFragment.setSerial(PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext()).getString(SERIAL_KEY, null));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

       //if the hotspot is not turned on, turn it on
            wifiHotspot.setUpWifiHotspot(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
            wifiHotspot.setUpWifiHotspot(false);

    }

    private void hideSettingsMenu(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
        this.invalidateOptionsMenu();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){

            case R.id.action_settings:
                Intent settingsIntent = new Intent (this , SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.action_sync:
                //TODO: Add action
                smartDisplayFragment.syncData();
                Snackbar.make(findViewById(R.id.root), R.string.your_data_is_syncing, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.action_about:
                //TODO: Add action
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            preferencesChanged = true; //true because the user has changed the settings

            /*EritSmartDisplayFragment displayFragment =
                    (EritSmartDisplayFragment) getSupportFragmentManager().findFragmentById(
                            R.id.display_fragment);*/

            if (key.equals(BRT_KEY)){
                //do something

            }
            if (key.equals(SPD_KEY)){
                //do something
            }
            if (key.equals(INV_KEY)){
                //do something

            }
            if (key.equals(NUM_OF_MSGS_KEY)){
               setUpSpinner(Integer.parseInt(PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext()).getString(NUM_OF_MSGS_KEY, null)));
            }
            if (key.equals(SERIAL_KEY)){
                smartDisplayFragment.setSerial(PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext()).getString(SERIAL_KEY, null));
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(preferencesChangeListener);

    }



    public  void setUpSpinner(int num){
        //since the default preferences have been set,
        //initialize the EritSmartDisplayFragment
            smartDisplayFragment.setNum(num);
            smartDisplayFragment.addSpinnerEntries();


    }

}
