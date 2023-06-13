package com.example.chick.helpers;

import android.content.SharedPreferences;

public class PreferencesHelper {
    private static String token;

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    public static final String APP_PREFERENCES = "app";
    public static final String APP_PREFERENCES_TOKEN = "token";

    public static void setPreferences(SharedPreferences preferences) {
        PreferencesHelper.preferences = preferences;
    }

    public static void setEditor(SharedPreferences.Editor editor) {
        PreferencesHelper.editor = editor;
    }

    public static String getToken() {
        if (token == null) token = preferences.getString(APP_PREFERENCES_TOKEN, null);
        return token;
    }

    public static void saveToken(String value) {
        token = value;
        editor.putString(APP_PREFERENCES_TOKEN, value);
        editor.apply();
    }
}
