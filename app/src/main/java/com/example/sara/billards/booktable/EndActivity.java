package com.example.sara.billards.booktable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sara.billards.R;

public class EndActivity extends AppCompatActivity {
    private static final String TAG = "EndActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent =getIntent(); //pobranie intentu z HoursActivity
        String date= intent.getStringExtra("DATE");

        int tableId= intent.getIntExtra("tableId",1);


        Log.e(TAG, "tableId  " + tableId);
        Log.e(TAG, "date from HoursActivity class " + date);









    }
}
