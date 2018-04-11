package com.example.sara.billards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.widget.CalendarView;
import android.widget.Toast;


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
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                if (today.after(calendar)) {
                    Toast.makeText(getApplicationContext(), "Nie można wybrac daty wcześniejszej niż dzisiejsza! ", Toast.LENGTH_SHORT).show();
                } else {
                    date = DateUtils.formatDate(calendar.getTime());


                    context = getApplicationContext();
                    Intent intent = new Intent(context, HoursActivity.class);
                    intent.putExtra("DATE", date); // wyslanie date w intent do klasy HoursActivity

                    Intent intent2 = getIntent(); //pobranie intentu RegistrationAct
                    int tableId = intent2.getIntExtra("tableId", 1); // odebranie TableId

                    intent.putExtra("tableId", tableId); // wyslanie tableId dalej->do HoursActivity
                    startActivity(intent);
                }
            }
        });
    }


}

