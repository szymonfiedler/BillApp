package com.example.sara.billards;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaCodec;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User_reg extends Activity {
    EditText mEdit, mEdit2,mEdit3;
    Button mButton;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        Context context = getApplicationContext();
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        mEdit   = (EditText)findViewById(R.id.reg_fullname);
        mEdit2   = (EditText)findViewById(R.id.reg_email);
        mEdit3   = (EditText)findViewById(R.id.reg_password);
        mButton = (Button)findViewById(R.id.btnRegister);
        mButton.setOnClickListener(
                (View view) -> {

                    String content = mEdit2.getText().toString();
                    boolean prawda= isEmailValid(content);
                    if (!prawda) {
                        Toast.makeText(context, "Email nieprawidłowy!",
                                Toast.LENGTH_LONG).show();
                    }
                    else{

                        AlertDialog.Builder builder;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(User_reg.this, android.R.style.Theme_Material_Dialog_Alert);
                        } else builder = new AlertDialog.Builder(User_reg.this);
                        builder.setTitle("Rejestracja")
                                .setMessage("Zarejestrowano pomyślnie!")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();

                    }
                    String content2=mEdit2.getText().toString();
                    String content3=mEdit3.getText().toString();
                    //wysyła do bazy
                });


        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                finish();
            }
        });
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}