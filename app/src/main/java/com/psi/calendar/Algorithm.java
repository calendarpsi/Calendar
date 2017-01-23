package com.psi.calendar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Algorithm extends AppCompatActivity {



    private static List<String> raw_input = new ArrayList<>();
    private ArrayList<Sesion> time_table_1 = new ArrayList<>();
    private ArrayList<Sesion> time_table_2 = new ArrayList<>();
    private ArrayList<Sesion> time_table_3 = new ArrayList<>();
    private static Horario elHorario;
    ProgressDialog mProgress;
    private static int question2 = 0;
    private static ArrayList<String> question3 = null;
    private static int question4 = 0;
    private static int [] question5;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("The algorithm is working ...");
        mProgress.show();

        //ALGORITHM CODE ------------------------------------------------------------------------------->
        elHorario = new Horario();
        try {
            elHorario.main(raw_input);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("INput read ERROR! Raw", "onCreate: ");
        }
        elHorario.crearAsignaturasInteres(question5);
        //System.out.println(elHorario.getCantGrupos());

        //Variables for calculating fitness/ variables for measuring the quality of each timetable generated
        int mat_disponibilidad[][];
        int sum_horarioDeseado = 0, sum_horarioGenerado = 0, porcentaje_ocupado = 0;
        //Variables for the Algorithm execution
        int generationCount = 0, vueltas=50, poblacionSize=50;//Set up parameters //TODO valorar que desde la aplicación se pueda ajustar
        //TODO Integración:usar constructor metiendo los parámetros que vengan de las elecciones del usuario: dias_laborable, h_inicio, h_fin, materias[],...
        //elHorario = new Horario();

        //TODO Parte delicada por la decisión de cómo calcularlo. Está en proceso
        //mat_disponibilidad = elHorario.crearMat_disp();//TODO: Cambiar nombre a crearMat_vacia. Esta linea y la siguiente pueden ser solo una y borrarmos la variable mat_dispopinibilidad
        mat_disponibilidad = elHorario.crearMat_vacia();
        elHorario.pregunta2(question2);
        elHorario.pregunta3(question3);
        elHorario.pregunta4(question4);
        //System.out.println("Preguntas realizadas.");
        //System.out.println(elHorario.getCantGrupos());
        //System.out.println(elHorario.getCantGruposLowCPU());
        //elHorario.setMat_disponibilidad(mat_disponibilidad);//TODO cambiar. sería un get ya. Desde la app ya se habrá creado el horario...
        //elHorario.imprimirMat(mat_disponibilidad); //TODO: borrar en definitivo		//For fitness estimation. Puede que no deba ir aquí
        //sum_horarioDeseado = elHorario.sumarMat(mat_disponibilidad);//TODO: más adelante puede que no se necesite
        //fit_Ideal=sum_horarioDeseado*porcentaje_ocupado-sum_horarioGenerado;

        //ALGORITMO STARTS: the fist population is generated in a randomly way.
        PoblacionHorarios miPob = new PoblacionHorarios(poblacionSize, true);
        //Our population will be evolving until we reach an optimum solution
        while (vueltas>generationCount) {//It is important to set up "vueltas" correctly
            generationCount++;
            //System.out.println("Generation: " + generationCount + " Fittest: " + miPob.getFittest().getFitness());
            miPob = Algoritmo.evolvePopulation(miPob);//Next Generation: we evolve the population => save the fittest + crossover + mutation
        }
        //System.out.println("Solution found!");
        //System.out.println("Generation: " + generationCount);
        //System.out.println("Genes:");
        //System.out.println(miPob.getFittest());


        Log.e("Algorithm", "onCreate: "+raw_input);

        // EXPORT TO CALENDAR --------------------
        //int hora, int duracion, int dia, String nombre, String tipo
        //TODO cambiar estos objetos por las tres salidas del algoritmo: opcion1, opcion2, opcion3
        ArrayList<Sesion> export = elHorario.exportGen(miPob.getFittest().getGenoma());

        for (int i = 0; i < export.size(); i++) {
            time_table_1.add(export.get(i));
            Log.d("RESU", "onCreate: "+export.get(i));

        }
        AlgorithmOptions.setTime_table_1(time_table_1);
        //AlgorithmOptions.setTime_table_2(time_table_2);
        //AlgorithmOptions.setTime_table_3(time_table_3);
        mProgress.hide();
        toCalendarOption();


    }

    private void deleteSubjects(ArrayList<String> question5) {
        boolean alg=true, ao=true, emp=true, fmt=true, cal1=true;
        ArrayList<String> result = new ArrayList<>();
        boolean dont_touch = false;

        if(question5.contains("ALL")){
            Log.d("DELETE EVENTS", "deleteSubjects: NOTHING");
            dont_touch = true;
        }

        if (question5.contains("ALG"))
        {
            for(String o: raw_input){
                if(o.contains("ALG"))
                    result.add(o);
            }
        }
        if(question5.contains("AO")){
            for(String o: raw_input){
                if(o.contains("AO"))
                    result.add(o);
            }
        }
        if(question5.contains("EMP")){
            for(String o: raw_input){
                if(o.contains("EMP"))
                    result.add(o);
            }
        }
        if(question5.contains("FMT")){
            for(String o: raw_input){
                if(o.contains("FMT"))
                    result.add(o);
            }
        }
        if(question5.contains("CAL-I")){
            for(String o: raw_input){
                if(o.contains("CAL-I"))
                    result.add(o);
            }
        }
        if(!dont_touch) {
            raw_input.clear();
            for (String o : result) {
                raw_input.add(o);
            }
        }
    }

    private void toCalendarOption(){
        Intent intent = new Intent(this, AlgorithmOptions.class);
        startActivity(intent);
        if (mProgress != null) {
            if(mProgress.isShowing()) mProgress.dismiss();
            mProgress = null;
        }
        this.finish();
    }

    public ArrayList<Sesion> getPrueba() {
        return time_table_1;
    }
    public static void setInput(List<String> input) {
        Algorithm.raw_input = input;
    }
    public static Horario getElHorario() {
        return elHorario;
    }
    public static void setQuestion2(int question2) {
        Algorithm.question2 = question2;
    }

    public static void setQuestion3(ArrayList<String> question3) {
        Algorithm.question3 = question3;
    }

    public static void setQuestion4(int question4) {
        Algorithm.question4 = question4;
    }
    public static void setQuestion5(int []question5) {
        Algorithm.question5 = question5;
    }
}

