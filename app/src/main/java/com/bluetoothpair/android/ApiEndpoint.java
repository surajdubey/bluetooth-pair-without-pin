package com.bluetoothpair.android;

import com.bluetoothpair.android.model.UserLogin;
import com.bluetoothpair.android.model.UserLoginResponse;
import com.bluetoothpair.android.model.UserRegistration;
import com.bluetoothpair.android.model.UserRegistrationRequest;
import com.bluetoothpair.android.model.UserRegistrationResponse;
import com.bluetoothpair.android.model.UserSignup;
import com.bluetoothpair.android.model.UserSignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by suraj on 22/7/16.
 */
public interface ApiEndpoint {

    @POST("login")
    Call<UserLoginResponse> login(@Body UserLogin userLogin);

    @POST("signup")
    Call<UserSignupResponse> signup(@Body UserSignup userSignup);

    @POST("checkUserRegistration")
    Call<UserRegistrationResponse> checkUserRegistration(@Header("access_token") String accessToken,
                                                         @Body UserRegistrationRequest userRegistrationRequest);
}
