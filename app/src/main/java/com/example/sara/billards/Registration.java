package com.example.sara.billards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.sara.billards.booktable.DefaultBookedTablesRepository;

import org.json.JSONArray;

public class Registration extends Activity {
    private TextView mTextView;
    private Button buttonGET, buttonPOST, button_hours;
    private RequestQueue mQueue;
    private JSONArray latestRequestArray;
    CalenderActivity calenderActivity;
    Context context;
    public static final String REQUEST_TAG = "Activity";
    public static final String REQUEST_TAG2 = "Activty **POST**";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mTextView = (TextView) findViewById(R.id.textView);
        buttonGET = (Button) findViewById(R.id.buttonGET);
        buttonPOST = (Button) findViewById(R.id.buttonPOST);
        button_hours = (Button) findViewById(R.id.button_hours);
    }

    protected void onStart() {
        super.onStart();
        // Instantiate the RequestQueue.
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        String url = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api2/";
        DefaultBookedTablesRepository.createSingletonInstance(mQueue, url);


        buttonGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultBookedTablesRepository.getInstance().getBookedTables(
                        "dummy",
                        bookedTables -> {
                            mTextView.setText(bookedTables.toString());
                        },
                        error -> {
                        });
            }
        });
        buttonPOST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, CalenderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }
}
