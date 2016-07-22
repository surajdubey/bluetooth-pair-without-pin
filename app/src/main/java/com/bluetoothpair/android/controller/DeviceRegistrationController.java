package com.bluetoothpair.android.controller;

import android.content.Context;

import com.bluetoothpair.android.ApiEndpoint;
import com.bluetoothpair.android.RegistrationCallback;
import com.bluetoothpair.android.model.UserRegistrationRequest;
import com.bluetoothpair.android.model.UserRegistrationResponse;
import com.bluetoothpair.android.utils.L;
import com.bluetoothpair.android.utils.PreferenceUtil;
import com.bluetoothpair.android.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by suraj on 22/7/16.
 */
public class DeviceRegistrationController {
    private Context context;
    public DeviceRegistrationController(Context context) {
        this.context = context;
    }

    public void checkRegistration(String deviceMacAddress, final RegistrationCallback callback) {
        Retrofit retrofit = new RetrofitUtil().getInstance();
        ApiEndpoint endpoint = retrofit.create(ApiEndpoint.class);

        PreferenceUtil preferenceUtil = new PreferenceUtil(context);
        String accessToken = preferenceUtil.getAccessToken();

        L.d(accessToken + " " + deviceMacAddress);

        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setUsername(deviceMacAddress);
        Call<UserRegistrationResponse> registrationResponseCall = endpoint.checkUserRegistration(accessToken, registrationRequest);

        registrationResponseCall.enqueue(new Callback<UserRegistrationResponse>() {
            @Override
            public void onResponse(Call<UserRegistrationResponse> call, Response<UserRegistrationResponse> response) {
                UserRegistrationResponse registrationResponse = response.body();
                callback.onSuccess(registrationResponse);
            }

            @Override
            public void onFailure(Call<UserRegistrationResponse> call, Throwable t) {

            }
        });


    }
}
