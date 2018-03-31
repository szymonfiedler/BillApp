package com.example.sara.billards;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.sara.billards.booktable.BookTableRequest;
import com.example.sara.billards.booktable.BookedTableResponseHandler;
import com.example.sara.billards.booktable.DefaultTableBookingService;
import com.example.sara.billards.booktable.TableBookedResponse;

public class HoursActivity extends AppCompatActivity {
TextView tvHour1, tvHour2,tvHour3,tvHour4,tvHour5,tvHour6,tvHour7,tvHour8,tvHour9,tvHour10,tvHour11,tvHour12;
    Button bHour1,bHour2,bHour3,bHour4,bHour5,bHour6,bHour7,bHour8,bHour9,bHour10,bHour11,bHour12;
    private static final String TAG = "HoursActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);

        tvHour1 = (TextView) findViewById(R.id.tvHour1);
        tvHour2 = (TextView) findViewById(R.id.tvHour2);
        tvHour3 = (TextView) findViewById(R.id.tvHour3);
        tvHour4 = (TextView) findViewById(R.id.tvHour4);
        tvHour5 = (TextView) findViewById(R.id.tvHour5);
        tvHour6 = (TextView) findViewById(R.id.tvHour6);
        tvHour7 = (TextView) findViewById(R.id.tvHour7);
        tvHour8 = (TextView) findViewById(R.id.tvHour8);
        tvHour9 = (TextView) findViewById(R.id.tvHour9);
        tvHour10 = (TextView) findViewById(R.id.tvHour10);
        tvHour11 = (TextView) findViewById(R.id.tvHour11);
        tvHour12 = (TextView) findViewById(R.id.tvHour12);

        bHour1= (Button) findViewById(R.id.bHour1);
        bHour2= (Button) findViewById(R.id.bHour2);
        bHour3= (Button) findViewById(R.id.bHour3);
        bHour4= (Button) findViewById(R.id.bHour4);
        bHour5= (Button) findViewById(R.id.bHour5);
        bHour6= (Button) findViewById(R.id.bHour6);
        bHour7= (Button) findViewById(R.id.bHour7);
        bHour8= (Button) findViewById(R.id.bHour8);
        bHour9= (Button) findViewById(R.id.bHour9);
        bHour10= (Button) findViewById(R.id.bHour10);
        bHour11= (Button) findViewById(R.id.bHour11);
        bHour12= (Button) findViewById(R.id.bHour12);

        bHour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               takeHour();
            }
        });
        Intent intent =getIntent(); // code to get data from previous activity
        String date= intent.getStringExtra("getDate()"); // storing the data in variable e1

        Log.e(TAG, "date from HoursActivity class " + date);

    }
    public String takeHour() {
        bHour1.setBackgroundColor(Color.RED);
        String hour=tvHour1.getText().toString();
        Toast.makeText(getApplicationContext(), "click " + hour, Toast.LENGTH_SHORT).show();
        return hour;
    }


}
