package com.example.osid;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.osid.GLOBAL.GLOBAL;

public class GlucometerActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView waitTime_txt;
    TextView waitingGlucometer_lbl;
    com.example.osid.MyTextView_Roboto_Regular glucosa, insulinaRecomendada;
    LinearLayout glucoInfoLinearLayout;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glucometer);
        progressBar = findViewById(R.id.waitProgressBarID_glucometer);
        waitTime_txt = findViewById(R.id.waitTimeID_glucometer);
        waitingGlucometer_lbl = findViewById(R.id.waitingGlucometerID_glucometer);
        glucoInfoLinearLayout = findViewById(R.id.glucoInfoID_LinearLayout);
        glucosa = findViewById(R.id.basal1_function_ID);
        insulinaRecomendada = findViewById(R.id.insulina_recomendada);
        back = findViewById(R.id.goBackID_glucometer);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        GLOBAL.btconection.MyConexionBT.run();
        ReceiveData(!GLOBAL.btconection.MyConexionBT.dato.equals(""));
    }

    public void ReceiveData(Boolean data)
    {
        if(data)
        {
            waitingGlucometer_lbl.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            waitTime_txt.setVisibility(View.INVISIBLE);

            glucosa.setText(GLOBAL.btconection.MyConexionBT.dato + " mg/dl");
            if(Integer.parseInt(glucosa.getText().toString()) > 100){
                float insulina = 0;
                insulina = Integer.parseInt(glucosa.getText().toString()) - 100;
                insulina = insulina/GLOBAL.user.getPGPU();
                insulinaRecomendada.setText(insulina + " U");
            }
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