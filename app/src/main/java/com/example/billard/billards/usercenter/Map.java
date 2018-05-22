package com.example.billard.billards.usercenter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.billard.billards.R;
import com.example.billard.billards.tables.DefaultTablesRepository;
import com.example.billard.billards.usercenter.Home;


public class Map extends AppCompatActivity {

    Button back;
    View v1, v2, v3, v4, v5, v6, v7;
    RelativeLayout v1_2, v2_2, v3_2, v4_2, v5_2, v6_2, v7_2;


    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v5 = findViewById(R.id.v5);
        v6 = findViewById(R.id.v6);
        v7 = findViewById(R.id.v7);
        v1_2 = (RelativeLayout) findViewById(R.id.v1_2);
        v2_2 = (RelativeLayout) findViewById(R.id.v2_2);
        v3_2 = (RelativeLayout) findViewById(R.id.v3_2);
        v4_2 = (RelativeLayout) findViewById(R.id.v4_2);
        v5_2 = (RelativeLayout) findViewById(R.id.v5_2);
        v6_2 = (RelativeLayout) findViewById(R.id.v6_2);
        v7_2 = (RelativeLayout) findViewById(R.id.v7_2);
        TextView s1 = (TextView) findViewById(R.id.typ_num1);
        TextView s2 = (TextView) findViewById(R.id.typ_num2);
        TextView s3 = (TextView) findViewById(R.id.typ_num3);
        TextView s4 = (TextView) findViewById(R.id.typ_num4);
        TextView s5 = (TextView) findViewById(R.id.typ_num5);
        TextView s6 = (TextView) findViewById(R.id.typ_num6);
        TextView s7 = (TextView) findViewById(R.id.typ_num7);
        s1.setText("Typ - " + DefaultTablesRepository.id_type[0] + "\n" + "Liczba miejsc - " + DefaultTablesRepository.num_of_seats[0]);
        s2.setText("Typ - " + DefaultTablesRepository.id_type[1] + "\n" + "Liczba miejsc - " + DefaultTablesRepository.num_of_seats[1]);
        s3.setText("Typ - " + DefaultTablesRepository.id_type[2] + "\n" + "Liczba miejsc - " + DefaultTablesRepository.num_of_seats[2]);
        s4.setText("Typ - " + DefaultTablesRepository.id_type[3] + "\n" + "Liczba miejsc - " + DefaultTablesRepository.num_of_seats[3]);
        s5.setText("Typ - " + DefaultTablesRepository.id_type[4] + "\n" + "Liczba miejsc - " + DefaultTablesRepository.num_of_seats[4]);
        s6.setText("Typ - " + DefaultTablesRepository.id_type[5] + "\n" + "Liczba miejsc - " + DefaultTablesRepository.num_of_seats[5]);
        s7.setText("Typ - " + DefaultTablesRepository.id_type[6] + "\n" + "Liczba miejsc - " + DefaultTablesRepository.num_of_seats[6]);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v1_2.setVisibility(View.VISIBLE);

            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v2_2.setVisibility(View.VISIBLE);
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v3_2.setVisibility(View.VISIBLE);
            }
        });
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v4_2.setVisibility(View.VISIBLE);
            }
        });
        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v5_2.setVisibility(View.VISIBLE);
            }
        });
        v6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v6_2.setVisibility(View.VISIBLE);
            }
        });
        v7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v7_2.setVisibility(View.VISIBLE);
            }
        });

        v1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v1_2.setVisibility(View.INVISIBLE);
            }
        });
        v2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v2_2.setVisibility(View.INVISIBLE);
            }
        });
        v3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v3_2.setVisibility(View.INVISIBLE);
            }
        });
        v4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v4_2.setVisibility(View.INVISIBLE);
            }
        });
        v5_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v5_2.setVisibility(View.INVISIBLE);
            }
        });
        v6_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v6_2.setVisibility(View.INVISIBLE);
            }
        });
        v7_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v7_2.setVisibility(View.INVISIBLE);
            }
        });
        back = (Button) findViewById(R.id.arrow);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

    }
}