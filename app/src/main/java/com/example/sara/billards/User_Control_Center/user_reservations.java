package com.example.sara.billards.User_Control_Center;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


import com.example.sara.billards.DateUtils;

import com.example.sara.billards.EndActivity;
import com.example.sara.billards.MainActivity;
import com.example.sara.billards.R;

import com.example.sara.billards.registration.AsyncResponse;
import com.example.sara.billards.registration.JSONfunction;
import com.example.sara.billards.registration.LoginActivity;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.util.Calendar;
import java.util.Date;


public class user_reservations extends Activity implements AsyncResponse {


    Context context;
    private DeleteReservationTask mDelResTask = null;
    private static final String AUTH_TOKEN_URL = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api2/";
    private static final String L_TAG = user_reservations.class.getSimpleName();



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_rezervations);
        LinearLayout scrViewButLay = (LinearLayout) findViewById(R.id.L2);
        scrViewButLay.setOrientation(LinearLayout.VERTICAL);


        for (int i = 0; i < JSONfunction.ID_RES.length; ++i) {
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = df.parse(JSONfunction.DATE[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
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

                final TextView rez = new TextView(this);
                rez.setId(i);
                rez.setTextColor(Color.WHITE);
                String nazwa = "Rezerwacja nr " + JSONfunction.ID_RES[i] + "\n" + "Stół numer: " + JSONfunction.ID_TABLE[i] + "\n" + "W godzinach: " + JSONfunction.HOUR_FROM[i] + " - " + JSONfunction.HOUR_TO[i] + "\n" + "Data: " + JSONfunction.DATE[i] + "\n" + "Opłata: " + JSONfunction.CHARGE[i] + " zł";
                rez.setText(nazwa);
                rez.setTextSize(20);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
                rez.setLayoutParams(layoutParams);
                rez.setGravity(Gravity.CENTER);
                scrViewButLay.addView(rez);

                final Button cnl = new Button(this);
                cnl.setId(i);
                cnl.setBackgroundResource(R.drawable.button_shapes);
                cnl.setTextColor(Color.WHITE);
                String n2 = "Odwołaj rezerwacje";
                cnl.setText(n2);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(850,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams2.gravity = Gravity.CENTER;
                layoutParams2.setMargins(10, 10, 10, 10); //
                cnl.setLayoutParams(layoutParams2);
                scrViewButLay.addView(cnl);
                int finalI = i;
                Calendar cal2, today2;
                cal = Calendar.getInstance();
                today = Calendar.getInstance();
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                today.set(Calendar.HOUR_OF_DAY, 0);
                String Today, now;
                Today = today.toString();
                Today = DateUtils.formatDate(today.getTime());
                now = sdf.format(cal.getTime());
                String finalToday = Today;
                int finalI1 = i;
                cnl.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (JSONfunction.DATE[finalI1].equals(finalToday)) {
                            int Now = Integer.parseInt(now.substring(0, 2));

                            if (JSONfunction.HOUR_FROM[finalI1] - Now < 6) {
                                Toast.makeText(getApplicationContext(), "Nie można usunąć rezerwacji! ", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            attemptdelres(Integer.toString(JSONfunction.ID_RES[finalI]));
                        }
                    }
                });
            }
        }
    }

    private void attemptdelres(String IDRes) {
        mDelResTask = new DeleteReservationTask(IDRes, this);
        mDelResTask.execute((Void) null);
    }

    public void processFinish(String token) {

        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(user_reservations.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(user_reservations.this);
        }

        builder.setTitle("Usunięto")
                .setMessage("Usunięto rezerwację!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }

    public class DeleteReservationTask extends AsyncTask<Void, Void, String> {

        private final String sIdRes;
        private Boolean success = false;
        public AsyncResponse delegate = null;


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

        protected String getToken() {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 2500;
            String token = LoginActivity.Token;
            try {
                URL url = new URL(AUTH_TOKEN_URL + sIdRes + "/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("DELETE");

                conn.setDoOutput(true);
                Log.d(L_TAG, "Set up data unrelated headers");


                //header crap
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("Authorization", token);
                // conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //Setup sen

                //

                //connect
                conn.connect();


                int serverResponseCode = conn.getResponseCode();
                // do something with response
                is = conn.getInputStream();

                // Convert the InputStream into a string

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
                if (is != null) {
                    is.close();
                }
                String serverResponseMessage = conn.getResponseMessage();
                //  int serverResponseCode = conn.getResponseCode();
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

}
