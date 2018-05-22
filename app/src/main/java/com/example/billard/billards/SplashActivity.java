package com.example.billard.billards;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.example.billard.billards.JSONRequests.CustomVolleyRequestQueue;
import com.example.billard.billards.authorization.LoginActivity;
import com.example.billard.billards.common.SaveSharedPreference;
import com.example.billard.billards.tables.DefaultTablesRepository;
import com.example.billard.billards.usercenter.Home;


public class SplashActivity extends AppCompatActivity {

    Context context;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SaveSharedPreference.getUserID(SplashActivity.this) != 0) {

            mQueue = CustomVolleyRequestQueue.getInstance(SplashActivity.this)
                    .getRequestQueue();


            String url = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api4/";
            DefaultTablesRepository.createSingletonInstance(mQueue, url);
            DefaultTablesRepository.getInstance().getTables(
                    Tables -> {
                        SaveSharedPreference.size = Tables.size();
                        SaveSharedPreference.dane = (Tables.toString());
                        Intent intent = new Intent(SplashActivity.this, Home.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    },
                    error -> {
                    });

        } else {
            context = getApplicationContext();
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }


    }
}
