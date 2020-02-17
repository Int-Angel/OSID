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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osid.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class GlucometerActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final int REQUEST_ENABLE_BT = 0;
    //private static final int REQUEST_DISCOVER_BT = 1;

    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    Switch onOff;
    ImageButton  mPairedBtn,mScanBtn;
    ListView mListView;
    public ArrayList<BluetoothDevice> mBTdevices = new ArrayList<>();
    BluetoothAdapter mBlueAdapter;
    public DeviceListAdapter deviceListAdapter;

    private BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            showToast("ACTION FOUND");
            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTdevices.add(device);
                deviceListAdapter = new DeviceListAdapter(context,R.layout.device_adapter_view, mBTdevices);
                mListView.setAdapter(deviceListAdapter);
                showToast("SE ENCONTRO UN DISPOSITIVO "+ device.getName() + device.getAddress());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glucometer);
        onOff = findViewById(R.id.turnonoff);
        onOff.setOnCheckedChangeListener(this);
        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mScanBtn = findViewById(R.id.scanBtn);
        mPairedTv     = findViewById(R.id.pairedTv);
        mBlueIv       = findViewById(R.id.bluetoothIv);
        //mOffBtn       = findViewById(R.id.offBtn);
        //mDiscoverBtn  = findViewById(R.id.discoverableBtn);
        mPairedBtn    = findViewById(R.id.pairedBtn);
        mListView     = findViewById(R.id.deviceList);
        mBTdevices = new ArrayList<>();

        //adapter
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        //check if bluetooth is available or not
        if (mBlueAdapter == null){
            mStatusBlueTv.setText("Bluetooth is not available");
        }
        else {
            mStatusBlueTv.setText("Bluetooth is available");
        }

        //set image according to bluetooth status(on/off)
        if (mBlueAdapter.isEnabled()){
            mBlueIv.setImageResource(R.drawable.ic_action_on);
            onOff.setChecked(true);
        }
        else {
            mBlueIv.setImageResource(R.drawable.ic_action_off);
            onOff.setChecked(false);
        }

/*
        //on btn click
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isEnabled()){
                    showToast("Turning On Bluetooth...");
                    //intent to on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                }
                else {
                    showToast("Bluetooth is already on");
                }
            }
        });
        //discover bluetooth btn click
        mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isDiscovering()){
                    showToast("Making Your Device Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });
        //off btn click
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()){
                    mBlueAdapter.disable();
                    showToast("Turning Bluetooth Off");
                    mBlueIv.setImageResource(R.drawable.ic_action_off);
                }
                else {
                    showToast("Bluetooth is already off");
                }
            }
        });
 */
        //get paired devices btn click
        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()){
                    mPairedTv.setText("Paired Devices");
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for (BluetoothDevice device: devices){
                        mPairedTv.append("\nDevice: " + device.getName()+ ", " + device);
                    }
                }
                else {
                    //bluetooth is off so can't get paired devices
                    showToast("Turn on bluetooth to get paired devices");
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK){
                    //bluetooth is on
                    mBlueIv.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on");
                }
                else {
                    //user denied to turn bluetooth on
                    showToast("could't on bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //toast message function
    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void ScanForDevices(View view) {
        showToast("Searching for Devices");
        if(mBlueAdapter.isDiscovering()){
            mBlueAdapter.cancelDiscovery();
            showToast("Cancelling Discovery");
            checkBTConnections();
            mBlueAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver2, discoverDevicesIntent);
        }
        if(!mBlueAdapter.isDiscovering()){
            checkBTConnections();

            mBlueAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver2, discoverDevicesIntent);
        }

    }

    @Override
    protected void onDestroy() {
        showToast("onDestroy: called");
        super.onDestroy();
        //mBluetoothAdapter.cancelDiscovery();
    }

    private void checkBTConnections() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if(permissionCheck != 0){
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
            else{
                showToast("No need to check permission, SDK version is greater than LOLLIPOP");
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            showToast("Turning On Bluetooth...");
            //intent to on bluetooth
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        } else {
            showToast("Turning Off Bluetooth...");
            mBlueAdapter.disable();
            mBlueIv.setImageResource(R.drawable.ic_action_off);
        }
    }
}