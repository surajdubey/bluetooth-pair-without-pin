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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bluetoothpair.android.R;
import com.bluetoothpair.android.RegistrationCallback;
import com.bluetoothpair.android.controller.DeviceRegistrationController;
import com.bluetoothpair.android.model.UserRegistrationResponse;
import com.bluetoothpair.android.utils.L;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class BluetoothPairActivity extends AppCompatActivity {

    private Context context = this;

    private BluetoothAdapter bluetoothAdapter;
    private ListView bluetoothDeviceList;
    private ArrayAdapter<String> bluetoothArrayAdapter;
    private ArrayList<BluetoothDevice> bluetoothDevices;
    private ArrayList<Integer> pins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothDeviceList = (ListView) findViewById(R.id.bluetoothDeviceList);
        bluetoothArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        bluetoothDeviceList.setAdapter(bluetoothArrayAdapter);

        bluetoothDevices = new ArrayList<>();
        pins = new ArrayList<>();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(bluetoothIntent, 0);

        registerReceiver(bluetoothDeviceFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        bluetoothAdapter.startDiscovery();

        bluetoothDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                pairDevice(position);
            }
        });

    }

    public final BroadcastReceiver bluetoothDeviceFoundReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, final Intent intent) {
            String action = intent.getAction();
            Log.d("bluetooth", action);
            if(action.equals(BluetoothDevice.ACTION_FOUND)) {
                final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                String deviceMacAddress = device.getAddress();

                DeviceRegistrationController registrationController = new DeviceRegistrationController(context);

                registrationController.checkRegistration(deviceMacAddress, new RegistrationCallback() {
                    @Override
                    public void onSuccess(UserRegistrationResponse registrationResponse) {
                        //get pin to be entered

                        if(registrationResponse!=null && registrationResponse.getSuccess().equals("true")) {
                            int pin = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 1234);
                            bluetoothDevices.add(device);
                            pins.add(pin);

                            bluetoothArrayAdapter.add(device.getName() + " " + device.getAddress());
                            bluetoothArrayAdapter.notifyDataSetChanged();
                            Log.d("bluetooth", "Pin is " + pin);
                        }
                    }
                });
            }
        }
    };

    private void pairDevice(int position) {
        BluetoothDevice device = bluetoothDevices.get(position);
        int pin = pins.get(position);
        device.createBond();


        byte[] pinBytes;

        try {
            pinBytes = ("" + pin).getBytes("UTF-8");
            device.setPin(pinBytes);
            device.setPairingConfirmation(true);
        }
        catch(IOException e) {
            L.d("unhandled IOException");
        }
        catch (Exception e) {
            L.e(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothDeviceFoundReceiver);
    }
}
