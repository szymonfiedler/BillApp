package com.example.billard.billards.authorization;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
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

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class UserRegistatrion extends Activity implements AsyncResponse {


    private static final String AUTH_TOKEN_URL = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/register/";
    private static final String SUCCESS_MESSAGE = "Successful result";
    private static final String FAILURES_MESSAGE = "Something went wrong";
    private static final String L_TAG = LoginActivity.class.getSimpleName();

    private UserRegisterTask mAuthTask = null;

    private EditText Username, Email, Password, Password2, Name, Surname;
    CircularProgressButton bRegister;
    View rect, icon;


    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);


        Context context = getApplicationContext();
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        Username = (EditText) findViewById(R.id.username);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Password2 = (EditText) findViewById(R.id.password4);
        Name = (EditText) findViewById(R.id.name);
        Surname = (EditText) findViewById(R.id.surname);
        bRegister = (CircularProgressButton) findViewById(R.id.btnRegister);


        Password.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                startRegister();
                return true;
            }
            return false;
        });

        loginScreen.setOnClickListener(arg0 -> {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        bRegister.setOnClickListener(view -> startRegister());


    }

    private void startRegister() {

        boolean cancel = false;
        View focusView = null;

        if (mAuthTask != null) {
            return;
        }


        Username.setError(null);
        Password.setError(null);

        // Store values at the time of the login attempt.
        String username = Username.getText().toString();
        String password = Password.getText().toString();
        String email = Email.getText().toString();
        String name = Name.getText().toString();
        String surname = Surname.getText().toString();
        String password2 = Password2.getText().toString();


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            Password.setError(getString(R.string.error_invalid_password));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle5);
            icon = findViewById(R.id.password2);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            Password.startAnimation(shake);
            focusView = Password;
            cancel = true;
        }

        boolean prawda = isEmailValid(email);
        if (!prawda) {
            Email.setError(getString(R.string.error_invalid_email));
            Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            rect = findViewById(R.id.rectangle4);
            icon = findViewById(R.id.emailic);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            Email.startAnimation(shake);
            focusView = Email;
            cancel = true;
        }

        if (TextUtils.isEmpty(password2)) {
            Password2.setError(getString(R.string.error_invalid_password));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle6);
            icon = findViewById(R.id.password3);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            Password2.startAnimation(shake);
            focusView = Password2;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            Password.setError(getString(R.string.error_invalid_password));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle5);
            icon = findViewById(R.id.password2);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            Password.startAnimation(shake);
            focusView = Password;
            cancel = true;
        }
        if (!password.equals(password2)) {
            Password.setError("Hasła się nie zgadzają");
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle5);
            icon = findViewById(R.id.password2);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            rect = findViewById(R.id.rectangle6);
            icon = findViewById(R.id.password3);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            Password.startAnimation(shake);
            Password2.startAnimation(shake);
            focusView = Password;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            Username.setError(getString(R.string.error_field_required));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle);
            icon = findViewById(R.id.profile);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            Username.startAnimation(shake);
            focusView = Username;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            Email.setError(getString(R.string.error_field_required));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle4);
            icon = findViewById(R.id.emailic);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            Email.startAnimation(shake);
            focusView = Email;
            cancel = true;
        }
        if (TextUtils.isEmpty(name)) {
            Name.setError(getString(R.string.error_field_required));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle2);
            icon = findViewById(R.id.profile2);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            Name.startAnimation(shake);
            focusView = Name;
            cancel = true;
        }
        if (TextUtils.isEmpty(surname)) {
            Surname.setError(getString(R.string.error_field_required));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle3);
            icon = findViewById(R.id.profile3);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            Surname.startAnimation(shake);
            focusView = Surname;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            bRegister.startAnimation();
            mAuthTask = new UserRegisterTask(username, email, password, name, surname, this);
            mAuthTask.execute((Void) null);
        }
    }

    @Override
    public void processFinish(String response) {
        if (response.equals(SUCCESS_MESSAGE)) {
            bRegister.doneLoadingAnimation(Color.parseColor("#7990A7"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
            showDialog(1, "Rejestracja", "Zarejestrowano pomyślnie!");
        } else {
            bRegister.revertAnimation();
            bRegister.setBackgroundResource(R.drawable.button);
            showDialog(0, "Rejestracja", "Wystąpił błąd!");
        }
    }


    public void showDialog(int select, String mas1, String mas2) {
        final Dialog dialog = new Dialog(UserRegistatrion.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (select == 1) {
            dialog.setContentView(R.layout.dialog_ok);
        } else {
            dialog.setContentView(R.layout.dialog_not_ok);
        }
        Button dialog_btn = (Button) dialog.findViewById(R.id.ok2);
        TextView title = (TextView) dialog.findViewById(R.id.text1);
        TextView text = (TextView) dialog.findViewById(R.id.text2);
        title.setText(mas1);
        text.setText(mas2);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        dialog.show();

        dialog_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        dialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                dialog.dismiss();
            }
            return true;
        });

    }

    private boolean isPasswordValid(String password) {

        return password.length() > 8;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @SuppressLint("StaticFieldLeak")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)


    public class UserRegisterTask extends AsyncTask<Void, Void, String> {
        private final String sUserName;
        private final String sEmail;
        private final String sName;
        private final String sSurname;
        private final String sPassWord;
        private Boolean success = false;
        AsyncResponse delegate = null;


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

            try {
                return getToken(this.sUserName, this.sEmail, this.sPassWord, this.sName, this.sSurname);

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        String getToken(String username, String email, String password, String name, String surname) {
            JSONObject login = JSONfunction.Register(username, email, password, name, surname);
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


        String readIt(InputStream stream, int len) throws IOException {
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