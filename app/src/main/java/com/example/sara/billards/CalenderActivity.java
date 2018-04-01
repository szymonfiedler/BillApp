package com.example.sara.billards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.sara.billards.booktable.TableOrder;
import com.example.sara.billards.booktable.BookedTable;
import com.example.sara.billards.booktable.Consumer;
import com.example.sara.billards.booktable.DefaultBookedTablesRepository;

import java.sql.Date;
import java.util.Calendar;

public class CalenderActivity extends AppCompatActivity {
    CalendarView calendar;
    Context context;
    String date;

    private static final String TAG = "CalenderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calender);

        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getBaseContext(), "selected date:" + year + "/" + month + "/" + dayOfMonth, Toast.LENGTH_LONG).show();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                date = DateUtils.formatDate(calendar.getTime());
                Log.e(TAG, " date from PostRequest class " + date);
                Toast.makeText(getBaseContext(), "get date():" + date, Toast.LENGTH_LONG).show();



//                DefaultBookedTablesRepository.getInstance().bookTable(
//                        TableOrder.builder()
//                                .withTableId(1)
//                                .withUserId(1)
//                                .withPrice(15d)
//                                .withStartHour(1)
//                                .withEndHour(2)
//                                .withDate(date)
//                                .build(),
//                        new Consumer<BookedTable>() {
//                            @Override
//                            public void accept(BookedTable bookedResponse) {
//                                Log.e(TAG, " Got Response " + bookedResponse);
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.e(TAG, " Got Error " + error.getMessage());
//                            }
//                        }
//
//                );

                context = getApplicationContext();
                Intent intent = new Intent(context, HoursActivity.class);
                intent.putExtra("DATE", date); // Here putting an extra text in e1
                startActivity(intent);

            }
        });
    }


}

