package com.bluetoothpair.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by suraj on 22/7/16.
 */
public class PreferenceUtil {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(Config.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setAccessToken(String accessToken) {
        editor.putString("accessToken", accessToken);
        editor.apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString("accessToken", "");
    }
}
