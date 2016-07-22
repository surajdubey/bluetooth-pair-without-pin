package com.bluetoothpair.android.utils;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by suraj on 22/7/16.
 */
public class BluetoothUtil {
    public String getBluetoothMacAddress() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return adapter.getAddress();
    }
}
