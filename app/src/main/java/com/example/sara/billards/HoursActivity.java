package com.example.sara.billards;

import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sara.billards.booktable.BookedTable;
import com.example.sara.billards.booktable.DefaultBookedTablesRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class HoursActivity extends AppCompatActivity {
    TextView tvHour11, tvHour12, tvHour13, tvHour14, tvHour15, tvHour16, tvHour17, tvHour18, tvHour19, tvHour20, tvHour21, tvHour22, tvHour23;
    Button bHour1,bHour2,bHour3,bHour4,bHour5,bHour6,bHour7,bHour8,bHour9,bHour10,bHour11,bHour12;
    private static final String TAG = "HoursActivity";
    private List<Button> allButtons;
    private List<TextView> allTextView;
Context context;
    BookedTable bookedTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);
        allTextView = new ArrayList<>();
        tvHour11 = (TextView) findViewById(R.id.tvHour11);
        tvHour12 = (TextView) findViewById(R.id.tvHour12);
        tvHour13 = (TextView) findViewById(R.id.tvHour13);
        tvHour14 = (TextView) findViewById(R.id.tvHour14);
        tvHour15 = (TextView) findViewById(R.id.tvHour15);
        tvHour16 = (TextView) findViewById(R.id.tvHour16);
        tvHour17 = (TextView) findViewById(R.id.tvHour17);
        tvHour18 = (TextView) findViewById(R.id.tvHour18);
        tvHour19 = (TextView) findViewById(R.id.tvHour19);
        tvHour20 = (TextView) findViewById(R.id.tvHour20);
        tvHour21 = (TextView) findViewById(R.id.tvHour21);
        tvHour22 = (TextView) findViewById(R.id.tvHour22);
        allTextView.add(tvHour11);
        allTextView.add(tvHour12);
        allTextView.add(tvHour13);
        allTextView.add(tvHour14);
        allTextView.add(tvHour15);
        allTextView.add(tvHour16);
        allTextView.add(tvHour17);
        allTextView.add(tvHour18);
        allTextView.add(tvHour19);
        allTextView.add(tvHour20);
        allTextView.add(tvHour21);
        allTextView.add(tvHour22);

        bHour1= (Button) findViewById(R.id.bHour1);
        bHour2= (Button) findViewById(R.id.bHour2);
        bHour3= (Button) findViewById(R.id.bHour3);
        bHour4= (Button) findViewById(R.id.bHour4);
        bHour5= (Button) findViewById(R.id.bHour5);
        bHour6= (Button) findViewById(R.id.bHour6);
        bHour7= (Button) findViewById(R.id.bHour7);
        bHour8= (Button) findViewById(R.id.bHour8);
        bHour9= (Button) findViewById(R.id.bHour9);
        bHour10= (Button) findViewById(R.id.bHour10);
        bHour11= (Button) findViewById(R.id.bHour11);
        bHour12= (Button) findViewById(R.id.bHour12);

        allButtons = new ArrayList<>();
        allButtons.add(bHour1);
        allButtons.add(bHour2);
        allButtons.add(bHour3);
        allButtons.add(bHour4);
        allButtons.add(bHour5);
        allButtons.add(bHour6);
        allButtons.add(bHour7);
        allButtons.add(bHour8);
        allButtons.add(bHour9);
        allButtons.add(bHour10);
        allButtons.add(bHour11);
        allButtons.add(bHour12);

        Intent intent =getIntent(); //get CalenderActivity intent
        String date= intent.getStringExtra("DATE"); // get date from CalendAractivity

        Intent intent2 =getIntent(); //get RegistrationActivity intent
        int tableId= intent2.getIntExtra("tableId",1);


        Log.e(TAG, "tableId  " + tableId);
        Log.e(TAG, "date from HoursActivity class " + date);

        //Log.e(TAG, "tableId from Registration class " + tableId);
        bHour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takeHour();

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour11.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour11.getText().toString().substring(7, 13));
                // intent.putExtra("Start_Hour", bookedTable.getStartHour());
                startActivity(intent);

            }
        });

        bHour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour12.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour12.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        bHour3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour13.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour13.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        bHour4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour14.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour14.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        bHour5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour15.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour15.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        bHour6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour16.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour16.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        bHour7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour17.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour17.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        bHour8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour18.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour18.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        bHour9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour19.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour19.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        bHour10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour20.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour20.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        bHour11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour21.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour21.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        bHour12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("tableId",tableId);
                intent.putExtra("DATE",date);
                intent.putExtra("startHour", tvHour22.getText().toString().substring(0, 5));
                intent.putExtra("endHour", tvHour22.getText().toString().substring(7, 13));

                startActivity(intent);
            }
        });
        Calendar cal, today;
        cal = Calendar.getInstance();
        today = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        today.set(Calendar.HOUR_OF_DAY, 0);


        String Today, now;
        Today = today.toString();
        Today = DateUtils.formatDate(today.getTime());
        now = sdf.format(cal.getTime());
        if (date.equals(Today)) {

            int Now = Integer.parseInt(now.substring(0, 2));

            if (Now > 11) {
                int bt = Now - 10;
                for (int i = 0; i < bt; i++) {
                    allButtons.get(i).setVisibility(View.GONE);
                    allTextView.get(i).setVisibility(View.GONE);
                }
            }
        }
        DefaultBookedTablesRepository.getInstance().getBookedTablesAtDate( //wyswietlenie wszystkich zajetych godzin po wybraniu konkretnego stolu i daty
                date,
                tableId,
                bookedTables -> {
                    Log.i(TAG, " Booked tables at date " + date + ": " + bookedTables);
                    List<BookedTable> bookedTablesSortedByStartHour = new ArrayList<>(bookedTables);
                    Collections.sort(bookedTablesSortedByStartHour, (o1, o2) -> o1.getStartHour() - o2.getStartHour());
                    int currentHour = 11;

                    for (BookedTable bookedTable: bookedTablesSortedByStartHour) {

                        int startHour = bookedTable.getStartHour();
                        int endHour = bookedTable.getEndHour();
                        // hack
                        if (startHour < 11) {
                            startHour += 12;
                            endHour += 12;
                        }
                        currentHour = startHour;
                        for (int i = startHour; i < endHour; i++) {
                            int indexOfButtonToBeDisabled = currentHour - 11;
                            allButtons.get(indexOfButtonToBeDisabled).setEnabled(false);//wygasniecie przycisku buttonu jesli godzina jest zarezerowwana w bazie danych
                            allButtons.get(indexOfButtonToBeDisabled).setText("Zajęty");
                            allButtons.get(indexOfButtonToBeDisabled).setBackgroundResource(R.drawable.button_shape_off);
                            currentHour++;
                        }

                    }

                },
                error -> Log.e(TAG, " BSth went wrong " + error.getMessage()));

    }


}
