package com.bluetoothpair.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bluetoothpair.android.R;
import com.bluetoothpair.android.controller.LoginController;
import com.bluetoothpair.android.utils.BluetoothUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPassword = (EditText) findViewById(R.id.etPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String password = etPassword.getText().toString();
        String username = new BluetoothUtil().getBluetoothMacAddress();

        LoginController loginController = new LoginController(this);
        loginController.login(username, password);

    }
}
