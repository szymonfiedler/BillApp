package com.example.sara.billards;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class User_reg extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                finish();
            }
        });
    }
}
