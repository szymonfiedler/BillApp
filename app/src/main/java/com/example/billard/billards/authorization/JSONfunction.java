package com.example.billard.billards.authorization;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class JSONfunction extends AppCompatActivity {
    private static final String ERROR_TAG = "E__JSONGfunctions";
    private static final String DEBUG_TAG = "D__JSONGfunctions";
    public static int[] ID_RES, ID_TABLE, HOUR_FROM, HOUR_TO, CHARGE;
    public static String[] DATE;
    public static String UNAME, USURNAME, UEMAIL;
    public static int user_id;
    public static int week, week_aft, weekend;

    public static JSONObject Register(String username, String email, String password, String name, String surname) {
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

    public static JSONObject Change(String name, String surname) {
        try {
            JSONObject temp = new JSONObject();
            temp.put("first_name", name);
            temp.put("last_name", surname);
            Log.d(DEBUG_TAG, "json login object created");
            return temp;
        } catch (JSONException ex) {
            Log.e(ERROR_TAG, "Something went wrong with JSON Username Password creation");
        }
        return null;
    }
    public static JSONObject Send(String username) {
        try {
            JSONObject temp = new JSONObject();
            temp.put("username", username);
            Log.d(DEBUG_TAG, "json login object created");
            return temp;
        } catch (JSONException ex) {
            Log.e(ERROR_TAG, "Something went wrong with JSON Username Password creation");
        }
        return null;
    }

    public static JSONObject getLoginObject(String username, String password) {
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

    public static JSONObject change_password(String password) {
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

    public static String parseAuthTokenAndUserId(String response) {
        try {
            JSONObject temp = new JSONObject(response);
            String token = "Token " + temp.getString("token");
            user_id = temp.getInt("user_id");
            return token;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String parseAuthToken2(String response) {
        try {
            JSONObject temp = new JSONObject(response);
            return temp.getString("token");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String parseResposne(String response) {
        try {
            JSONObject temp = new JSONObject(response);
            return "Detail " + temp.getString("detail");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "";
        }
    }


    public static ArrayList<String> getRes(String response) throws JSONException {

        ArrayList<String> stringArray = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);

        ID_RES = new int[jsonArray.length()];
        ID_TABLE = new int[jsonArray.length()];
        HOUR_FROM = new int[jsonArray.length()];
        HOUR_TO = new int[jsonArray.length()];
        CHARGE = new int[jsonArray.length()];
        DATE = new String[jsonArray.length()];

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

    public static ArrayList<String> getPrices(String response) throws JSONException {

        ArrayList<String> stringArray = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);


        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject rec = jsonArray.getJSONObject(i);

            week = rec.getInt("week");
            weekend = rec.getInt("weekend");
            week_aft = rec.getInt("week_aft");
            stringArray.add(jsonArray.getString(i));
        }

        return stringArray;
    }

    public static ArrayList<String> getUserInfo(String response) throws JSONException {

        ArrayList<String> stringArray = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);


        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject rec = jsonArray.getJSONObject(i);

            UNAME = rec.getString("first_name");
            USURNAME = rec.getString("last_name");
            UEMAIL = rec.getString("email");
            stringArray.add(jsonArray.getString(i));
        }

        return stringArray;
    }
}
