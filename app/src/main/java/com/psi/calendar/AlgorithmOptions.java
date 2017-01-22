package com.psi.calendar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AlgorithmOptions extends AppCompatActivity {

    private ImageView firstOption;
    private ImageView secondOption;
    private ImageView thirdOption;

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
        startActivity(intent);
    }

    /**
     * Intent for Second Option
     */
    private void secondOption(){
        Intent intent = new Intent(this,CalendarOption.class);
        startActivity(intent);
    }

    /**
     * Intent for Third Option
     */
    private void thirdOption(){
        Intent intent = new Intent(this,CalendarOption.class);
        startActivity(intent);
    }
}
