package com.bluetoothpair.android;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    private BluetoothAdapter bluetoothAdapter;
    private ListView bluetoothDeviceList;
    private ArrayAdapter<String> bluetoothArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothDeviceList = (ListView) findViewById(R.id.bluetoothDeviceList);
        bluetoothArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        bluetoothDeviceList.setAdapter(bluetoothArrayAdapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(bluetoothIntent, 0);

        String pairingRequest = "android.bluetooth.device.action.PAIRING_REQUEST";
        registerReceiver(bluetoothDeviceFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        bluetoothAdapter.startDiscovery();

    }

    public final BroadcastReceiver bluetoothDeviceFoundReceiver = new BluetoothDeviceFoundReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("bluetooth", action);
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                device.createBond();
                bluetoothArrayAdapter.add(device.getName() + " " + device.getAddress());
                bluetoothArrayAdapter.notifyDataSetChanged();

                //get pin to be entered
                int pin = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 0);
                Log.d("bluetooth", "Pin is " + pin);
                Log.d("bluetooth", "Bounded " + device.getName());

                byte[] pinBytes;

                try {
                    pinBytes = ("" + pin).getBytes("UTF-8");
                    device.setPin(pinBytes);
                    device.setPairingConfirmation(true);
                }
                catch(IOException e) {
                    Log.d("bluetooth", "unhandled IOException");
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothDeviceFoundReceiver);
    }
}
