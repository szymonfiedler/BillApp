package com.example.sara.billards;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends Activity  {
    EditText mEdit, mEdit2;
    Button mButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        Context context = getApplicationContext();
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        mEdit   = (EditText)findViewById(R.id.email);
        mEdit2   = (EditText)findViewById(R.id.pass);
        mButton = (Button)findViewById(R.id.btnLogin);
        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {

                        String content = mEdit.getText().toString();
                        boolean prawda= isEmailValid(content);
                        if (!prawda) {
                            Toast.makeText(context, "Email nieprawidłowy!",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{

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

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_info)
                                    .show();

                        }
                        String content2=mEdit2.getText().toString();
                    }
                });

        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), User_reg.class);
                startActivity(i);
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

