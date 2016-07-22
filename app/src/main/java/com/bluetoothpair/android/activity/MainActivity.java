package com.bluetoothpair.android.activity;

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

import com.bluetoothpair.android.R;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    private BluetoothAdapter bluetoothAdapter;
    private ListView bluetoothDeviceList;
    private ArrayAdapter<String> bluetoothArrayAdapter;
    private String pairingRequest = "android.bluetooth.device.action.PAIRING_REQUEST";

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


        registerReceiver(bluetoothDeviceFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        bluetoothAdapter.startDiscovery();

    }

    public final BroadcastReceiver bluetoothDeviceFoundReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("bluetooth", action);
            if(action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                device.createBond();
                bluetoothArrayAdapter.add(device.getName() + " " + device.getAddress());
                bluetoothArrayAdapter.notifyDataSetChanged();

                //get pin to be entered
                int pin = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 1234);
                Log.d("bluetooth", "Pin is " + pin);

                byte[] pinBytes;

                String TAG = "bluetooth";

                try {
                    pinBytes = ("" + pin).getBytes("UTF-8");
//                    Method m = device.getClass().getMethod("setPin", byte[].class);
//                    m.invoke(device, pinBytes);
//                    Log.d(TAG, "Success to add the PIN.");
//                    device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
//
//                    Log.d(TAG, "Success to setPairingConfirmation.");

                    device.setPin(pinBytes);
                    device.setPairingConfirmation(true);
                }
                catch(IOException e) {
                    Log.d("bluetooth", "unhandled IOException");
                }
                catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
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
