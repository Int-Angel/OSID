package com.example.osid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import com.example.osid.DB.DBCONTROLLER;
import com.example.osid.GLOBAL.BluetoothVerifyConnection;
import com.example.osid.GLOBAL.GLOBAL;

public class MainActivity extends AppCompatActivity {

    DBCONTROLLER dbcontroller;
    ImageButton openGlucomenter, openOSID, openSettings, openCharts;
    ImageButton addBasal, substractBasal;
    MyTextView_Roboto_Regular name;
    EditText basal;
    Switch activeBasal;
    MyTextView_Roboto_Bold time, basalPerHour;
    TextView bluetoothStatuslbl;

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

        time = findViewById(R.id.txtview_time_basal_per_hour_main);
        basalPerHour = findViewById(R.id.txtview_basal_per_hour_main);


        //------------------------BLUETOOTH INFO---------------------------------------//
        bluetoothStatuslbl = findViewById(R.id.bluetoothStatuslbl_main_ID);
        bluetoothStatuslbl.setText("Sin Conexion");
        bluetoothStatuslbl.setTextColor(getColor(R.color.red));
        //-----------------------------------------------------------------------------//

        activeBasal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ChangeBasalActivation(b);

            }
        });

        basal.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)){
                    if(basal.getText().toString().length()<=0){
                        basal.setText("0");
                    }
                    GLOBAL.user.setBasal(Float.parseFloat(basal.getText().toString()));
                    //basal.setText(GLOBAL.user.getBasal() + " U");
                    dbcontroller.ActualizarUser(GLOBAL.user);
                    basalPerHour.setText(GLOBAL.user.getBasal() / 24 + " U/h");
                    //basal.setSelected(false);
                    basal.clearFocus();
                    hideSoftKeyboard(getWindow().getDecorView().findViewById(android.R.id.content));
                    return true;
                }
                return false;
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
        VerifyBTModuleConnection();
    }


    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputManager.hideSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    void Initialitation(){
        GLOBAL.user.copyUser(dbcontroller.GetUser());

        basal.setText(GLOBAL.user.getBasal() + "");

        String Bienvenida = "";
        if(GLOBAL.user.isGender()){
            Bienvenida = "¡Bienvenido! ";
        }else{
            Bienvenida = "¡Bienvenida! ";
        }
        Bienvenida += GLOBAL.user.getNombre();
        name.setText(Bienvenida);

        time.setText("Tiempo: " + 0 + " min"); //TODO con el arduino
        basalPerHour.setText(GLOBAL.user.getBasal() / 24 + " U/h");
    }

    void AddBasal(int n){
        if(activeBasal.isChecked()){
            GLOBAL.user.setBasal(GLOBAL.user.getBasal() + n);
            basal.setText(GLOBAL.user.getBasal() + "");
            dbcontroller.ActualizarUser(GLOBAL.user);
            basalPerHour.setText(GLOBAL.user.getBasal() / 24 + " U/h");
        }
    }

    void SubstractBasal(int n){
        if(activeBasal.isChecked()){
            float finalBasal = GLOBAL.user.getBasal() - n;
            if(finalBasal >= 0){
                GLOBAL.user.setBasal(finalBasal);
                basal.setText(GLOBAL.user.getBasal() + "");
                dbcontroller.ActualizarUser(GLOBAL.user);
                basalPerHour.setText(GLOBAL.user.getBasal() / 24 + " U/h");
            }
        }
    }

    private void VerifyBTModuleConnection() {
        BluetoothVerifyConnection bluetoothVerifyConnection = new BluetoothVerifyConnection();
        if(GLOBAL.bluetoothDevice.getMAC() != null)
        {
            bluetoothVerifyConnection.VerifyConnection();

            if(bluetoothVerifyConnection.isSuccessfull)
            {
                bluetoothStatuslbl.setText("Enhorabuena! Estas Conectado");
                bluetoothStatuslbl.setTextColor(getColor(R.color.colorAccent));
            }
            else{
                bluetoothStatuslbl.setText("Sin Conexion");
                bluetoothStatuslbl.setTextColor(getColor(R.color.red));
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
    @Override
    protected void onRestart() {
        super.onRestart();
        Initialitation();
        VerifyBTModuleConnection();
    }

    @Override
    public void onBackPressed()
    {

    }
}