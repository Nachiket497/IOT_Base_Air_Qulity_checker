package com.example.airquality;

import androidx.appcompat.app.AppCompatActivity;
import pl.pawelkleczkowski.customgauge.CustomGauge;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Runnable refresh ;
    Handler handler = new Handler();
    public static Button click ;
    public static CustomGauge gauge ;
    public static TextView value_ppm ,condi , txt_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        start();
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
                FatchData process = new FatchData();
                process.execute();

            }
        });

        refresh = new Runnable() {
            public void run() {
                start();
                handler.postDelayed(refresh, 15000);
            }
        };
        handler.post(refresh);


    }



    private void start() {
        click.setVisibility(View.GONE);
        FatchData process = new FatchData();
        process.execute();
    }

    private void init(){
        txt_end = findViewById(R.id.txt_end);
        click  = findViewById(R.id.button);
        gauge = (CustomGauge) findViewById(R.id.gauge1);
        value_ppm = findViewById(R.id.textView);
        condi = findViewById(R.id.txt_condi);
        gauge.setStartValue(0);
        gauge.setEndValue(2000);
    }
}
