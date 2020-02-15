package com.example.osid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.osid.DB.DBCONTROLLER;
import com.example.osid.POJOs.Glucose;
import com.example.osid.POJOs.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
