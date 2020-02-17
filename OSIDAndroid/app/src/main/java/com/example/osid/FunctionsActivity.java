package com.example.osid;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osid.DB.DBCONTROLLER;
import com.example.osid.GLOBAL.GLOBAL;
import com.example.osid.POJOs.Insuline;
import com.example.osid.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FunctionsActivity extends AppCompatActivity {

    DBCONTROLLER dbcontroller;
    TextView basal;
    TextView currentInsuline_txt;
    TextView time;
    TextView basalPerHour;
    TextView lastReg;
    ImageButton addBasal, substractBasal;
    ImageButton addInsuline, substractInsuline;
    ImageButton goBack;

    Switch activeBasal;
    Insuline newInsuline;
    BigDecimal currentInsuline = new BigDecimal("0.1");

    Button inyectarButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functions);

        dbcontroller = new DBCONTROLLER(this);
        goBack = findViewById(R.id.goBackID_functions);

        activeBasal = findViewById(R.id.sw_active_basal_functions);
        basal = findViewById(R.id.basal1_function_ID);


        currentInsuline_txt = findViewById(R.id.basal2_functions_ID);
        time = findViewById(R.id.txtview_time_basal_per_hour_functions);
        basalPerHour = findViewById(R.id.txtview_basal_per_hour_functions);
        addBasal = findViewById(R.id.add_basal1_functions);
        substractBasal = findViewById(R.id.substract_basal1_functions);

        addInsuline = findViewById(R.id.add_basal2_functions);
        substractInsuline = findViewById(R.id.substract_basal2_functions);

        lastReg = findViewById(R.id.lastRegID);

        activeBasal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ChangeBasalActivation(b);
            }
        });

        
        //---------------------------------------------------------------------------------------------------//
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
        addInsuline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {AddInsuline(new BigDecimal("0.1"));
            }
        });
        substractInsuline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {SubstractInsuline(new BigDecimal("0.1"));    }
        });
        //---------------------------------------------------------------------------------------------------//
        inyectarButton = findViewById(R.id.inyectarButtonID);
        inyectarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inyeccionInstantanea();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Initialitation();
    }



    void Initialitation() {
        CheckInsulineRegs();
        
        newInsuline = new Insuline();
        basal.setText(GLOBAL.user.getBasal() + " U");
        currentInsuline_txt.setText(currentInsuline + "U");
        time.setText("Tiempo: " + 0 + " min"); //TODO con el arduino
        basalPerHour.setText(GLOBAL.user.getBasal() / 24 + " U/h");
    }

    private void CheckInsulineRegs() {
        int size=0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        try{
            size = dbcontroller.GetArrayInsuline().size();
        }
        catch (Exception e)
        {}

        if(size > 0)
        {
            Date lastRegTime = new Date();
            lastRegTime = dbcontroller.GetArrayInsuline().get(size-1).getFecha();
            //lastRegTime =  GLOBAL.INSULINE_ARRAY_LIST.get(size-1).getFecha();
            long millisReg = lastRegTime.getTime();
            long millisNow = date.getTime();

            long transcurridoTime = millisNow - millisReg;
            Toast.makeText(FunctionsActivity.this, "Tiempo transcurrido desde la ultima inyeccion:" + transcurridoTime, Toast.LENGTH_SHORT).show();

            if(transcurridoTime < 60000)
            {
                inyectarButton.setEnabled(false);
                inyectarButton.setBackgroundColor(Color.GRAY);
                lastReg.setText("Inyeccion en Proceso a "+GLOBAL.INSULINE_ARRAY_LIST.get(size-1).getFecha().toString());
            }

            else
                inyectarButton.setEnabled(true);
        }

    }

    void AddBasal(int n) {
        if (activeBasal.isChecked()) {
            GLOBAL.user.setBasal(GLOBAL.user.getBasal() + n);
            basal.setText(GLOBAL.user.getBasal() + " U");
            dbcontroller.ActualizarUser(GLOBAL.user);
            basalPerHour.setText(GLOBAL.user.getBasal() / 24 + " U/h");
        }
    }

    void SubstractBasal(int n) {
        if (activeBasal.isChecked()) {
            float finalBasal = GLOBAL.user.getBasal() - n;
            if (finalBasal >= 0) {
                GLOBAL.user.setBasal(finalBasal);
                basal.setText(GLOBAL.user.getBasal() + " U");
                dbcontroller.ActualizarUser(GLOBAL.user);
                basalPerHour.setText(GLOBAL.user.getBasal() / 24 + " U/h");
            }
        }
    }

    void AddInsuline(BigDecimal n) {
        currentInsuline = currentInsuline.add(n);
        currentInsuline_txt.setText(currentInsuline + " U");
    }

    void SubstractInsuline(BigDecimal n) {
        currentInsuline = currentInsuline.subtract(n);
        currentInsuline_txt.setText(currentInsuline + " U");
    }


    private void inyeccionInstantanea() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        newInsuline.setFecha(date);
        newInsuline.setInsuline(currentInsuline.floatValue());
        GLOBAL.INSULINE_ARRAY_LIST.add(newInsuline);

        dbcontroller.InsertInsuline(newInsuline);

        int size = GLOBAL.INSULINE_ARRAY_LIST.size();
        lastReg.setText("Inyeccion en Proceso a "+GLOBAL.INSULINE_ARRAY_LIST.get(size-1).getFecha().toString());
    }




    void ChangeBasalActivation(boolean active) {
    }
}
