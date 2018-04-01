package com.example.sara.billards;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sara.billards.booktable.BookedTable;
import com.example.sara.billards.booktable.DefaultBookedTablesRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class HoursActivity extends AppCompatActivity {
TextView tvHour1, tvHour2,tvHour3,tvHour4,tvHour5,tvHour6,tvHour7,tvHour8,tvHour9,tvHour10,tvHour11,tvHour12;
    Button bHour1,bHour2,bHour3,bHour4,bHour5,bHour6,bHour7,bHour8,bHour9,bHour10,bHour11,bHour12;
    private static final String TAG = "HoursActivity";
    private List<Button> allButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);

        tvHour1 = (TextView) findViewById(R.id.tvHour1);
        tvHour2 = (TextView) findViewById(R.id.tvHour2);
        tvHour3 = (TextView) findViewById(R.id.tvHour3);
        tvHour4 = (TextView) findViewById(R.id.tvHour4);
        tvHour5 = (TextView) findViewById(R.id.tvHour5);
        tvHour6 = (TextView) findViewById(R.id.tvHour6);
        tvHour7 = (TextView) findViewById(R.id.tvHour7);
        tvHour8 = (TextView) findViewById(R.id.tvHour8);
        tvHour9 = (TextView) findViewById(R.id.tvHour9);
        tvHour10 = (TextView) findViewById(R.id.tvHour10);
        tvHour11 = (TextView) findViewById(R.id.tvHour11);
        tvHour12 = (TextView) findViewById(R.id.tvHour12);

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




        //Log.e(TAG, "tableId from Registration class " + tableId);
        bHour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               takeHour();
            }
        });
        Intent intent =getIntent(); //pobranie intentu CalenderActivity
        String date= intent.getStringExtra("DATE"); // pobranie danych z CalendAractivity

        Intent intent2 =getIntent(); //pobranie intentu RegistrationAct
        int tableId= intent2.getIntExtra("tableId",1);


        Log.e(TAG, "tableId  " + tableId);
        Log.e(TAG, "date from HoursActivity class " + date);

        DefaultBookedTablesRepository.getInstance().getBookedTablesAtDate( //wyswietlenie wszystkich zajetych godzin po wybraniu konkretnego stolu i daty
                date,
                tableId, //TODO put table id here (pobierz idTable z poczatkowej activity.wyslij PUT dopiero w endActivity na samym koncu)
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
                            currentHour++;
                        }
                    }

                },
                error -> Log.e(TAG, " BSth went wrong " + error.getMessage()));

    }
    public String takeHour() {
        bHour1.setBackgroundColor(Color.RED);
        String hour=tvHour1.getText().toString();
        Toast.makeText(getApplicationContext(), "click " + hour, Toast.LENGTH_SHORT).show();
        return hour;
    }


}
