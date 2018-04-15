package com.example.sara.billards.User_Control_Center;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sara.billards.R;
import com.example.sara.billards.registration.AsyncResponse;
import com.example.sara.billards.registration.Changepassword;
import com.example.sara.billards.registration.JSONfunction;
import com.example.sara.billards.registration.LoginActivity;
import com.example.sara.billards.tables.DefaultTablesRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class user_reservations extends Activity {


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_rezervations);
        LinearLayout scrViewButLay = (LinearLayout) findViewById(R.id.L2);
        scrViewButLay.setOrientation(LinearLayout.VERTICAL);


        for (int i = 0; i < JSONfunction.ID_RES.length; ++i) {
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = df.parse(JSONfunction.DATE[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            cal.setTime(date);


            if (today.before(cal)) {

                final TextView rez = new TextView(this);
                rez.setId(i);
                rez.setTextColor(Color.WHITE);
                String nazwa = "Rezerwacja nr " + JSONfunction.ID_RES[i] + "\n" + "W godzinach: " + JSONfunction.HOUR_FROM[i] + " - " + JSONfunction.HOUR_TO[i] + "\n" + "Data: " + JSONfunction.DATE[i] + "\n" + "Opłata: " + JSONfunction.CHARGE[i] + " zł";
                rez.setText(nazwa);
                rez.setTextSize(20);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
                rez.setLayoutParams(layoutParams);
                rez.setGravity(Gravity.CENTER);
                scrViewButLay.addView(rez);

                final Button cnl = new Button(this);
                cnl.setId(i);
                cnl.setBackgroundResource(R.drawable.button_shapes);
                cnl.setTextColor(Color.WHITE);
                String n2 = "Odwołaj rezerwacje";
                cnl.setText(n2);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(850,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams2.gravity = Gravity.CENTER;
                layoutParams2.setMargins(10, 10, 10, 10); //
                cnl.setLayoutParams(layoutParams2);
                scrViewButLay.addView(cnl);
            }
        }
    }


}
