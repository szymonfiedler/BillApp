package com.example.sara.billards.registration;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class JSONfunction {
    private static final String ERROR_TAG = "E__JSONGfunctions";
    private static final String DEBUG_TAG = "D__JSONGfunctions";

    public static JSONObject getLoginObject(String email, String username, String password) {
        try {
            JSONObject temp = new JSONObject();
            temp.put("email", email);
            temp.put("username", username);
            temp.put("password", password);
            Log.d(DEBUG_TAG, "json login object created");
            return temp;
        } catch (JSONException ex) {
            Log.e(ERROR_TAG, "Something went wrong with JSON Username Password creation");
        }
        return null;
    }

    public static JSONObject getLoginObject2(String email, String password) {
        try {
            JSONObject temp = new JSONObject();
            temp.put("email", email);
            temp.put("password", password);
            Log.d(DEBUG_TAG, "json login object created");
            return temp;
        } catch (JSONException ex) {
            Log.e(ERROR_TAG, "Something went wrong with JSON Username Password creation");
        }
        return null;
    }

    public static String parseAuthToken(String response) {
        try {
            JSONObject temp = new JSONObject(response);
            String token = "Token " + temp.getString("token");
            return token;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "";
        }
    }


}
