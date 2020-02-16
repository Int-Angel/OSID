package com.example.osid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton openGlucomenter, openOSID, openSettings, openCharts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openGlucomenter = findViewById(R.id.btn_openGlucometer_main);
        openOSID = findViewById(R.id.btn_openOSID_main);
        openSettings = findViewById(R.id.btn_openSettings_main);
        openCharts = findViewById(R.id.btn_openCharts_main);

        openGlucomenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGlucometer();
            }
        });

        openOSID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenOSID();
            }
        });

        openSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSettings();
            }
        });

        openCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCharts();
            }
        });
    }

    void OpenGlucometer(){
        Intent glucometerActivity = new Intent(this,GlucometerActivity.class);
        startActivity(glucometerActivity);
    }

    void OpenOSID(){
        Intent osidAtivity = new Intent(this,FunctionsActivity.class);
        startActivity(osidAtivity);
    }

    void OpenSettings(){
        Intent settingsActivity = new Intent(this,SettingsActivity.class);
        startActivity(settingsActivity);
    }

    void OpenCharts(){
        Intent chartsActivity = new Intent(this,GraphActivity.class);
        startActivity(chartsActivity);
    }

}
