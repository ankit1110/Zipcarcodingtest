package com.zipcar.zipcarcodingtest.commons;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHandler {

    private static SharedPrefsHandler instance;
    private final SharedPreferences prefs;
    private Context context;

    private SharedPrefsHandler(Context mContext) {
        this.context = mContext;
        prefs = context.getSharedPreferences(
                "SavedItems", Context.MODE_PRIVATE);
    }

    public static SharedPrefsHandler getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefsHandler(context);
        }
        return instance;
    }

    public void putInt(final String key, final int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(final String key) {
        return prefs.getInt(key, 0);
    }
}
