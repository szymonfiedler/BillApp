package com.example.sara.billards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.android.volley.RequestQueue;
import com.example.sara.billards.booktable.DefaultBookedTablesRepository;
import com.example.sara.billards.tables.DefaultTablesRepository;


public class After_registration extends Activity {


    // private static final String TAG = "After_registration";
    private RequestQueue mQueue;


    Context context;
    Button bMap;
    int i, size;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        String url = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api2/";
        DefaultBookedTablesRepository.createSingletonInstance(mQueue, url);

        Intent intent = getIntent(); //intent from MainActivity
        int size = intent.getIntExtra("size", 1);
        LinearLayout scrViewButLay = (LinearLayout) findViewById(R.id.l1);
        scrViewButLay.setOrientation(LinearLayout.VERTICAL);
        for (int i = 1; i <= size; i++) {
            // Add Buttons
            final Button stol = new Button(this);
            stol.setId(i);
            stol.setBackgroundResource(R.drawable.button_shapes);
            stol.setTextColor(Color.WHITE);
            String nazwa = "Stół " + i + "\n" + "Ilość miejsc przy stole: " + DefaultTablesRepository.num_of_seats[i - 1] + "\n" + "Stół typu: " + DefaultTablesRepository.id_type[i - 1];
            stol.setText(nazwa);
            int finalI = i;
            stol.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    context = getApplicationContext();
                    int tab = finalI;
                    context = getApplicationContext();
                    Intent intent2 = new Intent(context, CalenderActivity.class);
                    intent2.putExtra("tableId", tab);//send date to CalenderActivity
                    startActivity(intent2);

                }
            });

            scrViewButLay.addView(stol);


        }

        bMap = (Button) findViewById(R.id.bmap);
        bMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Map.class);
                startActivity(intent);
            }
        });


    }
}
