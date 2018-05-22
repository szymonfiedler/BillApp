package com.example.billard.billards.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_NAME = "username";
    static final int PREF_USER_ID = 0;
    static final String PREF_USER_TOKEN = "Token 00";
    public static int size = 0;
    public static String dane;

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.apply();
    }

    public static void setUserToken(Context ctx, String Token) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_TOKEN, Token);
        editor.apply();
    }

    public static void setUserID(Context ctx, int user_id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(String.valueOf(PREF_USER_ID), user_id);
        editor.apply();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getUserToken(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_TOKEN, "");
    }

    public static int getUserID(Context ctx) {
        return getSharedPreferences(ctx).getInt(String.valueOf(PREF_USER_ID), 0);
    }

    public static void clearUserInfo(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.apply();
    }

}

