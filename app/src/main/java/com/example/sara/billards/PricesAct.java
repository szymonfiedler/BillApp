package com.example.sara.billards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Response.ErrorListener;
import static com.android.volley.Response.Listener;


public class PricesAct extends AppCompatActivity implements Listener, ErrorListener {
    public static final String TAG = "PricesActivity";
    TextView textView, tvinf, textViewId_Club, textViewWeek, textViewWeek_aft, textViewWeekend;
    Button button;
    private RequestQueue mQueue;

    public PricesAct() {
        super();
    }

    private JSONArray latestRequestArray;
    int id_club;
    int week;
    int weekend;
    int week_aft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        tvinf = (TextView) findViewById(R.id.tvinf);
        textViewId_Club = (TextView) findViewById(R.id.textViewId_Club);
        textViewWeek = (TextView) findViewById(R.id.textViewWeek);
        textViewWeek_aft = (TextView) findViewById(R.id.textViewWeek_aft);
        textViewWeekend = (TextView) findViewById(R.id.textViewWeekend);
        button = (Button) findViewById(R.id.button);


    }

    protected void onStart() {
        super.onStart();
        // Instantiate the RequestQueue.
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        String url = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api3/";
        final MyJSONArrayRequest jsonRequest = new MyJSONArrayRequest(Request.Method
                .GET, url,
                new JSONArray(), this, this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(jsonRequest);


            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        textView.setText(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        latestRequestArray = ((JSONArray) response);
        Log.e(TAG, "latestRequestArray's size: " + latestRequestArray.length());


        try {
            for (int i = 0; i < latestRequestArray.length(); i++) {
                JSONObject jsonObject = latestRequestArray.getJSONObject(i);

                int itemToDisplayId = latestRequestArray.length();
                id_club = jsonObject.getInt("Id_club");
                week = jsonObject.getInt("week");
                weekend = jsonObject.getInt("weekend");
                week_aft = jsonObject.getInt("week_aft");
                textViewId_Club.setText(" " + id_club);
                textViewWeek.setText(" " + week);
                textViewWeekend.setText("" + weekend);
                textViewWeek_aft.setText(" " + week_aft);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}