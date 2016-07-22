package com.bluetoothpair.android;

import com.bluetoothpair.android.model.UserRegistrationResponse;

/**
 * Created by suraj on 22/7/16.
 */
public interface RegistrationCallback {
    void onSuccess(UserRegistrationResponse registrationResponse);
}
