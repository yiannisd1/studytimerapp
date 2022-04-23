package com.example.studytimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Is study timer active
    private boolean active;
    private long wasActive;
    private Chronometer chronometer;


    TextView textView, textView2, textView3;
    EditText editText;
    SharedPreferences sharedPreferences;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putLong("ChronoTime", chronometer.getBase());
        savedInstanceState.putLong("storeTime", SystemClock.elapsedRealtime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        textView.setText("Enter your unit");

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

            }
        });

        if(savedInstanceState != null){

            chronometer.setBase(savedInstanceState.getLong("ChronoTime"));
            if (!active)
            {
                chronometer.start();
            }
        }

        sharedPreferences = getSharedPreferences("storedTime", MODE_PRIVATE);
        checkSharedPreferences();
    }

    public void checkSharedPreferences()
    {
        String textView2string = sharedPreferences.getString("ChronoTime", "");
        String textView3string = sharedPreferences.getString("storedTime", "");

        textView2.setText(textView2string);
        textView3.setText(textView3string);
    }

    public void onClickStart(View view) {
        if (!active)
        {
            // in order to return to same number it paused from
            chronometer.setBase(SystemClock.elapsedRealtime() - wasActive);
            chronometer.start();
            active = true;
        }
    }

    public void onClickPause(View view) {
        if (active)
        {
            wasActive = SystemClock.elapsedRealtime() - chronometer.getBase();
            active = false;
            chronometer.stop();
        }
    }

    public void onClickStop(View view) {


            chronometer.stop();
            wasActive = 0;

            editText = findViewById(R.id.editText);
            String text = editText.getText().toString();
            editText.setText("");
            editText.requestFocus();

            long totalSecs = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
            String hours = String.valueOf((totalSecs / 3600));
            String minutes = String.valueOf((totalSecs % 3600) / 60);
            String seconds = String.valueOf(totalSecs % 60);

            String userInput = ("The unit was " + text);
            String timerStored = "Last session lasted" + " " + hours + " hours " + minutes + " minutes " + seconds + " seconds";

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ChronoTime", userInput );
            editor.putString( "storedTime", timerStored);
            editor.apply();

            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.chronometer.setBase(elapsedRealtime);

            checkSharedPreferences();

            active = false;

    }

}







//
//        String value =editText.getText().toString();
//        textView.setText();