package com.example.sara.billards.booktable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sara.billards.R;

public class EndActivity extends AppCompatActivity {
    Button buttonPUT,buttonGET, buttonShowMyChoice;
    TextView textView;
    private static final String TAG = "EndActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        buttonGET=(Button)findViewById(R.id.buttonGET);
        buttonPUT=(Button)findViewById(R.id.buttonPUT);
        buttonShowMyChoice=(Button)findViewById(R.id.buttonShowMyChoice);
        textView=(TextView)findViewById(R.id.textView);

        Intent intent =getIntent(); //intent from HoursActivity
        String date= intent.getStringExtra("DATE");

        int tableId= intent.getIntExtra("tableId",1);

        String startHour= intent.getStringExtra("startHour");
        String endHour= intent.getStringExtra("endHour");

        Log.e(TAG, "tableId  " + tableId);
        Log.e(TAG, "date from HoursActivity class " + date);

        Log.e(TAG, "startHour " +startHour);
        Log.e(TAG, "endHour " +endHour);


        buttonGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultBookedTablesRepository.getInstance().getBookedTables(
                        bookedTables -> {
                            Toast.makeText(getApplicationContext(), "all date from base ", Toast.LENGTH_SHORT).show();
                            textView.setText(bookedTables.toString());
                        },
                        error -> {
                        });
            }
        });
        buttonShowMyChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("tableId: "+tableId+" ,date: "+date+" hourFrom: "+startHour+" ,hourTo: "+endHour);
            }
        });

        buttonPUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
