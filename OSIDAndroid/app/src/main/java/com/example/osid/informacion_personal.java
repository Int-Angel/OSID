package com.example.osid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.osid.POJOs.User;

import androidx.appcompat.app.AppCompatActivity;

public class informacion_personal extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    EditText nameEdit;
    EditText lastName1Edit;
    EditText lastName2Edit;
    EditText ageEdit;
    EditText weightEdit;
    EditText basalEdit;
    Button registerInfo;
    RadioGroup genderGroup;
    RadioButton maleRadio, femaleRadio;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomacion_personal);

        nameEdit = findViewById(R.id.name_lbl);
        lastName1Edit = findViewById(R.id.lastName1_lbl);
        lastName2Edit = findViewById(R.id.lastName2_lbl);
        ageEdit = findViewById(R.id.age_lbl);
        weightEdit = findViewById(R.id.weight_lbl);
        basalEdit = findViewById(R.id.basal_lbl);
        //---------------------------------------------------------//
        genderGroup = findViewById(R.id.radioGroupID);
        maleRadio = findViewById(R.id.masculinoButtonID);
        femaleRadio = findViewById(R.id.femeninoButtonID);
        genderGroup.setOnCheckedChangeListener(this);

        //---------------------------------------------------------//

        registerInfo = findViewById(R.id.registerInfo_btn);

        registerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
                Toast.makeText(informacion_personal.this, "vientos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RegisterUser()
    {
        User user = new User();
        user.setNombre(nameEdit.toString());
        user.setEdad(Integer.parseInt(ageEdit.toString()));
        user.setPeso(Integer.parseInt(weightEdit.toString()));
        user.setPrimerApellido(lastName1Edit.toString());
        user.setSegundoApellido(lastName2Edit.toString());
        user.setBasal(Integer.parseInt(basalEdit.toString()));

        if(gender == "Masculino")
            user.setGender(true);
        if(gender == "Femenino")
            user.setGender(false);
    }

    @Override
    public void onCheckedChanged(RadioGroup genderGroup, int i) {
        switch (i)
        {
            case R.id.masculinoButtonID:
                gender="Masculino";
                break;

            case R.id.femeninoButtonID:
                gender="Femenino";
                break;
        }
    }
}
