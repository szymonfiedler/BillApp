package com.example.sara.billards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.sara.billards.booktable.DefaultTableBookingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Registration extends Activity implements Response.Listener,
        Response.ErrorListener {
    private TextView mTextView;
    private Button buttonGET,buttonPOST,button_hours;
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
        button_hours=(Button) findViewById(R.id.button_hours);
    }
    protected void onStart() {
        super.onStart();
        // Instantiate the RequestQueue.
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        String url = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api2/";
        DefaultTableBookingService.createSingletonInstance(mQueue, url);
        final MyJSONArrayRequest jsonRequest = new MyJSONArrayRequest(Request.Method
                .GET, url,
                new JSONArray(), this, this);
        JSONObject sampleObject = MyPOSTRequest.createSampleObject();
        final MyPOSTRequest postRequest = new MyPOSTRequest(
                url,
                sampleObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mTextView.setText("New reservation sent \nData:\n"+response);
                    }
                },
                this);

        jsonRequest.setTag(REQUEST_TAG);
        postRequest.setTag(REQUEST_TAG2);

        buttonGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(jsonRequest);
            }
        });
        buttonPOST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(postRequest);
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

    @Override
    public void onErrorResponse(VolleyError error) {
        mTextView.setText(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        latestRequestArray = ((JSONArray) response);
        mTextView.setText("Response is: " + response);
        try {
            int itemToDisplayId = latestRequestArray.length();
            int reservationId = latestRequestArray.getJSONObject(itemToDisplayId).getInt("ID_RES");
            int tableId = latestRequestArray.getJSONObject(itemToDisplayId).getInt("ID_TABLE");
            String date = latestRequestArray.getJSONObject(itemToDisplayId).getString("DATE");
            int startHour = latestRequestArray.getJSONObject(itemToDisplayId).getInt("HOUR_FROM");
            int endHour = latestRequestArray.getJSONObject(itemToDisplayId).getInt("HOUR_TO");
            int charge = latestRequestArray.getJSONObject(itemToDisplayId).getInt("CHARGE");

            String textToDisplay = "Response:" + "\n\n"
                    + "Reservation " +(itemToDisplayId+1) +" details \n"
                    +"ID: "+reservationId + ", table: " + tableId + "\n"
                    + date + " (" + startHour + "-" + endHour+")"
                    +"\nCharge: "+ charge + " zł";
            mTextView.setText(textToDisplay + "\n\nFull json array: \n\n" + response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
