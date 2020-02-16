package com.example.osid;

import android.os.Bundle;
import android.widget.TextView;

import com.example.osid.DB.DBCONTROLLER;
import com.example.osid.GLOBAL.GLOBAL;
import com.example.osid.R;

import org.w3c.dom.Text;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FunctionsActivity extends AppCompatActivity {

    DBCONTROLLER dbcontroller;
    TextView basal1;
    TextView basal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functions);

        basal1 = findViewById(R.id.basal1_function_ID);
        basal2 = findViewById(R.id.basal2_functions_ID);
        Initialitation();
    }

    void Initialitation(){

        basal1.setText(GLOBAL.user.getBasal() + " U");

    }
}
