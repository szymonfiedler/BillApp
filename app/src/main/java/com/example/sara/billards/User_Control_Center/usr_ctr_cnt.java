package com.example.sara.billards.User_Control_Center;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sara.billards.MainActivity;
import com.example.sara.billards.R;
import com.example.sara.billards.registration.AsyncResponse;
import com.example.sara.billards.registration.Changepassword;

import com.example.sara.billards.registration.LoginActivity;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.sara.billards.registration.JSONfunction.getRes;

public class usr_ctr_cnt extends Activity implements AsyncResponse {

    private Button blog, brezerv, bpassch;
    Context context;
    private static final String AUTH_TOKEN_URL = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/user_history/";
    private final String USER_AGENT = "Mozilla/5.0";
    private static final String L_TAG = usr_ctr_cnt.class.getSimpleName();
    private ReservationTask mRes = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_control_center);
        TextView text = (TextView) findViewById(R.id.textView2);
        text.setText(" " + LoginActivity.Username + "!");
        blog = (Button) findViewById(R.id.blog);
        brezerv = (Button) findViewById(R.id.brezerv);
        bpassch = (Button) findViewById(R.id.bpassch);
        bpassch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Changepassword.class);
                startActivity(intent);
            }
        });
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.user_id = 0;
                LoginActivity.Username = "";
                context = getApplicationContext();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
        brezerv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptgetres();

            }
        });
    }


    private void attemptgetres() {
        mRes = new ReservationTask(this);
        mRes.execute((Void) null);
    }

    public void processFinish(String token) {

        try {
            ArrayList<String> a = getRes(token);
            context = getApplicationContext();
            Intent intent = new Intent(context, user_reservations.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class ReservationTask extends AsyncTask<Void, Void, String> {


        private Boolean success = false;
        public AsyncResponse delegate = null;


        ReservationTask(AsyncResponse delegate) {
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

            String token = LoginActivity.Token;
            try {
                URL url = new URL(AUTH_TOKEN_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");

                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(false);
                Log.d(L_TAG, "Set up data unrelated headers");

                conn.setRequestProperty("User-Agent", USER_AGENT);
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
                String contentAsString = readIt(is);

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
                if (is != null) {
                    is.close();
                }
                String serverResponseMessage = conn.getResponseMessage();
                //  int serverResponseCode = conn.getResponseCode();
                if (serverResponseCode == 200) {
                    this.success = true;
                } else {
                    Log.d(L_TAG, serverResponseMessage + " " + serverResponseCode);
                }

                Log.d(L_TAG, contentAsString);
                return contentAsString;

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e(L_TAG, "Exeption");
                return "";
            }


        }


        public String readIt(InputStream stream) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");

            char[] buffer = new char[100000];
            int bytesRead = reader.read(buffer, 0, buffer.length);

            return new String(buffer, 0, bytesRead);
        }


        @Override
        protected void onPostExecute(String response) {
            mRes = null;

            if (this.success) {
                String respon = response;
                if (respon.length() > 2) {
                    this.delegate.processFinish(respon);
                }

            } else {
                Log.d(L_TAG, response);
            }
        }

        @Override
        protected void onCancelled() {
            mRes = null;

        }

    }
}
