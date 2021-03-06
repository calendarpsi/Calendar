package com.psi.calendar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout importCalendar;
    private LinearLayout openGoogleCalendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
