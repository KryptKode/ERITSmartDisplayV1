package com.kryptkode.cyberman.eritsmartdisplay.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kryptkode.cyberman.eritsmartdisplay.R;
import com.kryptkode.cyberman.eritsmartdisplay.model.SmartDisplay;
import com.kryptkode.cyberman.eritsmartdisplay.utils.Connection;
import com.kryptkode.cyberman.eritsmartdisplay.utils.DataHelper;
import com.kryptkode.cyberman.eritsmartdisplay.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.id;
import static android.R.attr.switchMinWidth;


public class EritSmartDisplayFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> , TextView.OnEditorActionListener{
    private static final int SYNC_LOADER_ID = 100;
    private static final int SAVE_LOADER_ID = 200;
    public static final int SAVE_TO_SHARED_PREFERNCES_ID = 300;

    private String brtValue;
    private String spdValue;
    private String invValue;
    private String serial;

    private String result;

    public void setBrtValue(String brtValue) {
        this.brtValue = brtValue;
    }

    public void setSpdValue(String spdValue) {
        this.spdValue = spdValue;
    }

    public void setInvValue(String invValue) {
        this.invValue = invValue;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    private TextView textView;
    private Spinner spinner;
    private EditText enterMessageEditText;
    private EditText pmsThreeDigitEditText;
    private EditText dpkThreeDigitEditText;
    private EditText agoThreeDigitEditText;
    private EditText pmsTwoDigitEditText;
    private EditText dpkTwoDigitEditText;
    private EditText agoTwoDigitEditText;

    private TextInputLayout enterMessageTextInputLayout;
    private SmartDisplay smartDisplay;

    private String syncUrl;
    private int num;

    private SharedPreferences prefs;
    private  Map<String, String> msgs;
    private boolean firstTime = true;

    public void setNum(int num) {
        this.num = num;
    }


    public EritSmartDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_erit_smart_display, container, false);

        //instantiate the views
        textView = (TextView) view.findViewById(R.id.test);
        enterMessageEditText = (EditText) view.findViewById(R.id.edit_enter_message);
        pmsThreeDigitEditText = (EditText) view.findViewById(R.id.pms_000);
        pmsTwoDigitEditText = (EditText) view.findViewById(R.id.pms_00);
        dpkThreeDigitEditText = (EditText) view.findViewById(R.id.dpk_000);
        dpkTwoDigitEditText = (EditText) view.findViewById(R.id.dpk_00);
        agoTwoDigitEditText = (EditText) view.findViewById(R.id.ago_000);
        agoThreeDigitEditText = (EditText) view.findViewById(R.id.ago_00);
        enterMessageTextInputLayout = (TextInputLayout) view.findViewById(R.id.edit_enter_message_text_input_layout);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(spinnerItemSelectedListener);
        smartDisplay = new SmartDisplay();
        msgs = new HashMap<>();
        smartDisplay.setMessages(msgs);
        prefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        enterMessageEditText.setOnEditorActionListener(this);
        pmsThreeDigitEditText.setOnEditorActionListener(this);
        pmsTwoDigitEditText.setOnEditorActionListener(this);
        dpkThreeDigitEditText.setOnEditorActionListener(this);
        dpkTwoDigitEditText.setOnEditorActionListener(this);
        agoThreeDigitEditText.setOnEditorActionListener(this);
        agoTwoDigitEditText.setOnEditorActionListener(this);



