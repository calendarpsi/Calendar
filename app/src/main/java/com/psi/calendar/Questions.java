package com.psi.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Questions extends AppCompatActivity {
    private ViewPager view1;
    private LinearLayout pagina1;
    private LinearLayout pagina2;
    private LinearLayout pagina3;
    private LinearLayout pagina4;
    private LinearLayout pagina5;
    private LinearLayout pagina6;
    private LinearLayout pagina7;
    private Button btnContinuar;

    private int aux = 0;
    private boolean everyday = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        btnContinuar = (Button)findViewById(R.id.btnToAlgorithm);
        view1 = (ViewPager) findViewById(R.id.view);
        view1.setAdapter(new PageAdapter());
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continuar(view);
            }
        });
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
                        //pregunta1();
                    }
                    paginaactual = pagina1;

                    break;
                case 1:
                    if (pagina2 == null) {
                        pagina2 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina2, null);
                        //pregunta2();
                    }
                    paginaactual = pagina2;
                    break;
                case 2:
                    if (pagina3 == null) {
                        pagina3 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina3, null);
                        //pregunta3();
                    }
                    paginaactual = pagina3;
                    break;
                case 3:
                    if (pagina4 == null) {
                        pagina4 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina4, null);
                        //pregunta4();
                    }
                    paginaactual = pagina4;
                    break;

                case 4:
                    if (pagina5 == null) {
                        pagina5 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina5, null);
                        //pregunta5();
                        btnContinuar.setVisibility(View.VISIBLE);
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


    private void pregunta1() {
        //TODO rellenar codigo aqui pregunta 1
        Toast.makeText(this, "Pregunta 1", Toast.LENGTH_SHORT).show();
    }

    private void pregunta2() {
        //TODO rellenar codigo aqui pregunta 2
        Toast.makeText(this, "Pregunta 2", Toast.LENGTH_SHORT).show();

    }

    private void pregunta3() {
        //TODO rellenar codigo aqui pregunta 3
        Toast.makeText(this, "Pregunta 3", Toast.LENGTH_SHORT).show();


    }

    private void pregunta4() {
        //TODO rellenar codigo aqui pregunta 4
        Toast.makeText(this, "Pregunta 4", Toast.LENGTH_SHORT).show();


    }

    private void pregunta5() {
        //TODO linkar a la pregunta 6 o 7 dependiendo de la respuesta del usuario.
        Toast.makeText(this, "Pregunta 5", Toast.LENGTH_SHORT).show();


    }

    //***********************************************************************************
    //  Every time one checkbox is clicked in the 3rd question or in the 5th one,
    //         the program enters here and processes the input.
    //
    //***********************************************************************************
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        final int max = 2;

        //Checked one day of the week - PAGE 3
        if (view.getId() == R.id.checkBox2 || view.getId() == R.id.checkBox3
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
        //*******************************************************************************
        //
        //  If everyday option is checked (everyday = true) or if more than one day
        //          has been checked (aux > 2), and the user tries to check another one
        //          we revert the last check input.
        //
        //  Function toogle() = toogles the last input made by the user.
        //
        //*******************************************************************************
        if (aux > max || (everyday && (aux > 1))) {
            ((CheckBox) view).toggle();
            Toast.makeText(this, "You can select two days of the week, or 'Every day' instead. "
                    , Toast.LENGTH_LONG).show();
            aux--;
        }
    }
    //Page 1 - Checkboxes - "Free time between classes?"
    public void onRadioButtonClicked1(View view) {
        switch (view.getId())
        {
            //case 'No'
            case R.id.radioButton4:
                Log.d("NO", "onRadioButtonClicked1: ");
                break;

            //Case '1h'
            case R.id.radioButton3:
                Log.d("1h", "onRadioButtonClicked1: ");
                break;

            //case '2h'
            case R.id.radioButton2:
                Log.d("2h", "onRadioButtonClicked1: ");
                break;

            //Case '3h'
            case R.id.radioButton1:
                Log.d("3h", "onRadioButtonClicked1: ");
                break;

            //Case '4h'
            case R.id.radioButton:
                Log.d("4h", "onRadioButtonClicked1: ");
                break;
        }

    }
    //Page 2 - Checkboxes - When do you prefer taking classes?
    public void onRadioButtonClicked2(View view) {
        switch (view.getId())
        {
            //case 'Mornings'
            case R.id.radioButton4:
                Log.d("Morning", "onRadioButtonClicked2: ");
                break;

            //Case '+ mornin - after'
            case R.id.radioButton3:
                Log.d("Mornings rather after", "onRadioButtonClicked2: ");
                break;

            //case '+ after - mornin'
            case R.id.radioButton2:
                Log.d("Afternoon rather morn", "onRadioButtonClicked2: ");
                break;

            //Case 'Afternoons'
            case R.id.radioButton:
                Log.d("Afternoons", "onRadioButtonClicked2: ");
                break;
        }

    }

    //Page 4 - Checkboxes - "When do you want to have lunch?"
    public void onRadioButtonClicked4(View view) {
        switch (view.getId())
        {
            //case '1 p.m'
            case R.id.radioButton1:
                Log.d("1 p.m", "onRadioButtonClicked4: ");
                break;

            //Case '2 p.m'
            case R.id.radioButton2:
                Log.d("2 p.m", "onRadioButtonClicked4: ");
                break;

        }

    }

    //Siguiente activity
    public void continuar(View view){
        Intent intent = new Intent(this, Algorithm.class);
        startActivity(intent);
    }

}