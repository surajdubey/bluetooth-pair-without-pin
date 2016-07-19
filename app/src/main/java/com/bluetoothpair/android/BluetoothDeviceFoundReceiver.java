package com.bluetoothpair.android;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by suraj on 19/7/16.
 */
public class BluetoothDeviceFoundReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("bluetooth", action);
        if(BluetoothDevice.ACTION_FOUND.equals(action)) {

        }
    }
}
