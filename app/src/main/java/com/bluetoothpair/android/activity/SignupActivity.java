package com.bluetoothpair.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bluetoothpair.android.R;
import com.bluetoothpair.android.controller.SignupController;
import com.bluetoothpair.android.utils.BluetoothUtil;

public class SignupActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        Button btnSignup = (Button) findViewById(R.id.btnSignupSubmit);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void signup() {
        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        String username = new BluetoothUtil().getBluetoothMacAddress();

        SignupController signupController = new SignupController(this);
        signupController.signup(name, username, password);
    }

    private void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
