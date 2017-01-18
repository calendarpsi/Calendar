package com.psi.calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.api.services.calendar.CalendarScopes;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExportToGC extends AppCompatActivity {
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_gc);
        Button button = (Button) findViewById(R.id.btnEnviarGC);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    addEvent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void addEvent() throws IOException {

    }

}