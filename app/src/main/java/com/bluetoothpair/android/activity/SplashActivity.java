package com.bluetoothpair.android.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bluetoothpair.android.R;
import com.bluetoothpair.android.utils.PreferenceUtil;

public class SplashActivity extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PreferenceUtil preferenceUtil = new PreferenceUtil(this);
        if(preferenceUtil.getAccessToken().equals("")) {
            //Navigate to signup page
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        } else {
            //Navigate to signup page
            Intent intent = new Intent(this, BluetoothPairActivity.class);
            startActivity(intent);
        }

        //close current activity
        finish();
    }
}
