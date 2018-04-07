package com.example.sara.billards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class After_registration extends Activity {
    private static final String TAG = "After_registration";
    Context context;
    TextView t1, t2, t3, t4, t5, t6, t7;
    TextView[] t = {t1, t2, t3, t4, t5, t6, t7};
    int i;
    public static int tab1Id = 1, tab2Id = 2, tab3Id = 3, tab4Id = 4, tab5Id = 5, tab6Id = 6, tab7Id = 7;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);
        t7 = (TextView) findViewById(R.id.t7);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);

                intent.putExtra("tableId", tab1Id);
                startActivity(intent);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                intent.putExtra("tableId", tab2Id);
                startActivity(intent);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                intent.putExtra("tableId", tab3Id);
                startActivity(intent);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                intent.putExtra("tableId", tab4Id);
                startActivity(intent);
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                intent.putExtra("tableId", tab5Id);
                startActivity(intent);
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                intent.putExtra("tableId", tab6Id);
                startActivity(intent);
            }
        });
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Registration.class);
                intent.putExtra("tableId", tab7Id);
                startActivity(intent);

            }
        });

    }
}
