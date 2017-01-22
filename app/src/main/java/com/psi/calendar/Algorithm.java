package com.psi.calendar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Algorithm extends AppCompatActivity {



    private ArrayList<Sesion> time_table_1 = new ArrayList<Sesion>();
    private ArrayList<Sesion> time_table_2 = new ArrayList<Sesion>();
    private ArrayList<Sesion> time_table_3 = new ArrayList<Sesion>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button nextActivity = (Button)findViewById(R.id.btnToCalendarOption);
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCalendarOption();
            }
        });


        // EXPORT TO CALENDAR --------------------
        //int hora, int duracion, int dia, String nombre, String tipo
        time_table_1.add(new Sesion (10, 2, 16, "AO", "Teoría"));
        time_table_1.add(new Sesion (12, 2, 16, "EMP", "Teoría"));
        time_table_1.add(new Sesion (14, 2, 16, "CAL-I", "Teoría"));
        time_table_1.add(new Sesion (16, 2, 16, "FMT", "Teoría"));
        time_table_1.add(new Sesion (18, 2, 16, "ALG", "Teoría"));

        //17
        time_table_2.add(new Sesion (10, 2, 17, "AO", "Teoría"));
        time_table_2.add(new Sesion (12, 2, 17, "EMP", "Teoría"));
        time_table_2.add(new Sesion (14, 2, 17, "CAL-I", "Teoría"));
        time_table_2.add(new Sesion (16, 2, 17, "FMT", "Teoría"));
        time_table_2.add(new Sesion (18, 2, 17, "ALG", "Teoría"));

        //18
        time_table_3.add(new Sesion (10, 2, 18, "AO", "Teoría"));
        time_table_3.add(new Sesion (12, 2, 18, "EMP", "Teoría"));
        time_table_3.add(new Sesion (14, 2, 18, "CAL-I", "Teoría"));
        time_table_3.add(new Sesion (16, 2, 18, "FMT", "Teoría"));
        time_table_3.add(new Sesion (18, 2, 18, "ALG", "Teoría"));

        AlgorithmOptions.setTime_table_1(time_table_1);
        AlgorithmOptions.setTime_table_2(time_table_2);
        AlgorithmOptions.setTime_table_3(time_table_3);


    }

    private void toCalendarOption(){
        Intent intent = new Intent(this, AlgorithmOptions.class);
        startActivity(intent);
        this.finish();
    }

    public ArrayList<Sesion> getPrueba() {
        return time_table_1;
    }

}

