package com.kryptkode.cyberman.eritsmartdisplay.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Cyberman on 6/29/2017.
 */

public class EritDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "display_board.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TAG = EritDatabaseHelper.class.getSimpleName();

    public static final String CREATE_TABLE = "CREATE TABLE ";
    public static final String COMMA_SEP = ", ";
    public static final String TEXT = " TEXT ";
    public static final String PRIMARY_KEY = " PRIMARY KEY ";
    public static final String NOT_NULL = " NOT NULL ";
    public static final String INTEGER = " INTEGER ";
    public static final String OPEN_PARENTHESIS = "(";
    public static final String CLOSE_PARENTHESIS = " );";

    public EritDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATABASE_SCHEMA = CREATE_TABLE + EritContract.DisplayBoard.TABLE_NAME +
                OPEN_PARENTHESIS + EritContract.DisplayBoard._ID + INTEGER + NOT_NULL + PRIMARY_KEY + COMMA_SEP +
                EritContract.DisplayBoard.COLUMN_MESSAGE + TEXT + CLOSE_PARENTHESIS;

        Log.i(TAG, "onCreate: " + CREATE_DATABASE_SCHEMA);

        db.execSQL(CREATE_DATABASE_SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //not used yet
    }
}
