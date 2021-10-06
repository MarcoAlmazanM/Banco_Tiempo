package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //For changing status bar icon color
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        preferences = this.getSharedPreferences("userData",Context.MODE_PRIVATE);
        editor = preferences.edit();
        loginIntent();
        if(checkSession()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

    }

    public void btnLogin(View view){
        edUsername = findViewById(R.id.eTUsername);
        edPassword = findViewById(R.id.eTPassword);
        if (TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString())) {
            message = "All inputs required ...";
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        } else {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(edUsername.getText().toString());
            Resources resources = new Resources();
            String password = edPassword.getText().toString();
            password = resources.hash256(password);
            loginRequest.setPassword(password);
            loginUser(loginRequest);
        }
    }

    public void btnRegister(View view){
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register);
    }

    public boolean checkSession(){
        return this.preferences.getBoolean("SaveSession", false);
    }

    public void loginIntent(){
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            LoginResponse loginResponse = (LoginResponse) intent.getSerializableExtra("data");
            if (loginResponse.getLoginApproval() == 1) {
                preferences = this.getSharedPreferences("userData",Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("name",loginResponse.getName());
                editor.putString("lastName",loginResponse.getLastName());
                editor.putString("username",loginResponse.getUsername());
                editor.putBoolean("SaveSession",true);
                editor.apply();
                Intent menu = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(menu);
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }else{
                message = "Inicio Fallido";
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void loginUser(LoginRequest loginRequest) {
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    startActivity(new Intent(LoginActivity.this, LoginActivity.class).putExtra("data", loginResponse));
                    finish();
                } else {
                    message = "An error occurred, please try again...";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}