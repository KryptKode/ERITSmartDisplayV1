package com.kryptkode.cyberman.eritsmartdisplay.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Cyberman on 6/29/2017.
 */

public class EritContract {
    public static final String AUTHORITY = "com.kryptkode.cyberman.eritsmartdisplay.data";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static class DisplayBoard implements BaseColumns {
        public static final String TABLE_NAME = "display_board_data";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String COLUMN_MESSAGE = "message";

        public static Uri buildFavouritesUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
