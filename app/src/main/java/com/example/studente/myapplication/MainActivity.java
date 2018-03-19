package com.example.studente.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textView;
    private Button submit;
    private Button delete;
    private TextView textView1;
    private TextView textView2;
    private int a = 0, b = 30;
    private Persona p;
    private ArrayList nomiita;
    private long tempo = 30000;
    private static String PROGRESS_TAG = "PROGRES_BAR";
    private static String TEMPO_TAG_A = "TEMPO_A";
    private static String TEMPO_TAG_B = "TEMPO_B";
    private static int progress = 30;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        submit = (Button) findViewById(R.id.button);
        delete = (Button) findViewById(R.id.button1);
        delete.setVisibility(View.INVISIBLE);
        textView1 = (TextView) findViewById(R.id.textView2);
        textView1.setVisibility(View.INVISIBLE);
        textView2 = (TextView) findViewById(R.id.textView2);
        if (savedInstanceState != null && savedInstanceState.containsKey(PROGRESS_TAG)) {
            seekBar.setProgress(savedInstanceState.getInt(PROGRESS_TAG));
            progress=savedInstanceState.getInt(PROGRESS_TAG);
            timecalculate();

        } else {
            seekBar.setProgress(progress);
            textView.setText("30 sec");
        }





        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//            int progress = MainActivity.progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                a = progress / 60;
                b = progress % 60;

                    if (a < 1) {
                        textView.setText(b + " sec");
                    } else {
                        textView.setText(a + "min e " + b + " sec");
                    }

                tempo = ((a * 60) + b) * 1000;

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView1.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_LONG).show();
                setP();
                timer();
            }
        });

    }


    private void setP(){
        EditText nome = (EditText)findViewById(R.id.editText);
        EditText cognome = (EditText)findViewById(R.id.editText3);
        EditText tel = (EditText)findViewById(R.id.editText4);
        boolean name = TextUtils.isEmpty(nome.getText());
        boolean surname = TextUtils.isEmpty(cognome.getText());
        boolean t = TextUtils.isEmpty(tel.getText());


        if((TextUtils.isEmpty(nome.getText()) && TextUtils.isEmpty(cognome.getText()))  && TextUtils.isEmpty(tel.getText())){

            if(Locale.getDefault().getLanguage().toString().equals("it")) {
                p = new Persona("Mamma","","+39 340 946 2851");

            }else{
                p = new Persona("Mom","","+44 123 123 6523");
            }
        }else{
            if(Locale.getDefault().getLanguage().toString().equals("it")) {
                if(t){
                    p = new Persona(nome.getText().toString(),cognome.getText().toString(),"+39 340 946 2851");
                }else{
                    p = new Persona(nome.getText().toString(),cognome.getText().toString(),"+39 "+tel.getText().toString());
                }
            }else{
                if(t){
                    p = new Persona(nome.getText().toString(),cognome.getText().toString(),"+44 123 123 6523");
                }else{
                    p = new Persona(nome.getText().toString(),cognome.getText().toString(),"+44 "+tel.getText().toString());
                }

            }

        }
    }

    private void timer() {
        final CountDownTimer tim = new CountDownTimer(tempo, 1000) {
            public void onTick(long millisUntilFinished) {
                submit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.VISIBLE);
               //textView2.setText("" + millisUntilFinished);
                seekBar.setClickable(false);
                seekBar.setVisibility(View.INVISIBLE);
                textView.setText(a + "min e " + b + " sec");
                if (a == 0) {
                    textView.setText(b + " sec");
                } else if (b == 0) {
                    a--;
                    b = 59;
                }
                b--;
            }

            public void onFinish() {
                delete.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                Intent atv2= new Intent(getApplicationContext(),call.class);
                atv2.putExtra("key_persona",p);
                startActivity(atv2);
                b = 30;
                a = 0;
                textView.setText(0 + "min e " + 30 + " sec");

            }

        }.start();

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tim.cancel();
                //textView2.setText("ABORTED");
                Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_LONG).show();
                submit.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                delete.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PROGRESS_TAG, seekBar.getProgress());
        outState.putInt(TEMPO_TAG_A,a);
        outState.putInt(TEMPO_TAG_B,b);
    }

    public void timecalculate(){
        a = progress / 60;
        b = progress % 60;

        if(a < 1) {
            textView.setText(b + " sec");
        } else {
            textView.setText(a + "min e " + b + " sec");
        }

        tempo = ((a * 60) + b) * 1000;
    }

}




