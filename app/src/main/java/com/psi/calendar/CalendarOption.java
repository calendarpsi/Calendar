package com.psi.calendar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalendarOption extends AppCompatActivity {
    private List<Clase> Clases;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private Context context;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static GoogleAccountCredential credential = null;
    private ArrayList<Sesion> monday = new ArrayList<>(),  tuesday = new ArrayList<>()
            , wednesday = new ArrayList<>(), thursday = new ArrayList<>(), friday = new ArrayList<>();


    private ArrayList<Sesion> timetable = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_option);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Code container
        String key = getIntent().getExtras().getString("key");
        switch (key){
            case "1":
                timetable = (ArrayList<Sesion>) getIntent().getSerializableExtra("time_table_1");
                Log.d("Key: 1", "onCreate: "+timetable);
                break;
            case "2":
                timetable = (ArrayList<Sesion>) getIntent().getSerializableExtra("time_table_2");
                Log.d("Key: 2", "onCreate: "+timetable);
                break;
            case "3":
                timetable = (ArrayList<Sesion>) getIntent().getSerializableExtra("time_table_3");
                Log.d("Key: 3", "onCreate: "+timetable);
                break;
            default:
                Log.e("Error switch.", "onCreate_Calendar option: FATAL ERROR! ");
                break;
        }
        //Inicialize
        Collections.sort(timetable);
        organizeCalendar();


        context = getApplicationContext();
        rv=(RecyclerView)findViewById(R.id.rv);
        fab=(FloatingActionButton)findViewById(R.id.fabOption1);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                exportCalendar();
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        initializeData();
        initializeAdapter();
    }

    private void organizeCalendar() {
        for (int i = 0; i < timetable.size(); i++) {

            switch (timetable.get(i).getDia()){
                //Antes ponía 16
                case 1:
                    monday.add(timetable.get(i));
                    break;

                case 2:
                    tuesday.add(timetable.get(i));
                    break;

                case 3:
                    wednesday.add(timetable.get(i));
                    break;

                case 4:
                    thursday.add(timetable.get(i));
                    break;

                case 5:
                    friday.add(timetable.get(i));
                    break;

                default:
                    break;
            }
        }
    }

    private void initializeData(){

        Clases = new ArrayList<>();

        Clases.add(new Clase("Monday", "","#FFFFFF",34));
        for (int i = 0; i < monday.size(); i++) {
            int horaInicio = monday.get(i).getHora();
            int horaFin = monday.get(i).getHora()+monday.get(i).getDuracion();
            Clases.add(new Clase(
                    monday.get(i).getNombre()+"-"+monday.get(i).getAula(),
                    horaInicio+" - " +horaFin,
                    "#009688",24));
        }

        Clases.add(new Clase("Tuesday", "","#FFFFFF",34));
        for (int i = 0; i < tuesday.size(); i++) {
            int horaInicio = tuesday.get(i).getHora();
            int horaFin = tuesday.get(i).getHora() + tuesday.get(i).getDuracion();
            Clases.add(new Clase(
                    tuesday.get(i).getNombre()+"-"+tuesday.get(i).getAula(),
                    horaInicio + " - " + horaFin,
                    "#009688", 24));
        }

        Clases.add(new Clase("Wednesday", "","#FFFFFF",34));
        for (int i = 0; i < wednesday.size(); i++) {
            int horaInicio = wednesday.get(i).getHora();
            int horaFin = wednesday.get(i).getHora() + wednesday.get(i).getDuracion();
            Clases.add(new Clase(
                    wednesday.get(i).getNombre()+"-"+wednesday.get(i).getAula(),
                    horaInicio + " - " + horaFin,
                    "#009688", 24));
        }

        Clases.add(new Clase("Thursday", "","#FFFFFF",34));
        for (int i = 0; i < thursday.size(); i++) {
            int horaInicio = thursday.get(i).getHora();
            int horaFin = thursday.get(i).getHora() + thursday.get(i).getDuracion();
            Clases.add(new Clase(
                    thursday.get(i).getNombre()+"-"+thursday.get(i).getAula(),
                    horaInicio + " - " + horaFin,
                    "#009688", 24));
        }

        Clases.add(new Clase("Friday", "","#FFFFFF",34));
        for (int i = 0; i < friday.size(); i++) {
            int horaInicio = friday.get(i).getHora();
            int horaFin = friday.get(i).getHora() + friday.get(i).getDuracion();
            Clases.add(new Clase(
                    friday.get(i).getNombre()+"-"+friday.get(i).getAula(),
                    horaInicio + " - " + horaFin,
                    "#009688", 24));
        }



    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(Clases);
        rv.setAdapter(adapter);
    }

    public void exportCalendar(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("The calendar will be uploaded");
        alertDialogBuilder
                .setMessage("Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scheduleEvents();
                        Intent intent = new Intent(CalendarOption.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void setGoogleCredential(GoogleAccountCredential cred){
        credential = cred;
    }
    private void scheduleEvents() {

        GoogleAccountCredential credentials =credential;
        ArrayList<Event> events = new ArrayList<>();
        for (int i = 0; i < monday.size() ; i++) {
            Sesion aux = monday.get(i);
            Event event = new Event()
                    .setSummary(aux.getNombre()+" "+aux.getAula())
                    .setLocation("Escola de Enxeñaría de Telecomunicación")
                    .setDescription("A chance to learn technology in deep.");

            DateTime startDateTime;
            if(aux.getHora() <= 9)
               startDateTime  = new DateTime("2017-01-16T0" +aux.getHora()+":00:00+01:00");
            else
                startDateTime  = new DateTime("2017-01-16T" +aux.getHora()+":00:00+01:00");

            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Europe/Madrid");
            event.setStart(start);

            DateTime endDateTime = new DateTime("2017-01-16T" +(aux.getHora()+aux.getDuracion())+":00:00+01:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Europe/Madrid");
            event.setEnd(end);

            String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;COUNT=20"};
            event.setRecurrence(Arrays.asList(recurrence));

            EventReminder[] reminderOverrides = new EventReminder[] {
                    new EventReminder().setMethod("email").setMinutes(24 * 60),
                    new EventReminder().setMethod("popup").setMinutes(10),
            };
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(Arrays.asList(reminderOverrides));
            event.setReminders(reminders);
            events.add(event);
        }
        for (int i = 0; i < tuesday.size() ; i++) {
            Sesion aux = tuesday.get(i);
            Event event = new Event()
                    .setSummary(aux.getNombre()+" "+aux.getAula())
                    .setLocation("Escola de Enxeñaría de Telecomunicación")
                    .setDescription("A chance to learn technology in deep.");

            DateTime startDateTime;
            if(aux.getHora() <= 9)
                startDateTime  = new DateTime("2017-01-17T0" +aux.getHora()+":00:00+01:00");
            else
                startDateTime  = new DateTime("2017-01-17T" +aux.getHora()+":00:00+01:00");

            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Europe/Madrid");
            event.setStart(start);

            DateTime endDateTime = new DateTime("2017-01-17T" +(aux.getHora()+aux.getDuracion())+":00:00+01:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Europe/Madrid");
            event.setEnd(end);

            String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;COUNT=20"};
            event.setRecurrence(Arrays.asList(recurrence));

            EventReminder[] reminderOverrides = new EventReminder[] {
                    new EventReminder().setMethod("email").setMinutes(24 * 60),
                    new EventReminder().setMethod("popup").setMinutes(10),
            };
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(Arrays.asList(reminderOverrides));
            event.setReminders(reminders);
            events.add(event);
        }
        for (int i = 0; i <wednesday.size() ; i++) {
            Sesion aux = wednesday.get(i);
            Event event = new Event()
                    .setSummary(aux.getNombre()+" "+aux.getAula())
                    .setLocation("Escola de Enxeñaría de Telecomunicación")
                    .setDescription("A chance to learn technology in deep.");

            DateTime startDateTime;
            if(aux.getHora() <= 9)
                startDateTime  = new DateTime("2017-01-18T0" +aux.getHora()+":00:00+01:00");
            else
                startDateTime  = new DateTime("2017-01-18T" +aux.getHora()+":00:00+01:00");

            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Europe/Madrid");
            event.setStart(start);

            DateTime endDateTime = new DateTime("2017-01-18T" +(aux.getHora()+aux.getDuracion())+":00:00+01:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Europe/Madrid");
            event.setEnd(end);

            String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;COUNT=20"};
            event.setRecurrence(Arrays.asList(recurrence));

            EventReminder[] reminderOverrides = new EventReminder[] {
                    new EventReminder().setMethod("email").setMinutes(24 * 60),
                    new EventReminder().setMethod("popup").setMinutes(10),
            };
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(Arrays.asList(reminderOverrides));
            event.setReminders(reminders);
            events.add(event);
        }
        for (int i = 0; i < thursday.size() ; i++) {
            Sesion aux = thursday.get(i);
            Event event = new Event()
                    .setSummary(aux.getNombre()+" "+aux.getAula())
                    .setLocation("Escola de Enxeñaría de Telecomunicación")
                    .setDescription("A chance to learn technology in deep.");

            DateTime startDateTime;
            if(aux.getHora() <= 9)
                startDateTime  = new DateTime("2017-01-19T0" +aux.getHora()+":00:00+01:00");
            else
                startDateTime  = new DateTime("2017-01-19T" +aux.getHora()+":00:00+01:00");

            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Europe/Madrid");
            event.setStart(start);

            DateTime endDateTime = new DateTime("2017-01-19T" +(aux.getHora()+aux.getDuracion())+":00:00+01:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Europe/Madrid");
            event.setEnd(end);

            String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;COUNT=20"};
            event.setRecurrence(Arrays.asList(recurrence));

            EventReminder[] reminderOverrides = new EventReminder[] {
                    new EventReminder().setMethod("email").setMinutes(24 * 60),
                    new EventReminder().setMethod("popup").setMinutes(10),
            };
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(Arrays.asList(reminderOverrides));
            event.setReminders(reminders);
            events.add(event);
        }
        for (int i = 0; i < friday.size() ; i++) {
            Sesion aux = friday.get(i);
            Event event = new Event()
                    .setSummary(aux.getNombre()+" "+aux.getAula())
                    .setLocation("Escola de Enxeñaría de Telecomunicación")
                    .setDescription("A chance to learn technology in deep.");

            DateTime startDateTime;
            if(aux.getHora() <= 9)
                startDateTime  = new DateTime("2017-01-20T0" +aux.getHora()+":00:00+01:00");
            else
                startDateTime  = new DateTime("2017-01-20T" +aux.getHora()+":00:00+01:00");

            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Europe/Madrid");
            event.setStart(start);

            DateTime endDateTime = new DateTime("2017-01-20T" +(aux.getHora()+aux.getDuracion())+":00:00+01:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Europe/Madrid");
            event.setEnd(end);

            String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;COUNT=20"};
            event.setRecurrence(Arrays.asList(recurrence));

            EventReminder[] reminderOverrides = new EventReminder[] {
                    new EventReminder().setMethod("email").setMinutes(24 * 60),
                    new EventReminder().setMethod("popup").setMinutes(10),
            };
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(Arrays.asList(reminderOverrides));
            event.setReminders(reminders);
            events.add(event);
        }
        new CalendarOption.ScheduleEventTask(credentials, events, "primary").execute();

    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    /***
     * This class exports a group of events to Calendar.
     *
     */
    private class ScheduleEventTask extends AsyncTask<Void, Void, ArrayList<Event>> {

        private final String calendarId;
        private ArrayList<Event> events;
        private com.google.api.services.calendar.Calendar service = null;
        private Exception mLastError;

        /***
         * @param credential User accounts credentials
         * @param events Events to upload
         * @param calendarId Name of the calendar
         */
        public ScheduleEventTask(GoogleAccountCredential credential, ArrayList<Event> events, String calendarId) {

            this.calendarId = calendarId;
            this.events = events;

            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            service = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Calendar")
                    .build();


        }

        @Override
        protected ArrayList<Event> doInBackground(Void... voids) {
            ArrayList<Event> eventArrayList = new ArrayList<>();
            try {
                Log.d("AsyncTask", "doInBackground: jsldajf");
                for (int i = 0; i < events.size(); i++) {
                    Event event = service.events().insert(calendarId, events.get(i)).execute();

                    Log.d("doInBackground", "Html event:  "+event.getHtmlLink());
                    eventArrayList.add(event);
                }
            } catch (IOException e) {
                mLastError = e;
                cancel(true);
            }
            return eventArrayList;
        }
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(ArrayList<Event> events) {
            super.onPostExecute(events);
            // TODO: 21/09/16

            Log.d("onPostExecute", "All tasks scheduled. ");
        }

        @Override
        protected void onCancelled() {
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            CalendarOption.REQUEST_AUTHORIZATION);
                } else {
                    Log.d("onCancelled", "onCancelled: "+ mLastError.getMessage());
                }
            } else {
                Log.d("onCancelled", "Request cancelled. ");
            }
        }
    }
    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                CalendarOption.this,
                connectionStatusCode,
                CalendarOption.REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }
    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(CalendarOption.this, "This app requires Google Play Services. Please install " +
                            "Google Play Services on your device and relaunch this app.", Toast.LENGTH_SHORT).show();
                } else {
                    scheduleEvents();
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    scheduleEvents();
                }
                break;
        }
    }

}
