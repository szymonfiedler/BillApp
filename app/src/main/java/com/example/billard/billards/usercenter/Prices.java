package com.example.billard.billards.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.billard.billards.R;
import com.example.billard.billards.authorization.JSONfunction;
import com.example.billard.billards.usercenter.Home;


public class Prices extends AppCompatActivity {


    TextView do1, po1, do2, po2, do3, po3;
    Button back;

    public void onBackPressed() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        do1 = (TextView) findViewById(R.id.pnsrdo);
        do2 = (TextView) findViewById(R.id.czwptdo);
        do3 = (TextView) findViewById(R.id.sbnddo);
        po1 = (TextView) findViewById(R.id.pnsrod);
        po2 = (TextView) findViewById(R.id.czwptod);
        po3 = (TextView) findViewById(R.id.sbndod);

        do1.setText(JSONfunction.week + " zł / godzinę");
        do2.setText(JSONfunction.week + " zł / godzinę");
        do3.setText(JSONfunction.weekend + " zł / godzinę");
        po1.setText(JSONfunction.week_aft + " zł / godzinę");
        po2.setText(JSONfunction.weekend + " zł / godzinę");
        po3.setText(JSONfunction.weekend + " zł / godzinę");
        back = (Button) findViewById(R.id.arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }


}
