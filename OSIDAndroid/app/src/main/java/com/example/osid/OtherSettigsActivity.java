package com.example.osid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class OtherSettigsActivity extends AppCompatActivity {

    ImageButton back;
    //TODO notificaciones -> CAMBIO DE REPOSITORIO
    //TODO notificaciones -> CAMBIO DE CATETER
    //TODO notificaciones -> HORAS DE INGERIR COMIDAS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_settigs);

        back = findViewById(R.id.goBack_other_settings);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
