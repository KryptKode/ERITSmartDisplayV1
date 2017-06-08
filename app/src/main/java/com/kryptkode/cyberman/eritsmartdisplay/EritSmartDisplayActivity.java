package com.kryptkode.cyberman.eritsmartdisplay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.kryptkode.cyberman.eritsmartdisplay.fragments.EritSmartDisplayFragment;

import static com.kryptkode.cyberman.eritsmartdisplay.R.id.quizFragment;

public class EritSmartDisplayActivity extends AppCompatActivity implements EritSmartDisplayFragment.SpinnerEntriesChangeListener{
    public static final String BRT_KEY = "brt";
    public static final String SPD_KEY = "spd";
    public static final String INV_KEY = "inv";
    public static final String NUM_OF_MSGS_KEY = "number_of_messages";
    public static final String SERIAL_KEY = "serial";

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

        //find the screen size
        int screenSize = getResources().getConfiguration().
                screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        //if the device is a tablet, set the phoneDevice variable to false
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            phoneDevice = false;

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.your_data_is_saving, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferencesChanged){
            //since the default preferences have been set,
            //initialize the MainActivityFragment and start the quiz
            EritSmartDisplayFragment displayFragment =
                    (EritSmartDisplayFragment) getSupportFragmentManager().findFragmentById(
                            R.id.display_fragment);
            displayFragment.displayText(PreferenceManager.getDefaultSharedPreferences(this));
            displayFragment.setNum(Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString(NUM_OF_MSGS_KEY, null)));
            displayFragment.addSpinnerEntries();
            preferencesChanged = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (phoneDevice){
            getMenuInflater().inflate(R.menu.menu_erit_smart_display, menu);
            return true;
        }
        else{
            return  false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){

            case R.id.action_settings:
                Intent settingsIntent = new Intent (this , SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.action_sync:
                //TODO: Add action
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

            EritSmartDisplayFragment displayFragment =
                    (EritSmartDisplayFragment) getSupportFragmentManager().findFragmentById(
                            R.id.display_fragment);

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
                //do something
                displayFragment.setNum(Integer.parseInt(sharedPreferences.getString(NUM_OF_MSGS_KEY, null)));
            }
        }
    };

    @Override
    public void addSpinnerEntries() {

    }
}
