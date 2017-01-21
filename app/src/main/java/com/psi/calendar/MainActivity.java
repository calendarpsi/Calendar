package com.psi.calendar;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout importCalendar;
    private LinearLayout openGoogleCalendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        importCalendar = (LinearLayout)findViewById(R.id.toImportCalendar);
        openGoogleCalendar = (LinearLayout)findViewById(R.id.openGoogleCalendar);
        importCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toImportCalendar();
            }
        });
        openGoogleCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toGoogleCalendar();
            }
        });

    }
    //Activity ImportCalendar
    public void toImportCalendar(){
        Intent intent = new Intent(this, ImportCalendar.class);
        startActivity(intent);
    }
    //Activity Open Google Calendar
    public void toGoogleCalendar(){
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 2);
        long time = cal.getTime().getTime();
        Uri.Builder builder =
                CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        builder.appendPath(Long.toString(time));
        Intent intent =
                new Intent(Intent.ACTION_VIEW, builder.build());
        startActivity(intent);
    }
}
