package com.example.sara.billards.registration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sara.billards.MainActivity;
import com.example.sara.billards.R;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Changepassword extends BaseActivity implements AsyncResponse {

    private static final String AUTH_TOKEN_URL = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/login/";
    private static final String AUTH_TOKEN_URL2 = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/change_password/";
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    private UserLoginTask mAuthTask = null;
    private PasswordChangeTask mChangetask = null;
    public String tkn;
    private static final String L_TAG = "Changepassword";
    EditText username, password, pasnew;
    Button bLogin;
    public static String Username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        Context context = getApplicationContext();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
        pasnew = (EditText) findViewById(R.id.passnew);
        bLogin = (Button) findViewById(R.id.btnchange);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        bLogin.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {


                        attemptLogin();


                    }
                });


    }

    private void attemptLogin() {
        // If user is not very patient
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        username.setError(null);
        password.setError(null);
        pasnew.setError(null);
        // Store values at the time of the login attempt.
        Username = username.getText().toString();
        String Password = password.getText().toString();
        String Pasnew = pasnew.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(Password) && !isPasswordValid(Password)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }
        if (!TextUtils.isEmpty(Pasnew) && !isPasswordValid(Pasnew)) {
            pasnew.setError(getString(R.string.error_invalid_password));
            focusView = pasnew;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(Username)) {
            username.setError(getString(R.string.error_field_required));
            focusView = username;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            ;
            mAuthTask = new UserLoginTask(Username, Password, this);
            mAuthTask.execute((Void) null);
        }
    }

    @Override
    public void processFinish(String token) {

        String Pasnew = pasnew.getText().toString();
        tkn = token;
        mChangetask = new PasswordChangeTask(Pasnew, this);
        mChangetask.execute((Void) null);

    }

    public void processFinish2(String token) {

        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(Changepassword.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(Changepassword.this);
        }

        builder.setTitle("Hasło")
                .setMessage("Zmieniono hasło")
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

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String sUserName;
        private final String sPassWord;
        private Boolean success = false;
        public AsyncResponse delegate = null;


        UserLoginTask(String sUserName, String sPassword, AsyncResponse delegate) {
            this.sUserName = sUserName;
            this.sPassWord = sPassword;
            this.delegate = delegate;
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                return getToken(this.sUserName, this.sPassWord);

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        protected String getToken(String username, String password) {
            JSONfunction parser = new JSONfunction();
            JSONObject login = JSONfunction.getLoginObject2(username, password);
            String message = login.toString();
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;
            try {
                URL url = new URL(AUTH_TOKEN_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Log.d(L_TAG, "Set up data unrelated headers");
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //header crap
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                // conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //Setup sen
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //
                os.flush();
                //connect
                conn.connect();


                Log.d(L_TAG, "data is sent");


                // do something with response
                is = conn.getInputStream();
                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
                if (is != null) {
                    is.close();
                }
                String serverResponseMessage = conn.getResponseMessage();
                int serverResponseCode = conn.getResponseCode();
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


        public String readIt(InputStream stream, int len) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }


        @Override
        protected void onPostExecute(String response) {
            mAuthTask = null;

            if (this.success) {
                String token = JSONfunction.parseAuthToken2(response);
                if (token.length() > 2) {
                    this.delegate.processFinish(token);
                }

            } else {
                Log.d(L_TAG, response);
                password.setError(getString(R.string.error_incorrect_password));
                password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }

    public class PasswordChangeTask extends AsyncTask<Void, Void, String> {

        private final String sPassWordNew;
        private Boolean success = false;
        public AsyncResponse delegate = null;


        PasswordChangeTask(String sPasswordNew, AsyncResponse delegate) {
            this.sPassWordNew = sPasswordNew;
            this.delegate = delegate;
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                return getToken(this.sPassWordNew);

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        protected String getToken(String passwordNew) {
            JSONfunction parser = new JSONfunction();
            JSONObject login = JSONfunction.changepassword(passwordNew);
            String message = login.toString();
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;
            try {
                URL url = new URL(AUTH_TOKEN_URL2);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Log.d(L_TAG, "Set up data unrelated headers");
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //header crap
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                // conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                conn.setRequestProperty("Authorization", "Token " + tkn);
                //Setup sen
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //
                os.flush();
                //connect
                conn.connect();


                Log.d(L_TAG, "data is sent");


                // do something with response
                is = conn.getInputStream();
                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
                if (is != null) {
                    is.close();
                }
                String serverResponseMessage = conn.getResponseMessage();
                int serverResponseCode = conn.getResponseCode();
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

        public String readIt(InputStream stream, int len) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }


        @Override
        protected void onPostExecute(String response) {
            mChangetask = null;

            if (this.success) {
                String token = JSONfunction.parseResposne(response);
                if (token.equals("Detail Password has been saved.")) {
                    processFinish2(token);
                }

            } else {
                Log.d(L_TAG, response);
                password.setError(getString(R.string.error_field_required));
                password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mChangetask = null;

        }
    }
}


