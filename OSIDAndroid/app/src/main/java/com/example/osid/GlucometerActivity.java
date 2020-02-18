package com.example.osid;


import android.os.Bundle;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

public class GlucometerActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView waitTime_txt;
    TextView waitingGlucometer_lbl;
    LinearLayout glucoInfoLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glucometer_activity);
        progressBar = findViewById(R.id.waitProgressBarID_glucometer);
        waitTime_txt = findViewById(R.id.waitTimeID_glucometer);
        waitingGlucometer_lbl = findViewById(R.id.waitingGlucometerID_glucometer);
        glucoInfoLinearLayout = findViewById(R.id.glucoInfoID_LinearLayout);
        ReceiveData(false);
    }

    public void ReceiveData(Boolean data)
    {
        if(data)
        {
            waitingGlucometer_lbl.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            waitTime_txt.setVisibility(View.INVISIBLE);
        }
        else
        {
            waitingGlucometer_lbl.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            waitTime_txt.setVisibility(View.VISIBLE);
            glucoInfoLinearLayout.setVisibility(View.GONE);
        }
    }
}