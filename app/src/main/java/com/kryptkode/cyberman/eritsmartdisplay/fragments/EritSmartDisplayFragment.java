package com.kryptkode.cyberman.eritsmartdisplay.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kryptkode.cyberman.eritsmartdisplay.EritSmartDisplayActivity;
import com.kryptkode.cyberman.eritsmartdisplay.R;
import com.kryptkode.cyberman.eritsmartdisplay.data.EritContract;

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
    private int num;

    private SpinnerEntriesChangeListener listener;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }



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

        //instantiate the views
        textView = (TextView) view.findViewById(R.id.test);
        enterMessageEditText = (EditText) view.findViewById(R.id.edit_enter_message);
        enterMessageEditText.setOnEditorActionListener(onEditorListener);
        pmsThreeDigitEditText = (EditText) view.findViewById(R.id.pms_000);
        pmsTwoDigitEditText = (EditText) view.findViewById(R.id.pms_00);
        dpkThreeDigitEditText = (EditText) view.findViewById(R.id.dpk_000);
        dpkTwoDigitEditText = (EditText) view.findViewById(R.id.dpk_00);
        agoTwoDigitEditText = (EditText) view.findViewById(R.id.ago_000);
        agoThreeDigitEditText = (EditText) view.findViewById(R.id.ago_00);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        /*setNum(8);
        addSpinnerEntries();*/
        if(savedInstanceState != null){
            num = savedInstanceState.getInt("INT");
        }

        return  view;
    }

    //when the enter key is pressed on the enter_a_msg edit text, this method ois triggered
    TextView.OnEditorActionListener onEditorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                insertValueToDatabase();
            }
            return true;
        }
    };

    public void insertValueToDatabase(){
        int id = spinner.getSelectedItemPosition() + 1;
        Log.i("ASYNC", "insertValueToDatabase: " + id);
        String text = enterMessageEditText.getText().toString();
        ContentValues values = new ContentValues(0);
        values.put(EritContract.DisplayBoard._ID, id);
        values.put(EritContract.DisplayBoard.COLUMN_MESSAGE, text);
        InsertToDatabase insertTask = new InsertToDatabase();
        insertTask.execute(values);
    }

    @Override
    public void onStart() {
        super.onStart();
       // listener.addSpinnerEntries();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

       // listener = (SpinnerEntriesChangeListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // listener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("INT", num);
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

    private class InsertToDatabase extends AsyncTask<ContentValues, Void, Uri>{

        @Override
        protected Uri doInBackground(ContentValues... params) {
            ContentValues values = params[0];
            return getActivity().getContentResolver().insert(EritContract.DisplayBoard.CONTENT_URI,values );
        }

        @Override
        protected void onPostExecute(Uri uri) {
            super.onPostExecute(uri);
            Log.i("ASYNC", "onPostExecute: " + uri.toString());
            Toast.makeText(getContext(), "Insert Successful", Toast.LENGTH_SHORT).show();
        }
    }
}
