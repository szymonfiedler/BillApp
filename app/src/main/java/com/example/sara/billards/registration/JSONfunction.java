package com.example.sara.billards.registration;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JSONfunction {
    private static final String ERROR_TAG = "E__JSONGfunctions";
    private static final String DEBUG_TAG = "D__JSONGfunctions";
    public static int[] ID_RES, ID_TABLE, HOUR_FROM, HOUR_TO, CHARGE;
    public static String[] DATE;
    public static JSONObject getLoginObject(String username, String email, String password, String name, String surname) {
        try {
            JSONObject temp = new JSONObject();

            temp.put("username", username);
            temp.put("email", email);
            temp.put("password", password);
            temp.put("first_name", name);
            temp.put("last_name", surname);
            Log.d(DEBUG_TAG, "json login object created");
            return temp;
        } catch (JSONException ex) {
            Log.e(ERROR_TAG, "Something went wrong with JSON Username Password creation");
        }
        return null;
    }

    public static JSONObject getLoginObject2(String username, String password) {
        try {
            JSONObject temp = new JSONObject();
            temp.put("username", username);
            temp.put("password", password);
            Log.d(DEBUG_TAG, "json login object created");
            return temp;
        } catch (JSONException ex) {
            Log.e(ERROR_TAG, "Something went wrong with JSON Username Password creation");
        }
        return null;
    }

    public static JSONObject changepassword(String password) {
        try {
            JSONObject temp = new JSONObject();
            temp.put("new_password", password);
            Log.d(DEBUG_TAG, "json change password object created");
            return temp;
        } catch (JSONException ex) {
            Log.e(ERROR_TAG, "Something went wrong with JSON Password creation");
        }
        return null;
    }

    public static String parseAuthToken(String response) {
        try {
            JSONObject temp = new JSONObject(response);
            String token = "Token " + temp.getString("token");
            LoginActivity.user_id = temp.getInt("user_id");
            return token;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String parseAuthToken2(String response) {
        try {
            JSONObject temp = new JSONObject(response);
            String token = temp.getString("token");
            return token;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String parseResposne(String response) {
        try {
            JSONObject temp = new JSONObject(response);
            String detail = "Detail " + temp.getString("detail");
            return detail;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "";
        }
    }


    public static ArrayList<String> getRes(String response) throws JSONException {

        ArrayList<String> stringArray = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray(response);
        ID_RES = new int[jsonArray.length()];

        ID_TABLE = new int[jsonArray.length()];
        HOUR_FROM = new int[jsonArray.length()];
        HOUR_TO = new int[jsonArray.length()];
        CHARGE = new int[jsonArray.length()];
        DATE = new String[jsonArray.length()];
        // JSONObject temp = new JSONObject("{\"ID_RES\":\"ID_USER\",\"ID_TABLE\":\"DATE\":\"HOUR_FROM\":\"HOUR_TO\":\"CHARGE\":\"CONFIRMED\"}");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject rec = jsonArray.getJSONObject(i);
            ID_RES[i] = rec.getInt("ID_RES");

            ID_TABLE[i] = rec.getInt("ID_TABLE");
            HOUR_FROM[i] = rec.getInt("HOUR_FROM");
            HOUR_TO[i] = rec.getInt("HOUR_TO");
            CHARGE[i] = rec.getInt("CHARGE");
            DATE[i] = rec.getString("DATE");
            stringArray.add(jsonArray.getString(i));
        }

        return stringArray;
    }

}
