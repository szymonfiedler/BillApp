package com.example.billard.billards.authorization;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.billard.billards.JSONRequests.CustomVolleyRequestQueue;
import com.example.billard.billards.R;
import com.example.billard.billards.common.SaveSharedPreference;
import com.example.billard.billards.tables.DefaultTablesRepository;
import com.example.billard.billards.usercenter.Home;
import com.example.billard.billards.usercenter.UserProfile;
import com.example.billard.billards.usercenter.UserReservations;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static com.example.billard.billards.authorization.JSONfunction.getRes;

public class ForgottenPassword extends Activity {


    private static final String L_TAG = ForgottenPassword.class.getSimpleName();
    private SendEmail mSen = null;
    CircularProgressButton btn;
    public EditText user2;
    View rect, icon;
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        TextView ln = findViewById(R.id.link_to_lg);
        user2=findViewById(R.id.user);
        Context context = getApplicationContext();
        ln.setOnClickListener(view -> {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        btn = findViewById(R.id.btnSend);
        btn.setOnClickListener(view -> {
            attemptsendemail();
        });
    }

    private void attemptsendemail() {

        boolean cancel = false;
        View focusView = null;

        if (mSen!= null) {
            return;
        }


        user2.setError(null);

        // Store values at the time of the login attempt.



        String username2 = user2.getText().toString();


        if (TextUtils.isEmpty(username2)) {
            user2.setError(getString(R.string.error_field_required));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rect = findViewById(R.id.rectangle6);
            icon = findViewById(R.id.password3);
            icon.startAnimation(shake);
            rect.startAnimation(shake);
            btn.startAnimation(shake);
            focusView = user2;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            btn.startAnimation();
            mSen = new SendEmail(username2,this);
            mSen.execute((Void) null);
        }
    }



    public void processFinish() {

        btn.doneLoadingAnimation(Color.parseColor("#7990A7"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
            showDialogg("Zmiana hasła","Wysłano nowe hasło na poczte email");


    }

    public void showDialogg( String mas1, String mas2) {
        final Dialog dialog = new Dialog(ForgottenPassword.this);
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
    public class SendEmail extends AsyncTask<Void, Void, String> {

        private final String username;
        private Boolean success = false;
        public ForgottenPassword delegate = null;


        SendEmail(String username,ForgottenPassword delegate) {
            this.delegate = delegate;
            this.username=username;
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                return sendem(this.username);

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        String sendem(String username) {
            JSONObject login = JSONfunction.Send(username);
            assert login != null;
            String message = login.toString();
            InputStream is;



            try {
                URL url = new URL("http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/forget_password/");
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
                String contentAsString = readIt(is);

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


        public String readIt(InputStream stream) throws IOException {
            int len;
            byte[] buffer = new byte[10000];
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            while ((len = stream.read(buffer)) != -1) {
                byteArray.write(buffer, 0, len);
            }
            return new String(byteArray.toByteArray());

        }


        @Override
        protected void onPostExecute(String response) {
            mSen = null;

            if (this.success) {

                this.delegate.processFinish();


            } else {
                Log.d(L_TAG, response);
            }
        }

        @Override
        protected void onCancelled() {
            mSen = null;

        }
    }

}