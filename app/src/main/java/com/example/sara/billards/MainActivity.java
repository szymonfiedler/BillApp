package com.example.sara.billards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sara.billards.registration.LoginActivity;
import com.example.sara.billards.registration.User_reg;


public class MainActivity extends Activity {
    private static final String TAG = "MyActivity";

    Button binf, blogin, bregister, bprices, brez;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binf = (Button) findViewById(R.id.binf);
        blogin = (Button) findViewById(R.id.blogin);
        bregister = (Button) findViewById(R.id.brej);
        bprices = (Button) findViewById(R.id.bprices);
        brez = (Button) findViewById(R.id.brez);

        View a = findViewById(R.id.brej);
        View b = findViewById(R.id.brez);
        View c = findViewById(R.id.blogin);

        if (LoginActivity.logged == 0) {
            a.setVisibility(View.VISIBLE);
            b.setVisibility(View.INVISIBLE);
            c.setVisibility(View.VISIBLE);
        }
        if (LoginActivity.logged > 0) {
            a.setVisibility(View.GONE);
            b.setVisibility(View.VISIBLE);
            c.setVisibility(View.GONE);
        }


        binf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Inf.class);
                startActivity(intent);
            }
        });

        brez.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, After_registration.class);
                startActivity(intent);
            }
        });


        bprices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Prices.class);
                startActivity(intent);
            }
        });


        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, User_reg.class);
                startActivity(intent);
            }
        });

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
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
