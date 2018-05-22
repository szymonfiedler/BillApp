package com.example.billard.billards.authorization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.billard.billards.R;

public class ForgottenPassword extends Activity {

    Context context;

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        TextView ln = findViewById(R.id.link_to_lg);
        Context context = getApplicationContext();
        ln.setOnClickListener(view -> {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }
}
