package com.psi.calendar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExportToGC extends AppCompatActivity {

    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static GoogleAccountCredential credential = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_gc);
        context = getApplicationContext();
        scheduleEvents();

    }

    public static void setGoogleCredential(GoogleAccountCredential cred){
        credential = cred;
    }
    private void scheduleEvents() {

        GoogleAccountCredential credentials =credential;
        ArrayList<Event> events = new ArrayList<Event>();

        Event event = new Event()
                .setSummary("Google I/O 2020")
                .setLocation("800 Howard St., San Francisco, CA 94103")
                .setDescription("A chance to hear more about Google's developer products.");

        DateTime startDateTime = new DateTime("2017-01-20T09:00:00+01:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Madrid");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2017-01-20T11:00:00+01:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Madrid");
        event.setEnd(end);

        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("mestrecarting@gmail.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        //event.setId("qwertyuyui");
        events.add(event);


        Event event2 = new Event()
                .setSummary("Google I/O 2018")
                .setLocation("800 Howard St., San Francisco, CA 94103")
                .setDescription("A chance to hear more about Google's developer products.");

        startDateTime = new DateTime("2017-01-20T11:00:00+01:00");
        start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Madrid");
        event2.setStart(start);

        endDateTime = new DateTime("2017-01-20T11:30:00+01:00");
        end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Madrid");
        event2.setEnd(end);

        recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
        event2.setRecurrence(Arrays.asList(recurrence));


        attendees = new EventAttendee[] {
                new EventAttendee().setEmail("mestrecarting@gmail.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event2.setReminders(reminders);
        //event2.setId("asddfgh");
        events.add(event2);

        new ScheduleEventTask(credentials, events, calendarId).execute();

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
         * @param credential
         * @param events
         * @param calendarId
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
            ArrayList<Event> eventArrayList = new ArrayList<Event>();
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

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         *
         * @return List of Strings describing returned events.
         * @throws IOException
         */


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
                            ExportToGC.REQUEST_AUTHORIZATION);
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
                ExportToGC.this,
                connectionStatusCode,
                ExportToGC.REQUEST_GOOGLE_PLAY_SERVICES);
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
                    Toast.makeText(ExportToGC.this, "This app requires Google Play Services. Please install " +
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