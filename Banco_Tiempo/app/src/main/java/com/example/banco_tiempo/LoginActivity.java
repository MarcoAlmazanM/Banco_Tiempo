package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnRegister;
    EditText edUsername, edPassword;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edUsername = findViewById(R.id.eTUsername);
        edPassword = findViewById(R.id.eTPassword);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            LoginResponse loginResponse = (LoginResponse) intent.getSerializableExtra("data");
            if (loginResponse.getLoginApproval() == 1) {
                preferences = this.getSharedPreferences("datos",Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("Nombre",loginResponse.getName().toString());
                editor.putString("Apellido",loginResponse.getLastName().toString());
                editor.apply();
                Intent menu = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(menu);
            }else{
                String message = "Inicio Fallido";
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void btnLogin(View view){
        if (TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString())) {
            String message = "All inputs required ...";
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        } else {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(edUsername.getText().toString());
            loginRequest.setPassword(edPassword.getText().toString());

            loginUser(loginRequest);
        }
    }

    public void btnRegister(View view){
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register);
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
                    String message = "An error occurred, please try again...";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}