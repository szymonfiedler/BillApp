package com.example.billard.billards.usercenter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.billard.billards.R;
import com.example.billard.billards.authorization.AsyncResponse;
import com.example.billard.billards.authorization.Change_password;
import com.example.billard.billards.authorization.JSONfunction;
import com.example.billard.billards.authorization.LoginActivity;
import com.example.billard.billards.authorization.UserRegistatrion;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class UserProfile extends Activity implements AsyncResponse {


    private Button back;
    private TextView username;
    private EditText uname, usurname;
    private UserChangeTask mChangeTask = null;
    View rect, icon;
    CircularProgressButton bchange;
    private static final String L_TAG = UserProfile.class.getSimpleName();
    private static final String SUCCESS_MESSAGE = "Successful result";
    private static final String FAILURES_MESSAGE = "Something went wrong";


    public void onBackPressed() {
        Intent intent = new Intent(UserProfile.this, Home.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        back = findViewById(R.id.arrow);
        username = findViewById(R.id.uz);
        uname = findViewById(R.id.uname);
        usurname = findViewById(R.id.usurname);

        String bla = SaveSharedPreference.getUserToken(UserProfile.this);
        username.setText("Użytkownik " + SaveSharedPreference.getUserName(getApplicationContext()));
        uname.setText(JSONfunction.UNAME);
        usurname.setText(JSONfunction.USURNAME);
        bchange = findViewById(R.id.bchange);


        back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        bchange.setOnClickListener(view -> {
            startChange();
        });

    }

    private void startChange() {

        boolean cancel = false;
        View focusView = null;

        if (mChangeTask != null) {
            return;
        }


        uname.setError(null);
        usurname.setError(null);

        // Store values at the time of the login attempt.


        String name = uname.getText().toString();
        String surname = usurname.getText().toString();


        if (TextUtils.isEmpty(name)) {
            uname.setError(getString(R.string.error_field_required));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle6);
            icon = findViewById(R.id.password3);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            uname.startAnimation(shake);
            focusView = uname;
            cancel = true;
        }
        if (TextUtils.isEmpty(surname)) {
            usurname.setError(getString(R.string.error_field_required));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle5);
            icon = findViewById(R.id.password2);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            usurname.startAnimation(shake);
            focusView = usurname;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            bchange.startAnimation();
            mChangeTask = new UserChangeTask(name, surname, this);
            mChangeTask.execute((Void) null);
        }
    }

    @Override
    public void processFinish(String response) {
        if (response.equals(SUCCESS_MESSAGE)) {
            bchange.doneLoadingAnimation(Color.parseColor("#7990A7"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
            showDialog(1, "   Zmiana danych", "Zmieniono dane pomyślnie!");
        } else {
            bchange.revertAnimation();
            bchange.setBackgroundResource(R.drawable.button);
            showDialog(0, "Zmiana danych", "Wystąpił błąd!");
        }
    }


    public void showDialog(int select, String mas1, String mas2) {
        final Dialog dialog = new Dialog(UserProfile.this);
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
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        dialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                dialog.dismiss();
            }
            return true;
        });

    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @SuppressLint("StaticFieldLeak")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)


    public class UserChangeTask extends AsyncTask<Void, Void, String> {

        private final String sName;
        private final String sSurname;

        private Boolean success = false;
        AsyncResponse delegate;


        UserChangeTask(String sName, String sSurname, AsyncResponse delegate) {
            this.sName = sName;
            this.sSurname = sSurname;
            this.delegate = delegate;
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                return getToken(this.sName, this.sSurname);

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        String getToken(String name, String surname) {
            JSONObject login = JSONfunction.Change(name, surname);
            assert login != null;
            String message = login.toString();
            InputStream is;
            int len = 500;


            try {
                URL url = new URL("http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/change_name/");
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
                conn.setRequestProperty("Authorization", SaveSharedPreference.getUserToken(UserProfile.this));
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
                if (serverResponseCode == 200) {
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
            mChangeTask = null;

            if (this.success) {
                delegate.processFinish(SUCCESS_MESSAGE);
            } else {
                Log.d(L_TAG, response);
                delegate.processFinish(FAILURES_MESSAGE);
            }
        }

        @Override
        protected void onCancelled() {
            mChangeTask = null;

        }
    }


}
