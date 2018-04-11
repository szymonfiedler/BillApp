package com.example.sara.billards;

import android.app.Activity;

import android.os.Bundle;
import android.widget.TextView;

public class Inf extends Activity {
    TextView tvinf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf);
        tvinf=(TextView) findViewById(R.id.tvinf);
    }
}
