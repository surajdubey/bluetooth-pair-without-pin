package com.bluetoothpair.android;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

/**
 * Created by suraj on 19/7/16.
 */
public class BluetoothDeviceFoundReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("bluetooth", action);
        if(BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            device.createBond();

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

}
