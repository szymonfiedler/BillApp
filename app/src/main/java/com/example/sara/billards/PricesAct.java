package com.example.sara.billards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;


public class PricesAct extends AppCompatActivity implements Response.Listener, Response.ErrorListener {
    public static final String TAG = "PricesActivity";
    TextView textView, tvinf;
    Button button;
    private RequestQueue mQueue;

    public PricesAct() {
        super();
    }

    private JSONArray latestRequestArray;
    int id_table, week, weekend, week_aft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        tvinf = (TextView) findViewById(R.id.tvinf);
        textView = (TextView) findViewById(R.id.textView);
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
        textView.setText(" " + response);
        try {
            int itemToDisplayId = latestRequestArray.length();
            id_table = latestRequestArray.getJSONObject(itemToDisplayId).getInt("id_table");
            week = latestRequestArray.getJSONObject(itemToDisplayId).getInt("week");
            weekend = latestRequestArray.getJSONObject(itemToDisplayId).getInt("weekend");
            week_aft = latestRequestArray.getJSONObject(itemToDisplayId).getInt("week_aft");

            textView.setText(toString() + "\n \n\n" + response + "\n");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "Prices {" +
                "id_table=" + id_table +
                ", week=" + week +
                ", weekend=" + weekend +
                ", week_aft=" + week_aft +
                '}';
    }
}