package com.example.sara.billards.registration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User_reg extends BaseActivity implements AsyncResponse {


    private static final String AUTH_TOKEN_URL = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/register/";
    private static final String SUCCESS_MESSAGE = "Successful result";
    private static final String FAILURES_MESSAGE = "Something went wrong";
    private static final String L_TAG = LoginActivity.class.getSimpleName();

    private EditText Username, Email, Password, Name, Surname;
    private TextView tvTest;
    Button bRegister;
    private UserRegisterTask mAuthTask = null;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        Context context = getApplicationContext();
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        Username = (EditText) findViewById(R.id.username);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Name = (EditText) findViewById(R.id.name);
        Surname = (EditText) findViewById(R.id.surname);
        bRegister = (Button) findViewById(R.id.btnRegister);

        Password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    startRegister();
                    return true;
                }
                return false;
            }
        });
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString();
                boolean prawda = isEmailValid(email);
                if (!prawda) {
                    Toast.makeText(context, "Email nieprawidłowy!",
                            Toast.LENGTH_LONG).show();
                } else {
                    startRegister();
                }
            }
        });

        tvTest = (TextView) findViewById(R.id.returned_token);
    }

    private void startRegister() {
        // If user is not very patient
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        Username.setError(null);
        Password.setError(null);

        // Store values at the time of the login attempt.
        String username = Username.getText().toString();
        String password = Password.getText().toString();
        String email = Email.getText().toString();
        String name = Name.getText().toString();
        String surname = Surname.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            Password.setError(getString(R.string.error_invalid_password));
            focusView = Password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            Username.setError(getString(R.string.error_field_required));
            focusView = Username;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            Email.setError(getString(R.string.error_field_required));
            focusView = Email;
            cancel = true;
        }
        if (TextUtils.isEmpty(name)) {
            Name.setError(getString(R.string.error_field_required));
            focusView = Name;
            cancel = true;
        }
        if (TextUtils.isEmpty(surname)) {
            Surname.setError(getString(R.string.error_field_required));
            focusView = Surname;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //  and kick off a background task to
            // perform the user login attempt.

            mAuthTask = new UserRegisterTask(username, email, password, name, surname, this);
            mAuthTask.execute((Void) null);
        }
    }

    @Override
    public void processFinish(String response) {
        if (response == SUCCESS_MESSAGE) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        } else {
            tvTest.setText(FAILURES_MESSAGE);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, String> {
        private final String sUserName;
        private final String sEmail;
        private final String sName;
        private final String sSurname;
        private final String sPassWord;
        private Boolean success = false;
        public AsyncResponse delegate = null;


        UserRegisterTask(String sUserName, String sEmail, String sPassword, String sName, String sSurname, AsyncResponse delegate) {
            this.sUserName = sUserName;
            this.sEmail = sEmail;
            this.sPassWord = sPassword;
            this.sName = sName;
            this.sSurname = sSurname;
            this.delegate = delegate;
        }


        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                return getToken(this.sUserName, this.sEmail, this.sPassWord, this.sName, this.sSurname);

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        protected String getToken(String username, String email, String password, String name, String surname) {
            JSONfunction parser = new JSONfunction();
            JSONObject login = parser.getLoginObject(username, email, password, name, surname);
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
                os.close();
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
                if (serverResponseCode == 201) {
                    this.success = true;

                } else {
                    Log.d(L_TAG, serverResponseMessage + " " + serverResponseCode);
                }

                Log.d(L_TAG, contentAsString);
                return contentAsString;

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e(L_TAG, "Exception");
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
                delegate.processFinish(SUCCESS_MESSAGE);
            } else {
                Log.d(L_TAG, response);
                delegate.processFinish(FAILURES_MESSAGE);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}