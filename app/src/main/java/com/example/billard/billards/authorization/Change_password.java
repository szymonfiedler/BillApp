package com.example.billard.billards.authorization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.billard.billards.R;
import com.example.billard.billards.common.SaveSharedPreference;
import com.example.billard.billards.usercenter.Home;

import org.json.JSONObject;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class Change_password extends Activity implements AsyncResponse {

    private static final String AUTH_TOKEN_URL_LOGIN = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/login/";
    private static final String AUTH_TOKEN_URL_CHANGE_PASSWORD = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/change_password/";
    private static final String L_TAG = "Change_password";
    public final static String EXTRA_MESSAGE = "Token";


    private UserLoginTask mAuthTask = null;
    private PasswordChangeTask mChangetask = null;
    private static String Username;
    Button back;

    EditText username, password, pasnew;
    CircularProgressButton bLogin;

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
        pasnew = (EditText) findViewById(R.id.passnew);
        bLogin = (CircularProgressButton) findViewById(R.id.btnchange);

        back = (Button) findViewById(R.id.arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        password.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });


        bLogin.setOnClickListener(
                view -> attemptLogin());

    }


    private void attemptLogin() {

        boolean cancel = false;
        View focusView = null;

        if (mAuthTask != null) {
            return;
        }


        username.setError(null);
        password.setError(null);
        pasnew.setError(null);


        // Store values at the time of the login attempt.
        Username = username.getText().toString();
        String Password = password.getText().toString();
        String Pasnew = pasnew.getText().toString();


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(Password) && isPasswordValid(Password)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }
        if (!TextUtils.isEmpty(Pasnew) && isPasswordValid(Pasnew)) {
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
            bLogin.startAnimation();
            mAuthTask = new UserLoginTask(Username, Password, this);
            mAuthTask.execute((Void) null);
        }
    }

    @Override
    public void processFinish(String token) {

        String Pasnew = pasnew.getText().toString();
        mChangetask = new PasswordChangeTask(Pasnew, this);
        mChangetask.execute((Void) null);

    }

    public void processFinish2(String token) {
        bLogin.doneLoadingAnimation(Color.parseColor("#7990A7"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
        showDialog(token, "Hasło", "Zmieniono hasło!");
    }


    public void showDialog(String token, String mas1, String mas2) {
        final Dialog dialog = new Dialog(Change_password.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ok);
        Button dialog_btn = (Button) dialog.findViewById(R.id.ok2);
        TextView title = (TextView) dialog.findViewById(R.id.text1);
        TextView text = (TextView) dialog.findViewById(R.id.text2);
        title.setText(mas1);
        text.setText(mas2);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        dialog.show();

        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra(EXTRA_MESSAGE, token);

                startActivity(intent);
                finish();
            }
        });

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtra(EXTRA_MESSAGE, token);

                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
                return true;
            }
        });
    }

    private boolean isPasswordValid(String password) {
        return password.length() <= 8;
    }


    @SuppressLint("StaticFieldLeak")
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String sUserName;
        private final String sPassWord;
        private Boolean success = false;
        AsyncResponse delegate = null;


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

        String getToken(String username, String password) {
            JSONObject login = JSONfunction.getLoginObject(username, password);
            assert login != null;
            String message = login.toString();
            InputStream is;
            int len = 500;


            try {
                URL url = new URL(AUTH_TOKEN_URL_LOGIN);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Log.d(L_TAG, "Set up data unrelated headers");
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //header
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");


                //Setup sen
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
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


        String readIt(InputStream stream, int len) throws IOException {
            Reader reader;
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
                bLogin.revertAnimation();
                bLogin.setBackgroundResource(R.drawable.button);
                Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                password.startAnimation(shake);
                View rect = findViewById(R.id.rectangle2);
                View icon = findViewById(R.id.password2);
                icon.startAnimation(shake);
                rect.startAnimation(shake);
                password.setError(getString(R.string.error_incorrect_password));
                password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }

    @SuppressLint("StaticFieldLeak")
    public class PasswordChangeTask extends AsyncTask<Void, Void, String> {

        private final String sPassWordNew;
        private Boolean success = false;
        AsyncResponse delegate;


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

        String getToken(String passwordNew) {
            JSONObject login = JSONfunction.change_password(passwordNew);
            assert login != null;
            String message = login.toString();
            InputStream is;
            int len = 500;
            try {
                URL url = new URL(AUTH_TOKEN_URL_CHANGE_PASSWORD);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Log.d(L_TAG, "Set up data unrelated headers");
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //header
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("Authorization", SaveSharedPreference.getUserToken(Change_password.this));

                //Setup sen
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
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

        String readIt(InputStream stream, int len) throws IOException {
            Reader reader;
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


