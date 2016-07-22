package com.bluetoothpair.android.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bluetoothpair.android.ApiEndpoint;
import com.bluetoothpair.android.activity.BluetoothPairActivity;
import com.bluetoothpair.android.model.UserLogin;
import com.bluetoothpair.android.model.UserLoginResponse;
import com.bluetoothpair.android.utils.PreferenceUtil;
import com.bluetoothpair.android.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by suraj on 22/7/16.
 */
public class LoginController {
    private Context context;
    public LoginController(Context context) {
        this.context = context;
    }

    public void login(final String username, String password) {
        UserLogin userLogin = new UserLogin();
        userLogin.setPassword(password);
        userLogin.setUsername(username);

        Retrofit retrofit = new RetrofitUtil().getInstance();
        ApiEndpoint endpoint = retrofit.create(ApiEndpoint.class);

        Call<UserLoginResponse> loginResponseCall = endpoint.login(userLogin);
        loginResponseCall.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                UserLoginResponse loginResponse = response.body();
                if(loginResponse.getSuccess().equals("true")) {
                    PreferenceUtil preferenceUtil = new PreferenceUtil(context);
                    preferenceUtil.setAccessToken(loginResponse.getAccess_token());

                    Intent intent = new Intent(context, BluetoothPairActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else {
                    Toast.makeText(context, loginResponse.getAccess_token(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Toast.makeText(context, "internal error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
