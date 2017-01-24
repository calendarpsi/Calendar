package com.psi.calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SecondYearActivity extends AppCompatActivity implements Comparator {

    private int question1, question2, question4;
    private ArrayList<String> question3 = new ArrayList<>();
    private int [] question_secondYear = null;
    private ArrayList<Integer> secondYear_aux = new ArrayList<>();
    private ImageView imageView;

    //Variables from the third & fifth question
    private int aux5 = 0;
    private boolean check_all = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_year);
        question1 = Integer.parseInt(getIntent().getExtras().getString("question1"));
        question2 = Integer.parseInt(getIntent().getExtras().getString("question2"));
        question3 = (ArrayList<String>) getIntent().getSerializableExtra("question3");
        question4 = Integer.parseInt(getIntent().getExtras().getString("question4"));
        Log.d("question1","Second_year_Activity "+String.valueOf(question1));
        Log.d("question2","Second_year_Activity "+String.valueOf(question2));
        Log.d("question3","Second_year_Activity "+String.valueOf(question3));
        Log.d("question4","Second_year_Activity "+String.valueOf(question4));
        imageView = (ImageView)findViewById(R.id.firstYear_toAlgorithm);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAlgorithm(view);
            }
        });
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

    //***********************************************************************************
    //          Page 5 - Checkboxes - "Select subjects from the 1st semester"
    //
    //***********************************************************************************
    public void onRadioButtonClicked5(View view) {
        secondYear_aux.clear();
        aux5 = 0;

        //CD
        CheckBox  checkBox =(CheckBox) findViewById(R.id.checkBox7);
        if (checkBox.isChecked()){
            secondYear_aux.add(6);
            Log.d("CD", "onRadioButtonClicked5_secondYear: ");
        }
        else
            aux5 --;

        //PII

        checkBox =(CheckBox) findViewById(R.id.checkBox8);
        if (checkBox.isChecked()){
            secondYear_aux.add(8);
            Log.d("PII", "onRadioButtonClicked5_secondYear: ");
        }
        else
            aux5 --;

        //PDS

        checkBox =(CheckBox) findViewById(R.id.checkBox9);
        if (checkBox.isChecked()){
            secondYear_aux.add(10);
            Log.d("PDS", "onRadioButtonClicked5_secondYear: ");
        }
        else
            aux5 --;

        //TEM

        checkBox =(CheckBox) findViewById(R.id.checkBox10);
        if (checkBox.isChecked()){
            secondYear_aux.add(9);
            Log.d("TEM", "onRadioButtonClicked5_secondYear: ");
        }
        else
            aux5 --;

        //FE

        checkBox =(CheckBox) findViewById(R.id.checkBox11);
        if (checkBox.isChecked()){
            secondYear_aux.add(7);
            Log.d("FE", "onRadioButtonClicked5_secondYear: ");
        }
        else
            aux5 --;

        Log.d("Array", "onRadioButtonClicked5_secondYear: "+ secondYear_aux);
    }

    /**
     * Next Activity
     * @param view
     */
    public void toAlgorithm(View view) {
        if (secondYear_aux.isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "Please, choose at least one subject.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            Algorithm a = new Algorithm();
            a.setQuestion2(question2);
            a.setQuestion3(question3);
            a.setQuestion4(question4);

            //This piece of code changes between an Array of integers to an Integer array
            //It would be better to use the Wrapper class Integer, but we need int information.
            question_secondYear = new int[secondYear_aux.size()];
            for (int i = 0; i < secondYear_aux.size(); i++) {
                question_secondYear[i] = secondYear_aux.get(i);
            }
            Arrays.sort(question_secondYear);
            a.setQuestion5(question_secondYear);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("The algorithm is about to start...");
            alertDialogBuilder
                    .setMessage("Do you want to continue?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(SecondYearActivity.this, Algorithm.class);
                            startActivity(intent);
                            SecondYearActivity.this.finish();
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
}
