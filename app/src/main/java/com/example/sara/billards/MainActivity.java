package com.example.sara.billards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import org.json.JSONArray;


public class MainActivity extends Activity {
    private static final String TAG = "MyActivity";

    Button binf, blogin;
    Context context;
    TextView t1,t2,t3,t4,t5,t6,t7;
    TextView[] t= {t1,t2,t3,t4,t5,t6,t7};
    int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);
        t7 = (TextView) findViewById(R.id.t7);

        binf=(Button) findViewById(R.id.binf);
        blogin= (Button) findViewById(R.id.blogin);

        //tables
t1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        context = getApplicationContext();
        Intent intent = new Intent(context, Registration.class);
        startActivity(intent);
    }
});
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                startActivity(intent);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                startActivity(intent);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                startActivity(intent);
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                startActivity(intent);
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                startActivity(intent);
            }
        });
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                startActivity(intent);

            }
        });

        binf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Inf.class);
                startActivity(intent);
            }
        });


        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
//        for (int i=0;i<= t.length;i++){
//            System.out.println("tablica" );
//
//            t[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    context = getApplicationContext();
//        Intent intent = new Intent(context, Registration.class);
//        startActivity(intent);
//                }
//            });
//        }
//    }}

//
