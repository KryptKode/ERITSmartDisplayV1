package com.kryptkode.cyberman.eritsmartdisplay.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kryptkode.cyberman.eritsmartdisplay.R;

/**
 * Created by Cyberman on 6/29/2017.
 */

public class EritProvider extends ContentProvider {

    private EritDatabaseHelper eritDatabaseHelper;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ALL_DATA = 100;
    private static final int ONE_DATA = 101;


    static {
        uriMatcher.addURI(EritContract.AUTHORITY,
                EritContract.DisplayBoard.TABLE_NAME, ALL_DATA);

        uriMatcher.addURI(EritContract.AUTHORITY,
                EritContract.DisplayBoard.TABLE_NAME + "/#", ONE_DATA);
    }

    @Override
    public boolean onCreate() {
        eritDatabaseHelper = new EritDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = eritDatabaseHelper.getReadableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case ALL_DATA:
                cursor = db.query(EritContract.DisplayBoard.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case ONE_DATA:
                selection = EritContract.DisplayBoard._ID + "/?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(EritContract.DisplayBoard.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
               break;
            default:
                throw  new UnsupportedOperationException(getContext().getString(R.string.unknown_uri) + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return  cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = eritDatabaseHelper.getWritableDatabase();
        Uri returnedUri;
        switch (uriMatcher.match(uri)){
            case ALL_DATA:

                long id = db.insert(EritContract.DisplayBoard.TABLE_NAME, null, values);
                returnedUri = EritContract.DisplayBoard.buildFavouritesUri(id);
                break;

            default:
                throw  new UnsupportedOperationException(getContext().getString(R.string.unknown_uri) + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //not deleting yet
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //not updating yet
        return 0;

    }
}
