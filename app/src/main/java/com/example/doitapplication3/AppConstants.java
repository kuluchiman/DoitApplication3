package com.example.doitapplication3;

import android.os.Handler;
import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppConstants {

    public static final int REQ_LOCATION_BY_ADDRESS = 101;
    public static final int REQ_WEATHER_BY_GRID = 102;

    public static final int REQ_PHOTO_CAPTURE = 103;
    public static final int REQ_PHOTO_SELECTION = 104;

    public static final int CONTENT_PHOTO = 105;
    public static final int CONTENT_PHOTO_EX = 106;

    public static String FOLDER_PHOTO;

    public static String DATABASE_NAME = "note1.db";

    public static final int MODE_INSERT = 1;
    public static final int MODE_MODIFY = 2;

    public static Timestamp timestamp;


    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH");
    public static SimpleDateFormat dateFormat3 = new SimpleDateFormat("MM-dd");
    public static SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat dateFormat5 = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat dateFormat6 = new SimpleDateFormat("dd");
    public static SimpleDateFormat dateFormat7 = new SimpleDateFormat("EEE");
    public static SimpleDateFormat dateFormat8 = new SimpleDateFormat("MMM, dd / yyyy", new Locale("en", "US"));
    public static SimpleDateFormat dateFormat9 = new SimpleDateFormat("EEE, MMM dd / yyyy", new Locale("en", "US"));



    private static Handler handler = new Handler();
    private static final String TAG = "AppConstants";
    public static void println(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, data);
            }
        });
    }

}
