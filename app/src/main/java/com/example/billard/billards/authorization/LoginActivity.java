package com.example.billard.billards.authorization;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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


import com.android.volley.RequestQueue;
import com.example.billard.billards.JSONRequests.CustomVolleyRequestQueue;
import com.example.billard.billards.R;
import com.example.billard.billards.SplashActivity;
import com.example.billard.billards.tables.DefaultTablesRepository;
import com.example.billard.billards.usercenter.Home;
import com.example.billard.billards.common.SaveSharedPreference;

import org.json.JSONObject;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class LoginActivity extends Activity implements AsyncResponse {

    private static final String AUTH_TOKEN_URL = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/login/";
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String L_TAG = LoginActivity.class.getSimpleName();

    private static String Username;


    private UserLoginTask mAuthTask = null;


    View rect, icon;
    EditText username, password;
    CircularProgressButton bLogin;
    private RequestQueue mQueue;

    public void onBackPressed() {
        this.finishAffinity();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context context = getApplicationContext();
        TextView registerScreen = findViewById(R.id.link_to_register);
        TextView forgotScreen = findViewById(R.id.zapomniaem);


        username = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        bLogin = (CircularProgressButton) findViewById(R.id.btnLogin);
        password.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        bLogin.setOnClickListener(v -> {

            attemptLogin();
        });

        registerScreen.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserRegistatrion.class);
            startActivity(intent);

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        forgotScreen.setOnClickListener(v -> {
            Intent intent = new Intent(context, ForgottenPassword.class);
            startActivity(intent);

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    private void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }


        username.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        Username = username.getText().toString();
        String Password = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(Password) && !isPasswordValid(Password)) {
            password.setError(getString(R.string.error_invalid_password));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle2);
            icon = findViewById(R.id.password_lo);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            password.startAnimation(shake);
            focusView = password;
            cancel = true;
        }
        if (TextUtils.isEmpty(Password)) {
            password.setError(getString(R.string.error_field_required));
            Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            password.startAnimation(shake);
            rect = findViewById(R.id.rectangle2);
            icon = findViewById(R.id.password_lo);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(Username)) {
            username.setError(getString(R.string.error_field_required));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle);
            icon = findViewById(R.id.profile);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            username.startAnimation(shake);
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
        bLogin.doneLoadingAnimation(Color.parseColor("#7990A7"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
        SaveSharedPreference.setUserName(LoginActivity.this, Username);
        SaveSharedPreference.setUserToken(LoginActivity.this, token);
        SplashActivity.tkn=token;
        showDialog(SaveSharedPreference.getUserToken(LoginActivity.this), "Logowanie", "Zalogowano pomyślnie");

    }

    public void showDialog(String token, String mas1, String mas2) {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ok);
        Button dialog_btn = dialog.findViewById(R.id.ok2);
        TextView title = dialog.findViewById(R.id.text1);
        TextView text = dialog.findViewById(R.id.text2);
        title.setText(mas1);
        text.setText(mas2);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        mQueue = CustomVolleyRequestQueue.getInstance(LoginActivity.this)
                .getRequestQueue();


        String url = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api4/";
        DefaultTablesRepository.createSingletonInstance(mQueue, url);
        DefaultTablesRepository.getInstance().getTables(
                Tables -> {
                    SaveSharedPreference.size = Tables.size();
                    SaveSharedPreference.dane = (Tables.toString());
                    dialog.show();
                },
                error -> {
                });


        dialog_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtra(EXTRA_MESSAGE, token);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        dialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra(EXTRA_MESSAGE, token);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                dialog.dismiss();
            }
            return true;
        });

    }


    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }


    @SuppressLint("StaticFieldLeak")
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String sUserName;
        private final String sPassWord;
        private Boolean success = false;
        AsyncResponse delegate;


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
                String token = JSONfunction.parseAuthTokenAndUserId(response);
                SaveSharedPreference.setUserID(LoginActivity.this, JSONfunction.user_id);
                if (token.length() > 2) {
                    this.delegate.processFinish(token);
                }

            } else {
                Log.d(L_TAG, response);

                password.setError(getString(R.string.error_incorrect_password));
                bLogin.revertAnimation();
                bLogin.setBackgroundResource(R.drawable.button);
                Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                password.startAnimation(shake);
                rect = findViewById(R.id.rectangle2);
                icon = findViewById(R.id.password_lo);
                icon.startAnimation(shake);
                rect.startAnimation(shake);
                password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }
}