        if (savedInstanceState != null) {
            num = savedInstanceState.getInt("INT");
        }


        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        int id = v.getId();
        switch (id){
            case R.id.edit_enter_message:
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String key = "msg" + String.valueOf(spinner.getSelectedItemPosition() + 1) ;
                    String text = enterMessageEditText.getText().toString();
                    msgs.put(key, text);
                    dismissKeyboard();
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.dpk_000:
                if (actionId == EditorInfo.IME_ACTION_NEXT){

                    smartDisplay.setDpkValue000(dpkThreeDigitEditText.getText().toString());
                    dpkTwoDigitEditText.requestFocus();

                }
                break;
            case R.id.dpk_00:
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    smartDisplay.setDpkValue00(dpkTwoDigitEditText.getText().toString());
                    dismissKeyboard();
                }
                break;
            case R.id.pms_000:
                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    smartDisplay.setPmsValue000(pmsThreeDigitEditText.getText().toString());
                    pmsTwoDigitEditText.requestFocus();
                }
                break;
            case R.id.pms_00:
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    smartDisplay.setPmsValue00(pmsTwoDigitEditText.getText().toString());
                    dismissKeyboard();
                }
                break;
            case R.id.ago_000:
                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    smartDisplay.setAgoValue000(agoThreeDigitEditText.getText().toString());
                    agoTwoDigitEditText.requestFocus();
                }
                break;
            case R.id.ago_00:
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    smartDisplay.setAgoValue00(agoTwoDigitEditText.getText().toString());
                    dismissKeyboard();
                }
                break;

        }
        storeDataToSharedPreferences();
        return true;
    }
    public  void  dismissKeyboard(){
        //hide the soft keyboard
        ((InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                getView().getWindowToken(), 0);
    }
    public void storeDataToSharedPreferences() {
        SharedPreferences.Editor editor = prefs.edit();
        for (Map.Entry<String, String> entry : smartDisplay.getMessages().entrySet()) {
            String key = entry.getKey();
            String text = entry.getValue();
            editor.putString(key, text);
        }

        editor.putString(SmartDisplay.PMS_KEY, smartDisplay.getPmsValue000());
        editor.putString(SmartDisplay.AGO_KEY, smartDisplay.getAgoValue000());
        editor.putString(SmartDisplay.DPK_KEY, smartDisplay.getDpkValue000());
        editor.putString(SmartDisplay.DPK_KEY0, smartDisplay.getDpkValue00());
        editor.putString(SmartDisplay.PMS_KEY0, smartDisplay.getPmsValue00());
        editor.putString(SmartDisplay.AGO_KEY0, smartDisplay.getAgoValue00());
        editor.apply();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("INT", num);
    }

    public void addSpinnerEntries() {
        Log.v("SPINNER", "Num_of_msg-->" + num);
        List<String> spinnerEntries = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            spinnerEntries.add(getString(R.string.message_x, i));
        }
        Log.v("SPINNER", "Array-->" + spinnerEntries.size());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, spinnerEntries
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    AdapterView.OnItemSelectedListener spinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //get the message associated with the spinner and display on the edittext
          /*  if (!firstTime){*/

            String key = "msg" + String.valueOf(position + 1);
            String message = prefs.getString(key, "");
            enterMessageEditText.setText(message);

            if (!message.equals("")) {
                enterMessageTextInputLayout.setHint(getString(R.string.content_of_message) + " " + (position + 1));
            } else {
                enterMessageTextInputLayout.setHint(getString(R.string.enter_the_message) + " " + (position + 1));
            }
            /*}
            firstTime = false;*/
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case SAVE_LOADER_ID:
                return new AsyncTaskLoader<String>(getContext()) {
                    @Override
                    public String loadInBackground() {
                        return null;
                    }
                };

            case SYNC_LOADER_ID:
                return new AsyncTaskLoader<String>(getContext()) {
                    @Override
                    protected void onStartLoading() {
                        syncUrl = NetworkUtil.buildSyncingUrl(serial);
                        if (result != null) {
                            deliverResult(result);
                        } else {
                            forceLoad();
                        }


                    }

                    @Override
                    public String loadInBackground() {
                        String data = Connection.getData(syncUrl);
                        Log.i("BG", "loadInBackground: " + data);
                        return syncUrl;
                    }

                    @Override
                    public void deliverResult(String data) {
                        result = data;
                        super.deliverResult(data);
                    }
                };

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        int id = loader.getId();
        switch (id) {
            case SAVE_LOADER_ID:

                break;
            case SYNC_LOADER_ID:
                smartDisplay = DataHelper.parseSyncedData(data);
                addToSharedPreferences(smartDisplay);
                break;
        }

    }


    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    private void addToSharedPreferences(SmartDisplay smartDisplay) {


    }


    public void syncData() {
        Toast.makeText(getContext(), "Sync...", Toast.LENGTH_LONG).show();
        LoaderManager syncDataLoaderManager = getLoaderManager();
        Loader<String> loader = syncDataLoaderManager.getLoader(SYNC_LOADER_ID);
        if (loader == null) {
            syncDataLoaderManager.initLoader(SYNC_LOADER_ID, null, this);
        } else {
            syncDataLoaderManager.restartLoader(SYNC_LOADER_ID, null, this);
        }
    }

    public void saveData() {
        Toast.makeText(getContext(), "Save", Toast.LENGTH_LONG).show();
        LoaderManager saveDataLoaderManager = getLoaderManager();
        Loader<String> loader = saveDataLoaderManager.getLoader(SYNC_LOADER_ID);
        if (loader == null) {
            saveDataLoaderManager.initLoader(SAVE_LOADER_ID, null, this);
        } else {
            saveDataLoaderManager.restartLoader(SAVE_LOADER_ID, null, this);
        }
    }


}