package com.example.billard.billards.usercenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.example.billard.billards.JSONRequests.CustomVolleyRequestQueue;
import com.example.billard.billards.R;
import com.example.billard.billards.booktable.BookedTable;
import com.example.billard.billards.booktable.Consumer;
import com.example.billard.billards.booktable.DefaultBookedTablesRepository;
import com.example.billard.billards.booktable.TableOrder;
import com.example.billard.billards.common.SaveSharedPreference;
import com.example.billard.billards.tables.DefaultTablesRepository;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class EndActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Button reserve, back, cht, chd;
    TextView user, stol, dat, hour, num, typ;
    Context context;
    ImageView table;
    private static final String TAG = "EndActivity";
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private String datee;
    private int tableId;

    public void onBackPressed() {
        Intent intent = new Intent(EndActivity.this, Home.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        context = getApplicationContext();
        reserve = findViewById(R.id.reserve);
        back = findViewById(R.id.arrow);
        chd = findViewById(R.id.changetermin);
        cht = findViewById(R.id.changetable);
        typ = findViewById(R.id.typ);
        num = findViewById(R.id.num);
        table = findViewById(R.id.table);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });


        user = findViewById(R.id.user);
        stol = findViewById(R.id.stol);
        dat = findViewById(R.id.date);
        hour = findViewById(R.id.hour);
        Intent intent = getIntent();
        String date = intent.getStringExtra("DATE");

        tableId = intent.getIntExtra("tableId", 1);
        int userId = SaveSharedPreference.getUserID(EndActivity.this);
        String startHour = intent.getStringExtra("startHour");

        String endHour = intent.getStringExtra("endHour");

        String data2 = date.substring(8, 10) + "." + date.substring(5, 7) + "." + date.substring(0, 4);
        user.setText("Użytkownik " + "\n" + SaveSharedPreference.getUserName(EndActivity.this));
        stol.setText("Stół " + tableId);
        dat.setText("Data: " + data2 + "r.");
        hour.setText("Godzina: " + startHour + ":00 - " + endHour + ":00");
        typ.setText("Typ - " + DefaultTablesRepository.id_type[tableId - 1]);
        num.setText("Liczba miejsc przy stole - " + DefaultTablesRepository.num_of_seats[tableId - 1]);
        switch (DefaultTablesRepository.id_type[tableId - 1]) {
            case "Snooker":
                table.setBackgroundResource(R.drawable.snooker);
                break;
            case "Pool":
                table.setBackgroundResource(R.drawable.pool);
                break;
            case "Karambol":
                table.setBackgroundResource(R.drawable.carambol);
                break;
        }


        chd.setOnClickListener(view -> {
            context = getApplicationContext();
            int tab = tableId;
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    EndActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            Calendar today = Calendar.getInstance();
            dpd.setOkColor(Color.BLACK);
            dpd.setCancelColor(Color.BLACK);
            Calendar cal;
            cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String noww;
            noww = sdf.format(cal.getTime());
            int Now = Integer.parseInt(noww.substring(0, 2));
            if (Now > 22) {
                dpd.setDisabledDays(new Calendar[]{today});
            }
            dpd.vibrate(false);
            dpd.setTitle("Wybierz dzień");
            today.set(Calendar.HOUR_OF_DAY, 0);
            dpd.setMinDate(today);
            dpd.setVersion(DatePickerDialog.Version.VERSION_1);
            dpd.show(getFragmentManager(), "Datepickerdialog");


        });


        cht.setOnClickListener(view -> {
            Intent intenth = new Intent(EndActivity.this, Home.class);
            startActivity(intenth);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });


        reserve.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(EndActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_question);
            Button dialog_btn = dialog.findViewById(R.id.tak);
            Button dialog_btn2 = dialog.findViewById(R.id.nie);
            TextView title = dialog.findViewById(R.id.text1);
            TextView text = dialog.findViewById(R.id.text2);
            title.setText("Rezerwacje");
            text.setText("Czy napewno chcesz zarezerwować?");
            Window window = dialog.getWindow();
            assert window != null;

            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setGravity(Gravity.CENTER);
            dialog.show();

            dialog_btn.setOnClickListener(view ->
                    DefaultBookedTablesRepository.getInstance().bookTable(
                            TableOrder.builder()
                                    .withTableId(tableId)
                                    .withUserId(userId)
                                    .withPrice(15d)
                                    .withStartHour(startHour)
                                    .withEndHour(endHour)
                                    .withDate(date)
                                    .build(),
                            bookedResponse -> {
                                showDialog();
                            }, error -> Log.e(TAG, " Got Error " + error.getMessage())
                    ));


            dialog_btn2.setOnClickListener(view -> {
                dialog.dismiss();
            });
            dialog.setOnKeyListener((arg0, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            });
        });


    }


    private void showDialog() {

        final Dialog dialog = new Dialog(EndActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ok);
        Button dialog_btn = dialog.findViewById(R.id.ok2);
        TextView title = dialog.findViewById(R.id.text1);
        TextView text = dialog.findViewById(R.id.text2);
        title.setText("Rezerwacje");
        text.setText("Zarezerwowano!");
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        dialog.show();

        dialog_btn.setOnClickListener(view -> {
            Intent intent = new Intent(EndActivity.this, Home.class);
            startActivity(intent);
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            finish();
        });

        dialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                Intent intent = new Intent(EndActivity.this, Home.class);
                startActivity(intent);

                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

                dialog.dismiss();
                finish();
            }
            return true;
        });

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);

        datee = df.format(calendar.getTime());

        RequestQueue mQueue = CustomVolleyRequestQueue.getInstance(EndActivity.this)
                .getRequestQueue();


        String url = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api2/";
        DefaultBookedTablesRepository.createSingletonInstance(mQueue, url);
        DefaultBookedTablesRepository.getInstance().getBookedTablesAtDate( //wyswietlenie wszystkich zajetych godzin po wybraniu konkretnego stolu i daty
                datee,
                tableId,
                bookedTables -> {
                    int ii = 0;
                    int[] startHour;
                    int[] endHour;
                    List<BookedTable> bookedTablesSortedByStartHour = new ArrayList<>(bookedTables);
                    Collections.sort(bookedTablesSortedByStartHour, (o1, o2) -> o1.getStartHour() - o2.getStartHour());
                    startHour = new int[bookedTablesSortedByStartHour.toArray().length];
                    endHour = new int[bookedTablesSortedByStartHour.toArray().length];
                    for (BookedTable bookedTable : bookedTablesSortedByStartHour) {

                        startHour[ii] = bookedTable.getStartHour();
                        endHour[ii] = bookedTable.getEndHour();

                        ii += 1;

                    }
                    starttime(startHour, endHour);
                },
                error -> {
                });
    }


    private void starttime(int[] start, int[] end) {

        Calendar noww = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog dpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                EndActivity.this,
                noww.get(Calendar.HOUR),
                noww.get(Calendar.MINUTE),
                true
        );
        Timepoint[] bl = new Timepoint[start.length];
        for (int g = 0; g < start.length; g++) {
            for (; start[g] < end[g]; start[g]++) {
                Timepoint b = new Timepoint(start[g]);
                bl[g] = b;

            }
        }


        dpd.setDisabledTimes(bl);
        dpd.setVersion(com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version.VERSION_1);
        dpd.setOkColor(Color.BLACK);
        dpd.setCancelColor(Color.BLACK);
        Timepoint time, time1;
        time = new Timepoint(11);
        time1 = new Timepoint(23);
        dpd.setMinTime(time);
        dpd.setMaxTime(time1);
        dpd.setTitle("Wybierz godzinę");
        dpd.vibrate(false);
        dpd.enableMinutes(false);
        dpd.show(getFragmentManager(), "Timepickerdialog");


    }

    @Override
    public void onTimeSet(TimePickerDialog timePickerDialog, int i, int i1, int i2) {
        String starthour, endhour;
        starthour = Integer.toString(i);
        i += 1;
        endhour = Integer.toString(i);
        context = getApplicationContext();
        Intent intent = new Intent(context, EndActivity.class);
        intent.putExtra("tableId", tableId);
        intent.putExtra("DATE", datee);
        intent.putExtra("startHour", starthour);
        intent.putExtra("endHour", endhour);

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
