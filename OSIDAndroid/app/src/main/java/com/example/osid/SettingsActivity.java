package com.example.osid;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final int REQUEST_ENABLE_BT = 0;
    //private static final int REQUEST_DISCOVER_BT = 1;

    ImageButton Bluetooth, Otros, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bluetooth = findViewById(R.id.BluetoothSettings);
        Otros = findViewById(R.id.CualquierOtro);
        back = findViewById(R.id.goBack_settings);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public void ConfigurarBluetooth(View view) {
        Intent i = new Intent(this, BluetoothConfigurarActivity.class);
        startActivity(i);
    }


    public void OtrosAjustes(View view) {
        Intent i = new Intent(this, BluetoothConfigurarActivity.class);
        startActivity(i);
    }
}