package com.kryptkode.cyberman.eritsmartdisplay.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kryptkode.cyberman.eritsmartdisplay.EritSmartDisplayActivity;
import com.kryptkode.cyberman.eritsmartdisplay.R;

import java.util.ArrayList;
import java.util.List;


public class EritSmartDisplayFragment extends Fragment {

    private TextView textView;
    private Spinner spinner;
    private EditText enterMessageEditText;
    private EditText pmsThreeDigitEditText;
    private EditText dpkThreeDigitEditText;
    private EditText agoThreeDigitEditText;
    private EditText pmsTwoDigitEditText;
    private EditText dpkTwoDigitEditText;
    private EditText agoTwoDigitEditText;

    private SpinnerEntriesChangeListener listener;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int num;

    public EritSmartDisplayFragment() {
        // Required empty public constructor
    }

    public interface SpinnerEntriesChangeListener{
        void addSpinnerEntries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_erit_smart_display, container, false);

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        //instantiate the views
        textView = (TextView) view.findViewById(R.id.test);

        enterMessageEditText = (EditText) view.findViewById(R.id.edit_enter_message);

        pmsThreeDigitEditText = (EditText) view.findViewById(R.id.pms_000);
        pmsTwoDigitEditText = (EditText) view.findViewById(R.id.pms_00);
        dpkThreeDigitEditText = (EditText) view.findViewById(R.id.dpk_000);
        dpkTwoDigitEditText = (EditText) view.findViewById(R.id.dpk_00);
        agoTwoDigitEditText = (EditText) view.findViewById(R.id.ago_000);
        agoThreeDigitEditText = (EditText) view.findViewById(R.id.ago_00);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        //listener.addSpinnerEntries();

    }

    public void addSpinnerEntries() {
        Log.v("SPINNER", "Num_of_msg-->" + num);
        List<String> spinnerEntries = new ArrayList<>();
        for (int i = 1; i <= num; i++){
            spinnerEntries.add(getString(R.string.message_x, i));
        }
        Log.v("SPINNER", "Array-->" + spinnerEntries.size());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, spinnerEntries
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void displayText(SharedPreferences preferences){
        textView.append("BRT-->" +String.valueOf(preferences.getInt(EritSmartDisplayActivity.BRT_KEY, 0)) + "\n");
        textView.append("SPD-->" +String.valueOf(preferences.getInt(EritSmartDisplayActivity.SPD_KEY, 0)) + "\n");
        textView.append("INV-->" +String.valueOf(preferences.getString(EritSmartDisplayActivity.INV_KEY, null)) + "\n");
        textView.append("NUM-->" +String.valueOf(preferences.getString(EritSmartDisplayActivity.NUM_OF_MSGS_KEY, null)) + "\n");
        num = Integer.parseInt(preferences.getString(EritSmartDisplayActivity.NUM_OF_MSGS_KEY, null));
        Log.v("SPINNER", "Num_of_msgs-->" + num);
    }


}
