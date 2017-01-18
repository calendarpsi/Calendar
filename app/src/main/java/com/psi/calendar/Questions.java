package com.psi.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.psi.calendar.CalendarOption;
import com.psi.calendar.R;

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
    private boolean everyday = false, first_sem = false, second_sem = false;

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
            return 7;
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
                    }
                    paginaactual = pagina5;
                    break;
                case 5:
                    if (pagina6 == null) {
                        pagina6 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina6, null);
                        //pregunta6();
                    }
                    paginaactual = pagina6;
                    break;
                case 6:
                    if (pagina7 == null) {
                        pagina7 = (LinearLayout) LayoutInflater.from(Questions.this).inflate(R.layout.pagina7, null);
                        //pregunta7();
                        btnContinuar.setVisibility(View.VISIBLE);
                    }
                    paginaactual = pagina7;
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

    private void pregunta6() {
        //TODO rellenar codigo aqui pregunta 6
        Toast.makeText(this, "Pregunta 6", Toast.LENGTH_SHORT).show();


    }

    private void pregunta7() {
        //TODO rellenar codigo aqui pregunta 7
        Toast.makeText(this, "Pregunta 7", Toast.LENGTH_SHORT).show();
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
        //Checked 1st semester - PAG 5
        //TODO quitar Toast
        else if (view.getId() == R.id.checkBox7) {
            if (checked) {
                first_sem = true;
                Toast.makeText(this, "Debug 1st true", Toast.LENGTH_SHORT).show();
            } else {
                first_sem = false;
            }
        }
        //Checked 2nd semester - PAG 5
        //TODO quitar toast
        else if (view.getId() == R.id.checkBox8) {
            if (checked) {
                second_sem = true;
                Toast.makeText(this, "Debug 2nd true", Toast.LENGTH_SHORT).show();
            } else {
                second_sem = false;
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

    //Siguiente activity
    public void continuar(View view){
        Intent intent = new Intent(this, CalendarOption.class);
        startActivity(intent);
    }

}