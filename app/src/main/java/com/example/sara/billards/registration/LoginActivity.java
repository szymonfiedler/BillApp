package com.example.sara.billards.registration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sara.billards.MainActivity;
import com.example.sara.billards.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity  {
    EditText username, password;
    Button bLogin;
    public static int logged = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        Context context = getApplicationContext();
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
        bLogin = (Button) findViewById(R.id.btnLogin);
        bLogin.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {


                            logged = 4;
                            AlertDialog.Builder builder;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(LoginActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(LoginActivity.this);
                            }

                            builder.setTitle("Logowanie")
                                    .setMessage("Zalogowano pomyślnie")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent intent = new Intent(context, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_info)
                                    .show();

                        String Username = username.getText().toString();
                        String Password = password.getText().toString();
                    }
                });

        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), User_reg.class);
                startActivity(i);
            }
        });
    }

}


