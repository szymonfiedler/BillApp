package com.example.billard.billards.usercenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;

import android.widget.Toast;


import com.example.billard.billards.R;

import com.example.billard.billards.authorization.AsyncResponse;
import com.example.billard.billards.authorization.JSONfunction;
import com.example.billard.billards.common.SaveSharedPreference;
import com.example.billard.billards.tables.DefaultTablesRepository;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class UserReservations extends Activity implements AsyncResponse {


    private static DeleteReservationTask mDelResTask = null;
    private static final String AUTH_TOKEN_URL = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api2/";
    private static final String L_TAG = UserReservations.class.getSimpleName();
    private static String token;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button back;
    public static int[] lst;
    public static boolean finish = false;

    public void onBackPressed() {
        Intent intent = new Intent(UserReservations.this, Home.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();

    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rezervations);
        back = findViewById(R.id.arrow);

        token = SaveSharedPreference.getUserToken(UserReservations.this);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        expListView = findViewById(R.id.lvExp);


        prepareListData();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        ExpandableListView mExpandableList = findViewById(R.id.lvExp);
        mExpandableList.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);


        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener((parent, v, groupPosition, id) -> false);


        expListView.setOnGroupExpandListener(groupPosition -> {


        });


        expListView.setOnGroupCollapseListener(groupPosition -> {


        });


        expListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            expListView.expandGroup(groupPosition);
            Toast.makeText(
                    getApplicationContext(),
                    listDataHeader.get(groupPosition)
                            + " : "
                            + listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition), Toast.LENGTH_SHORT)
                    .show();
            return false;
        });

    }

    public UserReservations(int i) {
        attemptdelres(Integer.toString(JSONfunction.ID_RES[i]));
    }

    void attemptdelres(String IDRes) {
        mDelResTask = new DeleteReservationTask(IDRes, this);
        mDelResTask.execute((Void) null);
    }

    public UserReservations() {
    }

    public void processFinish(String token) {

        finish = true;

    }

    @SuppressLint("StaticFieldLeak")
    public class DeleteReservationTask extends AsyncTask<Void, Void, String> {

        private final String sIdRes;
        private Boolean success = false;
        AsyncResponse delegate;


        DeleteReservationTask(String sIDRes, AsyncResponse delegate) {
            this.sIdRes = sIDRes;
            this.delegate = delegate;
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                return getToken();

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        String getToken() {
            InputStream is;

            try {
                URL url = new URL(AUTH_TOKEN_URL + sIdRes + "/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("DELETE");

                conn.setDoOutput(true);
                Log.d(L_TAG, "Set up data unrelated headers");


                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("Authorization", token);

                conn.connect();


                int serverResponseCode = conn.getResponseCode();

                is = conn.getInputStream();


                if (is != null) {
                    is.close();
                }
                String serverResponseMessage = conn.getResponseMessage();

                if (serverResponseCode == 204) {
                    this.success = true;
                } else {
                    Log.d(L_TAG, serverResponseMessage + " " + serverResponseCode);
                }


                return "";

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e(L_TAG, "Exeption");
                return "";
            }


        }


        @Override
        protected void onPostExecute(String response) {
            mDelResTask = null;

            if (this.success) {
                String respon = "Usunięto";
                if (respon.length() > 2) {
                    this.delegate.processFinish(respon);
                }

            } else {
                Log.d(L_TAG, response);
            }
        }

        @Override
        protected void onCancelled() {
            mDelResTask = null;

        }

    }

    private void prepareListData() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        lst = new int[JSONfunction.ID_RES.length];
        int number = 0;
        for (int i = 0; i < JSONfunction.ID_RES.length; ++i) {

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = df.parse(JSONfunction.DATE[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.clear(Calendar.HOUR);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            today.clear(Calendar.HOUR);
            today.clear(Calendar.MINUTE);
            today.clear(Calendar.SECOND);
            today.clear(Calendar.MILLISECOND);
            cal.clear(Calendar.MILLISECOND);
            if (today.before(cal) || today.equals(cal)) {

                List<String> table = new ArrayList<>();
                //dynamic add

                listDataHeader.add("Rezerwacja numer " + JSONfunction.ID_RES[i] + "   ");

                table.add("Stół " + JSONfunction.ID_TABLE[i] + "Typ - " + DefaultTablesRepository.id_type[JSONfunction.ID_TABLE[i] - 1] + "\n" + "Liczba miejsc - " + DefaultTablesRepository.num_of_seats[JSONfunction.ID_TABLE[i] - 1] + "Data" + JSONfunction.DATE[i] + "\n" + JSONfunction.HOUR_FROM[i] + ":00 - " + JSONfunction.HOUR_TO[i] + ":00" + "Opłata" + JSONfunction.CHARGE[i] + " zł");
                lst[number] = i;


                listDataChild.put(listDataHeader.get(number), table);
                number += 1;


            }
        }


    }

}
