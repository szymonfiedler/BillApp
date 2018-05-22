package com.example.billard.billards.usercenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.billard.billards.JSONRequests.CustomVolleyRequestQueue;
import com.example.billard.billards.R;
import com.example.billard.billards.authorization.Change_password;
import com.example.billard.billards.authorization.LoginActivity;
import com.example.billard.billards.booktable.BookedTable;
import com.example.billard.billards.booktable.DefaultBookedTablesRepository;
import com.example.billard.billards.common.SaveSharedPreference;
import com.example.billard.billards.tables.DefaultTablesRepository;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.example.billard.billards.authorization.JSONfunction.getPrices;
import static com.example.billard.billards.authorization.JSONfunction.getRes;
import static com.example.billard.billards.authorization.JSONfunction.getUserInfo;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {


    Context context;
    public DrawerLayout drawerLayout;
    private ProgressBar progressBar;
    private int tab;
    private String date;
    private static final String AUTH_TOKEN_URL = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/user_history/";
    private final String USER_AGENT = "Mozilla/5.0";
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static final String L_TAG = Home.class.getSimpleName();
    private ReservationTask mRes = null;
    private PricesTask mPri = null;
    private UserInfoTask mUse = null;
    public Timepoint[] bl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        drawerLayout = findViewById(R.id.drawer_layout);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getSupportActionBar().setElevation(0);


        RelativeLayout.LayoutParams Params1 = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.text1_width), (int) getResources().getDimension(R.dimen.text1_height));
        RelativeLayout.LayoutParams Params2 = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.text2_width), (int) getResources().getDimension(R.dimen.text2_height));
        RelativeLayout.LayoutParams Params3 = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.text3_width), (int) getResources().getDimension(R.dimen.text3_height));
        RelativeLayout.LayoutParams Params4 = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.image_width), (int) getResources().getDimension(R.dimen.image_height));
        int size = DefaultTablesRepository.sizee;

        LinearLayout scrViewButLay = (LinearLayout) findViewById(R.id.LL1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        scrViewButLay.setOrientation(LinearLayout.VERTICAL);
        for (int i = 1; i <= size; i++) {
            // Add Buttons
            final RelativeLayout button = new RelativeLayout(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.chart_width), (int) getResources().getDimension(R.dimen.chart_height));
            if (i == size) {
                layoutParams.setMargins((int) getResources().getDimension(R.dimen.padding_left), (int) getResources().getDimension(R.dimen.padding_top), (int) getResources().getDimension(R.dimen.padding_left), (int) getResources().getDimension(R.dimen.padding_top));
            } else {
                layoutParams.setMargins((int) getResources().getDimension(R.dimen.padding_left), (int) getResources().getDimension(R.dimen.padding_top), (int) getResources().getDimension(R.dimen.padding_left), 0);
            }
            button.setLayoutParams(layoutParams);
            button.setId(i);
            button.setBackgroundResource(R.drawable.box);
            final TextView stol = new TextView(this);
            final TextView typ = new TextView(this);
            final TextView num = new TextView(this);
            final ImageView im = new ImageView(this);
            button.addView(stol);
            button.addView(typ);
            button.addView(num);
            button.addView(im);
            Params1.setMargins((int) getResources().getDimension(R.dimen.text1_left), (int) getResources().getDimension(R.dimen.text1_top), 0, 0);
            Params2.setMargins((int) getResources().getDimension(R.dimen.text2_left), (int) getResources().getDimension(R.dimen.text2_top), 0, 0);
            Params3.setMargins((int) getResources().getDimension(R.dimen.text2_left), (int) getResources().getDimension(R.dimen.test3_top), 0, 0);
            Params4.setMargins((int) getResources().getDimension(R.dimen.image_left), (int) getResources().getDimension(R.dimen.image_top), 0, 0);
            stol.setLayoutParams(Params1);
            typ.setLayoutParams(Params2);
            num.setLayoutParams(Params3);
            im.setLayoutParams(Params4);
            stol.setTextColor(Color.WHITE);
            typ.setTextColor(Color.WHITE);
            num.setTextColor(Color.WHITE);
            stol.setText(" Stół " + i);
            stol.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
            stol.setShadowLayer(4f, 2, 6, Color.BLACK);
            stol.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            typ.setText("Typ - " + DefaultTablesRepository.id_type[i - 1]);
            typ.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
            typ.setShadowLayer(4f, 2, 6, Color.BLACK);
            typ.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            num.setText("Liczba miejsc przy stole - " + DefaultTablesRepository.num_of_seats[i - 1]);
            num.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
            num.setShadowLayer(4f, 2, 6, Color.BLACK);
            num.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            switch (DefaultTablesRepository.id_type[i - 1]) {
                case "Snooker":
                    im.setBackgroundResource(R.drawable.snooker);
                    break;
                case "Pool":
                    im.setBackgroundResource(R.drawable.pool);
                    break;
                case "Karambol":
                    im.setBackgroundResource(R.drawable.carambol);
                    break;
            }

            int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    context = getApplicationContext();
                    tab = finalI;
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            Home.this,
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

                }
            });

            scrViewButLay.addView(button);


        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView user = (TextView) headerView.findViewById(R.id.texttt);
        user.setText("Użytkownik " + SaveSharedPreference.getUserName(Home.this));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.finishAffinity();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            progressBar.setVisibility(View.VISIBLE);
            context = getApplicationContext();
            SaveSharedPreference.clearUserInfo(context);
            Intent intent = new Intent(context, LoginActivity.class);
            progressBar.setVisibility(View.GONE);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (id == R.id.nav_profil) {

            progressBar.setVisibility(View.VISIBLE);
            attemptgetuserinfo();

        } else if (id == R.id.nav_rez) {

            progressBar.setVisibility(View.VISIBLE);
            attemptgetres();

        } else if (id == R.id.nav_map) {
            progressBar.setVisibility(View.VISIBLE);
            context = getApplicationContext();
            Intent intent = new Intent(context, Map.class);
            progressBar.setVisibility(View.GONE);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        } else if (id == R.id.nav_chp) {
            progressBar.setVisibility(View.VISIBLE);
            context = getApplicationContext();
            Intent intent = new Intent(context, Change_password.class);
            progressBar.setVisibility(View.GONE);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (id == R.id.nav_prices) {

            progressBar.setVisibility(View.VISIBLE);
            attemptgetprices();

        } else if (id == R.id.nav_inf) {
            progressBar.setVisibility(View.VISIBLE);
            context = getApplicationContext();
            Intent intent = new Intent(context, Inf.class);
            progressBar.setVisibility(View.GONE);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);

        date = df.format(calendar.getTime());

        RequestQueue mQueue = CustomVolleyRequestQueue.getInstance(Home.this)
                .getRequestQueue();
        int tableId = tab;


        String url = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api2/";
        DefaultBookedTablesRepository.createSingletonInstance(mQueue, url);
        DefaultBookedTablesRepository.getInstance().getBookedTablesAtDate( //wyswietlenie wszystkich zajetych godzin po wybraniu konkretnego stolu i daty
                date,
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
                Home.this,
                noww.get(Calendar.HOUR),
                noww.get(Calendar.MINUTE),
                true
        );
        bl = new Timepoint[start.length];
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
    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog timePickerDialog, int i, int i1, int i2) {
        int a = 0;
        for (Timepoint aBl : bl) {
            if (i == aBl.getHour()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Nie można wybrać tej godziny!", Toast.LENGTH_SHORT)
                        .show();
                a = 1;
            }
        }
        if (a == 0) {
            String starthour, endhour;
            starthour = Integer.toString(i);
            i += 1;
            endhour = Integer.toString(i);
            context = getApplicationContext();
            Intent intent = new Intent(context, EndActivity.class);
            intent.putExtra("tableId", tab);
            intent.putExtra("DATE", date);
            intent.putExtra("startHour", starthour);
            intent.putExtra("endHour", endhour);

            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }

    private void attemptgetres() {
        mRes = new ReservationTask(this);
        mRes.execute((Void) null);
    }

    public void processFinish(String token) {

        try {
            ArrayList<String> a = getRes(token);
            context = getApplicationContext();
            Intent intent = new Intent(context, UserReservations.class);
            progressBar.setVisibility(View.GONE);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (JSONException e) {
            mRes = new ReservationTask(this);
            mRes.execute((Void) null);
            e.printStackTrace();
        }

    }


    public class ReservationTask extends AsyncTask<Void, Void, String> {


        private Boolean success = false;
        public Home delegate = null;


        ReservationTask(Home delegate) {
            this.delegate = delegate;
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                return getToken();

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        protected String getToken() {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.

            String token = SaveSharedPreference.getUserToken(Home.this);
            try {
                URL url = new URL(AUTH_TOKEN_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");

                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(false);
                Log.d(L_TAG, "Set up data unrelated headers");

                conn.setRequestProperty("User-Agent", USER_AGENT);
                //header crap
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("Authorization", token);
                // conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //Setup sen

                //

                //connect
                conn.connect();


                int serverResponseCode = conn.getResponseCode();
                // do something with response
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
                if (is != null) {
                    is.close();
                }
                String serverResponseMessage = conn.getResponseMessage();
                //  int serverResponseCode = conn.getResponseCode();
                if (serverResponseCode == 200) {
                    this.success = true;
                } else {
                    Log.d(L_TAG, serverResponseMessage + " " + serverResponseCode);
                }

                Log.d(L_TAG, contentAsString);
                return contentAsString;

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e(L_TAG, "Exeption");
                return "";
            }


        }


        public String readIt(InputStream stream) throws IOException {
            int len;
            byte[] buffer = new byte[10000];
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            while ((len = stream.read(buffer)) != -1) {
                byteArray.write(buffer, 0, len);
            }
            return new String(byteArray.toByteArray());

        }


        @Override
        protected void onPostExecute(String response) {
            mRes = null;

            if (this.success) {

                this.delegate.processFinish(response);


            } else {
                Log.d(L_TAG, response);
            }
        }

        @Override
        protected void onCancelled() {
            mRes = null;

        }
    }

    private void attemptgetprices() {
        mPri = new PricesTask(this);
        mPri.execute((Void) null);
    }

    public void processFinish2(String token) {

        try {
            ArrayList<String> a = getPrices(token);
            context = getApplicationContext();
            Intent intent = new Intent(context, Prices.class);
            progressBar.setVisibility(View.GONE);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (JSONException e) {
            mPri = new PricesTask(this);
            mPri.execute((Void) null);
            e.printStackTrace();
        }

    }

    public class PricesTask extends AsyncTask<Void, Void, String> {


        private Boolean success = false;
        public Home delegate = null;


        PricesTask(Home delegate) {
            this.delegate = delegate;
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                return getToken();

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        protected String getToken() {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.

            String token = SaveSharedPreference.getUserToken(Home.this);
            try {
                URL url = new URL("http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api3/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");

                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(false);
                Log.d(L_TAG, "Set up data unrelated headers");

                conn.setRequestProperty("User-Agent", USER_AGENT);
                //header crap
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                // conn.setRequestProperty("Authorization", token);
                // conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //Setup sen

                //

                //connect
                conn.connect();


                int serverResponseCode = conn.getResponseCode();
                // do something with response
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
                if (is != null) {
                    is.close();
                }
                String serverResponseMessage = conn.getResponseMessage();
                //  int serverResponseCode = conn.getResponseCode();
                if (serverResponseCode == 200) {
                    this.success = true;
                } else {
                    Log.d(L_TAG, serverResponseMessage + " " + serverResponseCode);
                }

                Log.d(L_TAG, contentAsString);
                return contentAsString;

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e(L_TAG, "Exeption");
                return "";
            }


        }


        public String readIt(InputStream stream) throws IOException {
            int len;
            byte[] buffer = new byte[10000];
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            while ((len = stream.read(buffer)) != -1) {
                byteArray.write(buffer, 0, len);
            }
            return new String(byteArray.toByteArray());

        }


        @Override
        protected void onPostExecute(String response) {
            mPri = null;

            if (this.success) {

                this.delegate.processFinish2(response);


            } else {
                Log.d(L_TAG, response);
            }
        }

        @Override
        protected void onCancelled() {
            mPri = null;

        }
    }

    private void attemptgetuserinfo() {

        mUse = new UserInfoTask(this);
        mUse.execute((Void) null);
    }

    public void processFinish3(String token) {

        try {

            ArrayList<String> a = getUserInfo(token);
            Context context = getApplicationContext();
            Intent intent = new Intent(context, UserProfile.class);

            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            progressBar.setVisibility(View.GONE);
        } catch (JSONException e) {
            mUse = new Home.UserInfoTask(this);
            mUse.execute((Void) null);
            e.printStackTrace();
        }

    }

    public class UserInfoTask extends AsyncTask<Void, Void, String> {


        private Boolean success = false;
        public Home delegate = null;


        UserInfoTask(Home delegate) {
            this.delegate = delegate;
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                return getToken();

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        protected String getToken() {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.

            String token = SaveSharedPreference.getUserToken(Home.this);
            try {

                URL url = new URL("http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/user_info/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");

                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(false);
                Log.d(L_TAG, "Set up data unrelated headers");

                conn.setRequestProperty("User-Agent", USER_AGENT);
                //header crap
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("Authorization", token);
                // conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //Setup sen

                //

                //connect
                conn.connect();


                int serverResponseCode = conn.getResponseCode();
                // do something with response
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
                if (is != null) {
                    is.close();
                }
                String serverResponseMessage = conn.getResponseMessage();
                //  int serverResponseCode = conn.getResponseCode();
                if (serverResponseCode == 200) {
                    this.success = true;
                } else {
                    Log.d(L_TAG, serverResponseMessage + " " + serverResponseCode);
                }

                Log.d(L_TAG, contentAsString);
                return contentAsString;

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e(L_TAG, "Exeption");
                return "";
            }


        }


        public String readIt(InputStream stream) throws IOException {
            int len;
            byte[] buffer = new byte[10000];
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            while ((len = stream.read(buffer)) != -1) {
                byteArray.write(buffer, 0, len);
            }
            return new String(byteArray.toByteArray());

        }


        @Override
        protected void onPostExecute(String response) {
            mUse = null;

            if (this.success) {

                this.delegate.processFinish3(response);


            } else {
                Log.d(L_TAG, response);
            }
        }

        @Override
        protected void onCancelled() {
            mUse = null;

        }
    }


}

