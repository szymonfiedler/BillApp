package com.example.sara.billards;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.example.sara.billards.booktable.BookedTable;
import com.example.sara.billards.booktable.Consumer;
import com.example.sara.billards.booktable.DefaultBookedTablesRepository;
import com.example.sara.billards.booktable.TableOrder;
import com.example.sara.billards.registration.LoginActivity;

public class EndActivity extends AppCompatActivity {
    Button buttonPUT,buttonGET, buttonShowMyChoice;
    TextView textView, user, idtable, dat, hour1, hour2;
    private static final String TAG = "EndActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        buttonPUT=(Button)findViewById(R.id.buttonPUT);


        user = (TextView) findViewById(R.id.textView5);
        idtable = (TextView) findViewById(R.id.textView7);
        dat = (TextView) findViewById(R.id.textView9);
        hour1 = (TextView) findViewById(R.id.textView11);
        hour2 = (TextView) findViewById(R.id.textView13);
        Intent intent =getIntent(); //intent from HoursActivity
        String date= intent.getStringExtra("DATE");

        int tableId= intent.getIntExtra("tableId",1);
        int userId = LoginActivity.user_id;
        String startHour= intent.getStringExtra("startHour");

        String endHour= intent.getStringExtra("endHour");

        Log.e(TAG, "tableId  " + tableId);
        Log.e(TAG, "date from HoursActivity class " + date);

        Log.e(TAG, "startHour " +startHour);
        Log.e(TAG, "endHour " +endHour);


        user.setText(" " + LoginActivity.Username);
        idtable.setText(" " + tableId);
        dat.setText(" " + date);
        hour1.setText(" " + startHour + " ");
        hour2.setText(" " + endHour);

        buttonPUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DefaultBookedTablesRepository.getInstance().bookTable(
                        TableOrder.builder()
                                .withTableId(tableId)
                                .withUserId(userId)
                                .withPrice(15d)
                                .withStartHour(startHour)
                                .withEndHour(endHour)
                                .withDate(date)
                                .build(),
                        new Consumer<BookedTable>() {
                            @Override
                            public void accept(BookedTable bookedResponse) {
                                AlertDialog.Builder builder;

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(EndActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(EndActivity.this);
                                }

                                builder.setTitle("Zarezerwowano")
                                        .setMessage("Zarezerwowano pomyślnie!")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .show();
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
