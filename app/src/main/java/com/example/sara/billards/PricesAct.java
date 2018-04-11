package com.example.sara.billards;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.sara.billards.Prices.DefaultPricesRepository;
import com.example.sara.billards.booktable.DefaultBookedTablesRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PricesAct extends AppCompatActivity implements Response.Listener,Response.ErrorListener{
    public static final String TAG = "PricesActivity";
    TextView textView, tvinf;
    Button button;
    private RequestQueue mQueue;

    private JSONArray latestRequestArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        tvinf=(TextView) findViewById(R.id.tvinf);
        textView=(TextView) findViewById(R.id.textView);
        button=(Button) findViewById(R.id.button);


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DefaultPricesRepository.getInstance().getPrices(
//                        allPricess -> {
//                            Toast.makeText(getApplicationContext(), "all date from base ", Toast.LENGTH_SHORT).show();
//                            textView.setText(allPricess.toString());
//                        },
//                        error -> {
//                            Log.e(TAG, " error in PricesAct " + error);
//                        });
//            }
//        });


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
                    int id_table = latestRequestArray.getJSONObject(itemToDisplayId).getInt("id_table");
                    int week = latestRequestArray.getJSONObject(itemToDisplayId).getInt("week");
                    int weekend = latestRequestArray.getJSONObject(itemToDisplayId).getInt("weekend");
                    int week_aft = latestRequestArray.getJSONObject(itemToDisplayId).getInt("week_aft");

                    String textToDisplay = "Prices {" +
                            "id_table=" + id_table +
                            ", week=" + week +
                            ", weekend=" + weekend +
                            ", week_aft=" + week_aft +
                            '}';
                    textView.setText(textToDisplay + "\n\nFull json array: \n\n" + response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

}
