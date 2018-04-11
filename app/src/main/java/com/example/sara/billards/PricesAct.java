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

import com.android.volley.RequestQueue;
import com.example.sara.billards.Prices.DefaultPricesRepository;
import com.example.sara.billards.booktable.DefaultBookedTablesRepository;


public class PricesAct extends AppCompatActivity {
    public static final String TAG = "PricesActivity";
    TextView textView, tvinf;
    Button button;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        tvinf=(TextView) findViewById(R.id.tvinf);
        textView=(TextView) findViewById(R.id.textView);
        button=(Button) findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultPricesRepository.getInstance().getPrices(
                        allPricess -> {
                            Toast.makeText(getApplicationContext(), "all date from base ", Toast.LENGTH_SHORT).show();
                            textView.setText(allPricess.toString());
                        },
                        error -> {
                            Log.e(TAG, " error in PricesAct " + error);
                        });
            }
        });


}

}
