package com.bluetoothpair.android.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bluetoothpair.android.ApiEndpoint;
import com.bluetoothpair.android.activity.MainActivity;
import com.bluetoothpair.android.model.UserSignup;
import com.bluetoothpair.android.model.UserSignupResponse;
import com.bluetoothpair.android.utils.PreferenceUtil;
import com.bluetoothpair.android.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by suraj on 22/7/16.
 */
public class SignupController {
    private Context context;

    public SignupController(Context context) {
        this.context = context;
    }

    public void signup(String name, String username, String password) {
        final UserSignup signup = new UserSignup();
        signup.setName(name);
        signup.setPassword(password);
        signup.setUsername(username);

        Retrofit retrofit = new RetrofitUtil().getInstance();
        ApiEndpoint endpoint = retrofit.create(ApiEndpoint.class);

        Call<UserSignupResponse> signupResponseCall = endpoint.signup(signup);
        signupResponseCall.enqueue(new Callback<UserSignupResponse>() {
            @Override
            public void onResponse(Call<UserSignupResponse> call, Response<UserSignupResponse> response) {

                UserSignupResponse signupResponse = response.body();

                if(signupResponse.getSuccess().equalsIgnoreCase("true")) {
                    PreferenceUtil prefernceUtil = new PreferenceUtil(context);
                    prefernceUtil.setAccessToken(signupResponse.getAccess_token());

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else {
                    Toast.makeText(context, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserSignupResponse> call, Throwable t) {
                Toast.makeText(context, "inside failure", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
