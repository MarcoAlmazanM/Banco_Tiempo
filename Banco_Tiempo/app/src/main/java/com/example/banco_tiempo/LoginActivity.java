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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.eTUsername);
        edPassword = findViewById(R.id.eTPassword);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            LoginResponse loginResponse = (LoginResponse) intent.getSerializableExtra("data");
            if (loginResponse.getLoginApproval() == 1) {
                preferences = this.getSharedPreferences("userData",Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("name",loginResponse.getName().toString());
                editor.putString("lastName",loginResponse.getLastName().toString());
                editor.apply();
                Intent menu = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(menu);
                finish();
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
            String password = edPassword.getText().toString();
            password = hash256(password);
            loginRequest.setPassword(password);
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

    // Section Hash Sha 256
    private static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private String hash256(String password){
        MessageDigest digest=null;
        String hash = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            hash = bytesToHexString(digest.digest());
        } catch(NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return hash;
    }


}