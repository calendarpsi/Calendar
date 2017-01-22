package com.psi.calendar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class AlgorithmOptions extends AppCompatActivity {

    private ImageView firstOption;
    private ImageView secondOption;
    private ImageView thirdOption;



    private static ArrayList<Sesion> time_table_1 = new ArrayList<Sesion>();
    private static ArrayList<Sesion> time_table_2 = new ArrayList<Sesion>();
    private static ArrayList<Sesion> time_table_3 = new ArrayList<Sesion>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_options);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        firstOption = (ImageView)findViewById(R.id.firstOption);
        secondOption = (ImageView)findViewById(R.id.secondOption);
        thirdOption = (ImageView)findViewById(R.id.thirdOption);
        firstOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstOption();
            }
        });
        secondOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondOption();
            }
        });
        thirdOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdOption();
            }
        });
    }

    /**
     * Intent for First Option
     */
    private void firstOption(){
        Intent intent = new Intent(this,CalendarOption.class);
        Bundle info = new Bundle();

        info.putSerializable("time_table_1", time_table_1);
        intent.putExtras(info);
        intent.putExtra("key","1");
        startActivity(intent);
    }

    /**
     * Intent for Second Option
     */
    private void secondOption(){
        Intent intent = new Intent(this,CalendarOption.class);
        Bundle info = new Bundle();

        info.putSerializable("time_table_2", time_table_2);
        intent.putExtras(info);
        intent.putExtra("key","2");
        startActivity(intent);
    }

    /**
     * Intent for Third Option
     */
    private void thirdOption(){
        Intent intent = new Intent(this,CalendarOption.class);
        Bundle info = new Bundle();

        info.putSerializable("time_table_3", time_table_3);
        intent.putExtras(info);
        intent.putExtra("key","3");
        startActivity(intent);
    }

    public static void setTime_table_1(ArrayList<Sesion> time_table_1) {
        AlgorithmOptions.time_table_1 = time_table_1;
    }

    public static void setTime_table_2(ArrayList<Sesion> time_table_2) {
        AlgorithmOptions.time_table_2 = time_table_2;
    }

    public static void setTime_table_3(ArrayList<Sesion> time_table_3) {
        AlgorithmOptions.time_table_3 = time_table_3;
    }
}
