package com.psi.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.google.api.services.calendar.CalendarScopes;
import java.util.ArrayList;
import java.util.List;

public class CalendarOption extends AppCompatActivity {
    private List<Clase> Clases;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR};
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_option);
        context = getApplicationContext();
        rv=(RecyclerView)findViewById(R.id.rv);
        fab=(FloatingActionButton)findViewById(R.id.fabOption1);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                continuar();
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        Clases = new ArrayList<>();
        Clases.add(new Clase("Monday", "","#FFFFFF",34));
        Clases.add(new Clase("AO", "10:00 - 12:00","#009688",24));
        Clases.add(new Clase("Fisica", "12:00 - 13:00","#009688",24));
        Clases.add(new Clase("CalI", "15:00 - 16:00","#009688",24));

        Clases.add(new Clase("Tuesday", "","#FFFFFF",34));
        Clases.add(new Clase("AO", "10:00 - 12:00","#009688",24));
        Clases.add(new Clase("Fisica", "12:00 - 13:00","#009688",24));
        Clases.add(new Clase("CalI", "15:00 - 16:00","#009688",24));

        Clases.add(new Clase("Wednesday", "","#FFFFFF",34));
        Clases.add(new Clase("AO", "10:00 - 12:00","#009688",24));
        Clases.add(new Clase("Fisica", "12:00 - 13:00","#009688",34));
        Clases.add(new Clase("CalI", "15:00 - 16:00","#009688",24));

        Clases.add(new Clase("Monday", "","#FFFFFF",34));
        Clases.add(new Clase("AO", "10:00 - 12:00","#009688",24));
        Clases.add(new Clase("Fisica", "12:00 - 13:00","#009688",24));
        Clases.add(new Clase("CalI", "15:00 - 16:00","#009688",24));

        Clases.add(new Clase("Tuesday", "","#FFFFFF",34));
        Clases.add(new Clase("AO", "10:00 - 12:00","#009688",24));
        Clases.add(new Clase("Fisica", "12:00 - 13:00","#009688",24));
        Clases.add(new Clase("CalI", "15:00 - 16:00","#009688",24));

        Clases.add(new Clase("Wednesday", "","#FFFFFF",34));
        Clases.add(new Clase("AO", "10:00 - 12:00","#009688",24));
        Clases.add(new Clase("Fisica", "12:00 - 13:00","#009688",34));
        Clases.add(new Clase("Manuel Veiga a:", "15:00 - 16:00","#009688",24));


    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(Clases);
        rv.setAdapter(adapter);
    }

    public void continuar(){
        Intent intent = new Intent(this,ExportToGC.class);
        startActivity(intent);

    }

}
