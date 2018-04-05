package com.example.sara.billards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.sara.billards.R;
import com.example.sara.billards.booktable.BookedTable;
import com.example.sara.billards.booktable.Consumer;
import com.example.sara.billards.booktable.DefaultBookedTablesRepository;
import com.example.sara.billards.booktable.TableOrder;

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

                DefaultBookedTablesRepository.getInstance().bookTable(
                        TableOrder.builder()
                                .withTableId(tableId)
                                .withUserId(1)
                                .withPrice(15d)
                                .withStartHour(startHour)
                                .withEndHour(endHour)
                                .withDate(date)
                                .build(),
                        new Consumer<BookedTable>() {
                            @Override
                            public void accept(BookedTable bookedResponse) {
                                Log.e(TAG, " Got Response " + bookedResponse);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, " Got Error " + error.getMessage());
                            }
                        }
                );

            }
        });


    }
}
