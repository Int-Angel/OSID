package com.example.osid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.osid.DB.DBCONTROLLER;
import com.example.osid.GLOBAL.GLOBAL;

public class MainActivity extends AppCompatActivity {

    DBCONTROLLER dbcontroller;
    ImageButton openGlucomenter, openOSID, openSettings, openCharts;
    ImageButton addBasal, substractBasal;
    MyTextView_Roboto_Regular basal, name;
    Switch activeBasal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbcontroller = new DBCONTROLLER(this);

        openGlucomenter = findViewById(R.id.btn_openGlucometer_main);
        openOSID = findViewById(R.id.btn_openOSID_main);
        openSettings = findViewById(R.id.btn_openSettings_main);
        openCharts = findViewById(R.id.btn_openCharts_main);

        addBasal = findViewById(R.id.add_basal_main);
        substractBasal = findViewById(R.id.substract_basal_main);

        basal = findViewById(R.id.txtview_basal_main);
        name = findViewById(R.id.txtview_name_main);

        activeBasal = findViewById(R.id.sw_active_basal_main);

        activeBasal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ChangeBasalActivation(b);
            }
        });

        addBasal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBasal(1);
            }
        });

        substractBasal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubstractBasal(1);
            }
        });

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
        Initialitation();
    }

    void Initialitation(){
        GLOBAL.user.copyUser(dbcontroller.GetUser());

        basal.setText(GLOBAL.user.getBasal() + " U");

        String Bienvenida = "";
        if(GLOBAL.user.isGender()){
            Bienvenida = "¡Bienvenido! ";
        }else{
            Bienvenida = "¡Bienvenida! ";
        }
        Bienvenida += GLOBAL.user.getNombre();
        name.setText(Bienvenida);
    }

    void AddBasal(int n){
        if(activeBasal.isChecked()){
            GLOBAL.user.setBasal(GLOBAL.user.getBasal() + n);
            basal.setText(GLOBAL.user.getBasal() + " U");
            dbcontroller.ActualizarUser(GLOBAL.user);
        }
    }

    void SubstractBasal(int n){
        if(activeBasal.isChecked()){
            float finalBasal = GLOBAL.user.getBasal() - n;
            if(finalBasal>=0){
                GLOBAL.user.setBasal(finalBasal);
                basal.setText(GLOBAL.user.getBasal() + " U");
                dbcontroller.ActualizarUser(GLOBAL.user);
            }
        }
    }

    void ChangeBasalActivation(boolean active){
        //TODO mandar al arduino esat info

        
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
