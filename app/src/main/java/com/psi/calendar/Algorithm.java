package com.psi.calendar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Algorithm extends AppCompatActivity {



    private static List<String> raw_input = new ArrayList<>();
    private ArrayList<Sesion> time_table_1 = new ArrayList<>();
    private ArrayList<Sesion> time_table_2 = new ArrayList<>();
    private ArrayList<Sesion> time_table_3 = new ArrayList<>();
    private static Horario elHorario;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button nextActivity = (Button)findViewById(R.id.btnToCalendarOption);
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCalendarOption();
            }
        });


        //CODIGO MAIN:
        elHorario = new Horario();
        try {
            elHorario.main(raw_input);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("INput read! Raw", "onCreate: ");
        }
        //System.out.println(elHorario.getCantGrupos());

        //Variables for calculating fitness/ variables for measuring the quality of each timetable generated
        int mat_disponibilidad[][];
        int sum_horarioDeseado = 0, sum_horarioGenerado = 0, porcentaje_ocupado = 0;
        //Variables for the Algorithm execution
        int generationCount = 0, vueltas=50, poblacionSize=10;//Set up parameters //TODO valorar que desde la aplicación se pueda ajustar
        //TODO Integración:usar constructor metiendo los parámetros que vengan de las elecciones del usuario: dias_laborable, h_inicio, h_fin, materias[],...
        //elHorario = new Horario();

        //TODO Parte delicada por la decisión de cómo calcularlo. Está en proceso
        //mat_disponibilidad = elHorario.crearMat_disp();//TODO: Cambiar nombre a crearMat_vacia. Esta linea y la siguiente pueden ser solo una y borrarmos la variable mat_dispopinibilidad
        mat_disponibilidad = elHorario.crearMat_vacia();
        elHorario.pregunta2(1);
        ArrayList<String> viernes = new ArrayList<>(1);
        viernes.add("vi");
        elHorario.pregunta3(viernes);
        elHorario.pregunta4(13);
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


    }

    private void toCalendarOption(){
        Intent intent = new Intent(this, AlgorithmOptions.class);
        startActivity(intent);
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

}

