package com.example.banco_tiempo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("beta/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
