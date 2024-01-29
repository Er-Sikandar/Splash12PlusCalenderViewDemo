package com.example.simdetactapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    public static final String MY_PREFS = "SIM_DETECT_APP";
    private static Prefs prefs;
    private static SharedPreferences sharedPreference;
    private static SharedPreferences.Editor editor;

    public static Prefs getInstance(Context context){
        sharedPreference = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
        if (prefs==null){
            prefs=new Prefs();
        }
        return prefs;
    }

    public void setPrefsString(String key, String value) {
        editor.putString(key, value).commit();
    }
    public String getPrefsString(String key) {
        return sharedPreference.getString(key, Const.EMPTY);
    }




}
