package com.psi.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Questions extends AppCompatActivity implements Comparator {
    private ViewPager view1;
    private LinearLayout pagina1;
    private LinearLayout pagina2;
    private LinearLayout pagina3;
    private LinearLayout pagina4;
    private LinearLayout pagina5;
    private int countPage = 0;
    private int question1, question2, question4;
    private ArrayList<String> question3 = new ArrayList<String>();
    private int []question5 = null;
    private ArrayList<Integer> question5_aux = new ArrayList();


    //Variables from the third & fifth question
    private int aux = 0, aux5 = 0;
    private boolean everyday = false, check_all = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view1 = (ViewPager) findViewById(R.id.view);
        view1.setAdapter(new PageAdapter());
    }

    @Override
    public int compare(Object o, Object t1) {
        if((int)o < (int)t1)
            return 1;
        else if ((int)o > (int)t1)
            return -1;
        else
            return 0;
    }

    public class PageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View paginaactual = null;
            switch (position) {
                case 0:
                    if (pagina1 == null) {
                        pagina1 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina1, null);
                    }
                    paginaactual = pagina1;
                    break;
                case 1:
                    if (pagina2 == null) {
                        pagina2 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina2, null);
                    }
                    paginaactual = pagina2;
                    break;
                case 2:
                    if (pagina3 == null) {
                        pagina3 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina3, null);
                    }
                    paginaactual = pagina3;
                    break;
                case 3:
                    if (pagina4 == null) {
                        pagina4 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina4, null);
                    }
                    paginaactual = pagina4;
                    break;

                case 4:
                    if (pagina5 == null) {
                        pagina5 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina5, null);
                    }
                    paginaactual = pagina5;
                    break;

            }
            ViewPager vp = (ViewPager) collection;
            vp.addView(paginaactual, 0);
            return paginaactual;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(View collection, int position, Object view) {
            ((ViewPager) collection).removeView((View) view);
        }
    }

    //***********************************************************************************
    //              Page 1 - Checkboxes - "Free time between classes?"
    //
    //***********************************************************************************
    public void onRadioButtonClicked1(View view) {
        switch (view.getId())
        {
            //case 'No'
            case R.id.radioButton4:
                question1 = 0;
                Log.d("NO", "onRadioButtonClicked1: ");
                break;

            //Case '1h'
            case R.id.radioButton3:
                question1 = 1;
                Log.d("1h", "onRadioButtonClicked1: ");
                break;

            //case '2h'
            case R.id.radioButton2:
                question1 = 2;
                Log.d("2h", "onRadioButtonClicked1: ");
                break;

            //Case '3h'
            case R.id.radioButton1:
                question1 = 3;
                Log.d("3h", "onRadioButtonClicked1: ");
                break;

            //Case '4h'
            case R.id.radioButton:
                question1 = 4;
                Log.d("4h", "onRadioButtonClicked1: ");
                break;
        }

    }

    //***********************************************************************************
    //          Page 2 - Checkboxes - When do you prefer taking classes?
    //
    //***********************************************************************************
    public void onRadioButtonClicked2(View view) {
        switch (view.getId()) {
            //case 'Mornings'
            case R.id.radioButton4:
                question2 = 1;
                Log.d("Morning", "onRadioButtonClicked2: ");
                break;

            //Case '+ mornin - after'
            case R.id.radioButton3:
                question2 = 2;
                Log.d("Mornings rather after", "onRadioButtonClicked2: ");
                break;

            //case '+ after - mornin'
            case R.id.radioButton2:
                question2 = 3;
                Log.d("Afternoon rather morn", "onRadioButtonClicked2: ");
                break;

            //Case 'Afternoons'
            case R.id.radioButton:
                question2 = 4;
                Log.d("Afternoons", "onRadioButtonClicked2: ");
                break;
        }

    }

    //***********************************************************************************
    //          Page 3 - Checkboxes - When do you prefer having a day off?
    //
    //***********************************************************************************
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        final int max = 2;
        question3 = new ArrayList<String>();

        //Checked one day of the week - PAGE 3
        if (view.getId() == R.id.checkBox2
                || view.getId() == R.id.checkBox3
                || view.getId() == R.id.checkBox4 || view.getId() == R.id.checkBox5
                || view.getId() == R.id.checkBox6) {
            if (checked)
                aux++;
            else
                aux--;
        }
        //Checked Everyday - PAG 3
        else if (view.getId() == R.id.checkBox1) {
            if (checked) {
                everyday = true;
                aux++;
            } else {
                aux--;
                everyday = false;
            }
        }
        //******************************************************************************
        //  If everyday option is checked (everyday = true) or if more than one day
        //          has been checked (aux > 2), and the user tries to check another one
        //          we revert the last check input.
        //
        //  Function toogle() = toogles the last input made by the user.
        //*******************************************************************************
        if (aux > max || (everyday && (aux > 1))) {
            ((CheckBox) view).toggle();
            Toast.makeText(this, "You can select two days of the week, or 'Every day' instead. "
                    , Toast.LENGTH_LONG).show();
            aux--;
        }

        //*******************************************************************************
        //          This piece of code adds to an array of strings
        //                the name of the checkbox clicked.
        //*******************************************************************************
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
        if (checkBox.isChecked())
            question3.add("ev");
        checkBox =(CheckBox) findViewById(R.id.checkBox2);
        if (checkBox.isChecked())
            question3.add("lu");
        checkBox =(CheckBox) findViewById(R.id.checkBox3);
        if (checkBox.isChecked())
            question3.add("ma");
        checkBox =(CheckBox) findViewById(R.id.checkBox4);
        if (checkBox.isChecked())
            question3.add("mi");
        checkBox =(CheckBox) findViewById(R.id.checkBox5);
        if (checkBox.isChecked())
            question3.add("ju");
        checkBox =(CheckBox) findViewById(R.id.checkBox6);
        if (checkBox.isChecked())
            question3.add("vi");
        Log.d("Checkboxes question 3: ", "onCheckboxClicked: "+question3);
    }

    //***********************************************************************************
    //          Page 4 - Checkboxes - "When do you want to have lunch?"
    //
    //***********************************************************************************
    public void onRadioButtonClicked4(View view) {
        switch (view.getId())
        {
            //case '13'
            case R.id.radioButton1:
                question4 = 13;
                Log.d("13:00", "onRadioButtonClicked4: ");
                break;

            //Case '14:00'
            case R.id.radioButton2:
                question4 = 13;
                Log.d("14:00", "onRadioButtonClicked4: ");
                break;
        }
    }

    //***********************************************************************************
    //          Page 5 - Checkboxes - "Select subjects from the 1st semester"
    //
    //***********************************************************************************
    public void onRadioButtonClicked5(View view) {
        question5_aux.clear();
        aux5 = 0;

        //ALG

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
        if (checkBox.isChecked()) {
            question5_aux.add(3);
            Log.d("ALG", "onRadioButtonClicked5: ");
        }
        else
            aux5 --;

        //AO

        checkBox =(CheckBox) findViewById(R.id.checkBox2);
        if (checkBox.isChecked()){
            question5_aux.add(0);
            Log.d("AO", "onRadioButtonClicked5: ");
        }
        else
            aux5 --;

        //EMP

        checkBox =(CheckBox) findViewById(R.id.checkBox3);
        if (checkBox.isChecked()){
            question5_aux.add(4);
            Log.d("EMP", "onRadioButtonClicked5: ");
        }
        else
            aux5 --;

        //FMT

        checkBox =(CheckBox) findViewById(R.id.checkBox4);
        if (checkBox.isChecked()){
            question5_aux.add(2);
            Log.d("FMT", "onRadioButtonClicked5: ");
        }
        else
            aux5 --;

        //CAL1

        checkBox =(CheckBox) findViewById(R.id.checkBox5);
        if (checkBox.isChecked()){
            question5_aux.add(1);
            Log.d("CAL1", "onRadioButtonClicked5: ");
        }
        else
            aux5 --;

        //ALL
        /*
        checkBox =(CheckBox) findViewById(R.id.checkBox6);
        if (checkBox.isChecked()) {
            check_all = true;
            question5.add("ALL");
            Log.d("CHECK ALL!", "onRadioButtonClicked5: ");
        }
        else
            check_all = false;
        */

        //CD
        /*
        checkBox =(CheckBox) findViewById(R.id.checkBox7);
        if (checkBox.isChecked()){
            question5_aux.add(5);
            Log.d("CD", "onRadioButtonClicked5: ");
        }
        else
            aux5 --;

        //PII

        checkBox =(CheckBox) findViewById(R.id.checkBox8);
        if (checkBox.isChecked()){
            question5_aux.add(7);
            Log.d("PII", "onRadioButtonClicked5: ");
        }
        else
            aux5 --;

        //PDS

        checkBox =(CheckBox) findViewById(R.id.checkBox9);
        if (checkBox.isChecked()){
            question5_aux.add(9);
            Log.d("PDS", "onRadioButtonClicked5: ");
        }
        else
            aux5 --;

        //TEM

        checkBox =(CheckBox) findViewById(R.id.checkBox10);
        if (checkBox.isChecked()){
            question5_aux.add(8);
            Log.d("TEM", "onRadioButtonClicked5: ");
        }
        else
            aux5 --;

        //FE

        checkBox =(CheckBox) findViewById(R.id.checkBox11);
        if (checkBox.isChecked()){
            question5_aux.add(6);
            Log.d("FE", "onRadioButtonClicked5: ");
        }
        else
            aux5 --;


        //Forbidds checking one of the subjects and "Check all" at the same time.
        if (aux5 > -4) {
            ((CheckBox) view).toggle();
            Toast.makeText(this, "You can select several subjects, or ALL instead. "
                    , Toast.LENGTH_LONG).show();
            question5_aux.remove(0);

        }
        */

        Log.d("Array", "onRadioButtonClicked5: "+question5_aux);
    }

    /**
     * Next Activity
     * @param view
     */
    public void onButtonClicked(View view){
        Algorithm a  = new Algorithm();
        a.setQuestion2(question2);
        a.setQuestion3(question3);
        a.setQuestion4(question4);

        //This piece of code changes between an Array of integers to an Integer array
        //It would be better to use the Wrapper class Integer, but we need int information.
        question5 = new int [question5_aux.size()];
        for (int i = 0; i < question5_aux.size() ; i++) {
            question5[i] = question5_aux.get(i);
        }
        Arrays.sort(question5);
        a.setQuestion5(question5);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("The algorithm is about to start...");
        alertDialogBuilder
                .setMessage("Do you want to continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Questions.this,Algorithm.class);
                        startActivity(intent);
                        Questions.this.finish();
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
}