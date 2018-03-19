package com.example.studente.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Vibrator;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class call extends AppCompatActivity {
    TextView nome;
    TextView tel;
    ImageView accept;
    ImageView deny;
    ImageView deny1;
    ImageView front;
    ImageView speaker;
    Ringtone r;
    Vibrator v;
    Chronometer c;
    Persona p;
    int cont=0;
    String voice=" ";
    MediaPlayer voicePlayer;
    boolean flag = true;
    boolean flag1= true;
    CountDownTimer tim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_call);
        nome = (TextView) findViewById(R.id.personCall);
        tel = (TextView) findViewById(R.id.textNumber);
        accept = (ImageView) findViewById(R.id.accept);
        deny = (ImageView) findViewById(R.id.deny1);
        deny1 = (ImageView) findViewById(R.id.deny);
        front =(ImageView) findViewById(R.id.front);
        speaker = (ImageView) findViewById(R.id.speaker);

        final Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();


        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};

        c = (Chronometer) findViewById(R.id.crono);

        if (getIntent() != null) {
            p = (Persona) getIntent().getSerializableExtra("key_persona");
            nome.setText(p.getNome() + " "+ p.getCognome());
            tel.setText(p.getTelefono());
            v.vibrate(pattern, 0);
            r.play();

            tim = new CountDownTimer(15000, 1000) {
                @Override
                public void onTick(long l) {
                }
                @Override
                public void onFinish() {
                        if(cont==0) {
                            r.stop();
                            v.cancel();
                            c.stop();
                            notfiica();
                            finishAffinity();
                        }
                }
            }.start();
            }


        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.stop();
                v.cancel();
                c.stop();
                stopVoice();
                finishAffinity();
            }
        });

        deny1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    tim.cancel();
                    r.stop();
                    v.cancel();
                    c.stop();
                    stopVoice();
                    finishAffinity();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont=1;
                r.stop();
                v.cancel();
                nome.setVisibility(View.VISIBLE);
                tel.setVisibility(View.VISIBLE);
                accept.setVisibility(View.INVISIBLE);
                deny.setVisibility(View.VISIBLE);
                deny1.setVisibility(View.INVISIBLE);
                speaker.setVisibility(View.VISIBLE);
                front.setVisibility(View.VISIBLE);
                c.setVisibility(View.VISIBLE);
                playVoice();
                speaker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stopVoice();
                        playVoice();
                    }
                });
                front.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stopVoice();
                        if(flag1) {
                            speaker.setVisibility(View.INVISIBLE);
                            flag1=false;
                        }else {
                            speaker.setVisibility(View.VISIBLE);
                            flag1 = true;
                            flag = true;
                            playVoice();
                        }
                    }
                });
                c.setBase(SystemClock.elapsedRealtime());
                c.start();

            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        r.stop();
        v.cancel();
        c.stop();
        stopVoice();
    }
    public void notfiica(){

                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.background_red)
                                .setColor(101)
                                .setContentText(p.getNome() + " " + p.getCognome() + " " + p.getTelefono());   //this is the message showed in notification
                if(Locale.getDefault().getLanguage().toString().equals("it")) {
                    builder.setContentTitle("Chiamata Persa");
                }else{
                    builder.setContentTitle("Missed Call");
                }

                Intent intent = new Intent(this, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);
                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());

    }

    public  void playVoice(){

        if(flag){

            flag=false;
            Uri voiceURI=Uri.parse("android.resource://"+getPackageName().toString()+"/raw/effect");

            voicePlayer = new MediaPlayer();

            try {
                voicePlayer.setDataSource(this, voiceURI);
            } catch (Exception e) {
                e.printStackTrace();
            }

            voicePlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);

            voicePlayer.prepareAsync();

            voicePlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }

            });
        }else {
            flag=true;
            stopVoice();
            Uri voiceURI = Uri.parse("android.resource://" + getPackageName().toString() + "/raw/effect");

            voicePlayer = new MediaPlayer();

            try {
                voicePlayer.setDataSource(this, voiceURI);
            } catch (Exception e) {
                e.printStackTrace();
            }

            voicePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            voicePlayer.prepareAsync();

            voicePlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
    }

    private void stopVoice() {

        if (voicePlayer != null && voicePlayer.isPlaying()) {
            voicePlayer.stop();
        }

    }
}
