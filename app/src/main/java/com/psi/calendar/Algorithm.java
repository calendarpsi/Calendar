package com.psi.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Algorithm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        Button nextActivity = (Button)findViewById(R.id.btnToCalendarOption);
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCalendarOption();
            }
        });
    }

    private void toCalendarOption(){
        Intent intent = new Intent(this, AlgorithmOptions.class);
        startActivity(intent);
        this.finish();
    }
}
